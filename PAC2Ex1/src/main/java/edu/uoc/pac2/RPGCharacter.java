package edu.uoc.pac2;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPGCharacter {
    //Attributes
    private int id;
    private static int nextId=0;
    private String name="Name";
    private static String forbiddenChars="[~!@#$%^&*()]";
    private int level=1;
    private int life=100;
    private boolean isAlive=true;
    private LocalDate lastDeath=null;
    private char alignment='N';

    public RPGCharacter(){
        setId();
    }

    public RPGCharacter(String name, char alignment){
        setName(name);
        setAlignment(alignment);
        setId();
    }

    public RPGCharacter(String name, char alignment, int life){
        setName(name);
        setAlignment(alignment);
        setLife(life);
        setId();
    }

    public int getId(){
        return this.id;
    }

    private void setId(){
        this.id=getNextId();
        incNextId();
    }

    public static int getNextId(){
        return RPGCharacter.nextId;
    }

    private void incNextId(){
        RPGCharacter.nextId++;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        setName(name, RPGCharacter.forbiddenChars);
    }

    public void setName(String name, String forbiddenChars) {
        //Pattern & matcher definition RegEx
        Pattern p = Pattern.compile(forbiddenChars);
        Matcher m = p.matcher(name);

        if (m.find()){
            System.out.println ("[ERROR] Name cannot contain any of the forbidden chars");
        } else {
            String clean_name = name.strip().replaceAll(" +", " "); //string preparation with RegEx
            String[] words = clean_name.split(" ");
            String Pascal ="";

            for (String word : words) {
                String temp = word.substring(0, 1).toUpperCase() +
                        word.substring(1).toLowerCase();
                Pascal = Pascal.concat(temp);
            }
            this.name=Pascal;
        }
    }

    public int getLevel() { return this.level; }

    private void setLevel(int level) { this.level=level; }

    public void incLevel(){ setLevel(this.getLevel()+1); }

    public int getLife() { return this.life; }

    private void setLife(int life) {

        if (life > 0) {
            this.life = life;
        } else {
            System.out.println ("[ERROR] Life must be a positive number");
        }
    }

    public void updateLife(int life){

        if (this.life==0){
            System.out.println("[ERROR] The character is dead");
        } else {

            if (life>0.5*this.life && life>=0){
                System.out.println("[ERROR] A character cannot increase its life more than 50% in a single healing");
            }
            else{
                this.life+=life;
            }
        }
        this.life = Math.max(this.life, 0);
        if (this.life==0) {setAlive(false);}
    }

    public boolean isAlive(){ return  this.isAlive; }

    private void setAlive(boolean alive){

        if (!alive){
            setLastDeath(LocalDate.now());
        }
        this.isAlive=alive;
    }

    public boolean resurrect() {

        if (isAlive()){
            return false;
        }   else {
            setAlive(true);
            setLife(1);
            return true;
        }
    }

    public LocalDate getLastDeath(){ return this.lastDeath; }

    private void setLastDeath(LocalDate lastDeath){ this.lastDeath=lastDeath; }

    public char getAlignment() { return this.alignment; }

    public void setAlignment(char alignment) {
        char [] options = {'H', 'A', 'N'};

        if (new String(options).contains(String.valueOf(alignment))){
            this.alignment=alignment;
        } else {
            System.out.println ("[ERROR] Alignment must be a valid value ('H', 'A' or 'N')");
        }
    }
}
