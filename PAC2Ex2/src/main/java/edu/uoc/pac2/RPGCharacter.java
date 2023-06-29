package edu.uoc.pac2;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a character in a role-playing game (RPG)
 * @author Xavier Vizcaino
 * @version 1.0
 */

public class RPGCharacter {
    /**
     * RPGCharacter identifier
     */
    private int id;
    /**
     * RPGCharacter identifier for next character
     */
    private static int nextId=0;
    /**
     * RPGCharacter name
     */
    private String name="Name";
    /**
     * RPGCharacter forbidden characters in character name
     */
    private static String forbiddenChars="[~!@#$%^&*()]";
    /**
     * RPGCharacter level
     */
    private int level=1;
    /**
     * RPGCharacter life with a range [0,100]
     */
    private int life=100;
    /**
     * RPGCharacter Alive indicator
     */
    private boolean isAlive=true;
    /**
     * RPGCharacter Local Date value for last time the character turn dead
     */
    private LocalDate lastDeath=null;
    /**
     * RPGCharacter alignment
     */
    private char alignment='N';

    /**
     * Class constructor
     */
    public RPGCharacter(){
        setId();
    }

    /**
     * Class constructor specifying 2 parameters
     * @param name to be set into the object
     * @param alignment to be set into the object
     */
    public RPGCharacter(String name, char alignment){
        try {
            setName(name);
            setAlignment(alignment);
            setId();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Class constructor specifying 3 parameters
     * @param name to be set into the object
     * @param alignment to be set into the object
     * @param life to be set into the object
     */
    public RPGCharacter(String name, char alignment, int life){
        try {
            setName(name);
            setAlignment(alignment);
            setLife(life);
            setId();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * RPGCharacter id getter
     * @return id
     */
    public int getId(){
        return this.id;
    }

    /**
     * RPGCharacter id setter
     */
    private void setId(){
        this.id=getNextId();
        incNextId();
    }

    /**
     * RPGCharacter nextId getter
     * @return nextId
     */
    public static int getNextId(){
        return RPGCharacter.nextId;
    }

    /**
     * This method increases the value of RPGCharacter nextId in 1 unit
     */
    private void incNextId(){
        RPGCharacter.nextId++;
    }

    /**
     * RPGCharacter name getter
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * RPGCharacter name setter specifying the name
     * @param name to be set into the object
     * @throws Exception when exception is raised in method setName with 2 parameters (name, forbiddenChars)
     */
    public void setName(String name) throws Exception{
        setName(name, RPGCharacter.forbiddenChars);
    }

    /**
     * RPGCharacter name setter specifying 2 params: the name and the forbidden char list
     * This method removes leading and trailing spaces and converts name to PascalCase
     * @param name to be set into the object
     * @param forbiddenChars forbidden chars list provided as string
     * @throws Exception when name contains any of the forbidden chars provided as param
     */
    public void setName(String name, String forbiddenChars) throws Exception {
        //Pattern & matcher definition RegEx
        Pattern p = Pattern.compile(forbiddenChars);
        Matcher m = p.matcher(name);

        if (m.find()){
            throw new Exception("[ERROR] Name cannot contain any of the forbidden chars");
        } else {

            String clean_name= name.strip().replaceAll(" +", " "); //string preparation with RegEx
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

    /**
     * RPGCharacter level getter
     * @return level
     */
    public int getLevel() { return this.level; }

    /**
     * RPGCharacter level setter
     * @param level to be set into the object
     */
    private void setLevel(int level) { this.level=level; }

    /**
     * This method increases character level in 1 unit
     */
    public void incLevel(){ setLevel(this.getLevel()+1); }

    /**
     * RPGCharacter life getter
     * @return life
     */
    public int getLife() { return this.life; }

    /**
     * RPGCharacter life setter
     * @param life to be set into the object
     * @throws Exception when param "life" is not >0
     */
    private void setLife(int life) throws Exception {

        if (life > 0) {
            this.life = life;
        } else {
            throw new Exception("[ERROR] Life must be a positive number");
        }
    }

    /**
     * This method updates life by adding (or removing) the units provided by param to the object attribute life
     * @param life to be added into the object's attribute life
     * @throws Exception when the object's life is 0 or when the param would increase life more than 50%
     */
    public void updateLife(int life) throws Exception{

        if (this.life==0){
            throw new Exception("[ERROR] The character is dead");
        } else {

            if (life>0.5*this.life && life>=0){
                throw new Exception("[ERROR] A character cannot increase its life more than 50% in a single healing");
            }
            else{
                this.life+=life;
            }
        }
        this.life = Math.max(this.life, 0);
        if (this.life==0) {setAlive(false);}
    }

    /**
     * RPGCharacter isAlive getter
     * @return isAlive
     */
    public boolean isAlive(){ return  this.isAlive; }

    /**
     * RPGCharacter isAlive setter
     * When param alive is false then LastDeath is updated with LocalDate.now()
     * @param alive to be set into the object
     */
    private void setAlive(boolean alive){

        if (!alive){
            setLastDeath(LocalDate.now());
        }
        this.isAlive=alive;
    }

    /**
     * This method resurrects the character by changing isAlive to "true" and setting life to 1
     * @throws Exception when the character is already alive
     */
    public void resurrect() throws Exception {

        if (isAlive()){
            throw new Exception("[ERROR] The character is already alive");
        }   else {
            setAlive(true);
            setLife(1);
        }
    }

    /**
     * RPGCharacter lastDeath getter
     * @return lastDeath
     */
    public LocalDate getLastDeath(){ return this.lastDeath; }

    /**
     * RPGCharacter lastDeath setter
     * @param lastDeath to be set into the object
     */
    private void setLastDeath(LocalDate lastDeath){ this.lastDeath=lastDeath; }

    /**
     * RPGCharacter alignment getter
     * @return alignment
     */
    public char getAlignment() { return this.alignment; }

    /**
     * RPGCharacter alignment setter
     * This method checks if param alignment is one of the valid options
     * @param alignment to be set into the object
     * @throws Exception when param alignment is not one of the valid options
     */
    public void setAlignment(char alignment) throws Exception {
        char [] options = {'H', 'A', 'N'};

        if (new String(options).contains(String.valueOf(alignment))){
            this.alignment=alignment;
        } else {
            throw new Exception("[ERROR] Alignment must be a valid value ('H', 'A' or 'N')");
        }
    }
}
