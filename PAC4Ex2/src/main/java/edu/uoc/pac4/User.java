package edu.uoc.pac4;

import edu.uoc.pac4.exception.UserException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class User {
    //Attributes
    private String name;
    private String email;
    private LocalDate birthDate;
    private double debt = 0;
    private boolean premium = false;
    private Gender gender;
    private Address address;
    public static final int MAX_ORDERS = 1000;
    private Order[] orders;


    //Methods
    public User(String name, String email, LocalDate birthDate, Gender gender) throws UserException {
        setName(name);
        setEmail(email);
        setBirthDate(birthDate);
        setGender(gender);
        this.orders = new Order[MAX_ORDERS];
    }

    public User(String name, String email, LocalDate birthDate, Gender gender, Address address) throws UserException {
        setName(name);
        setEmail(email);
        setBirthDate(birthDate);
        setGender(gender);
        setAddress(address);
        this.orders = new Order[MAX_ORDERS];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws UserException {
        if(!email.matches("(.*)[^\s]@\\w*\\.\\w*(.*)")) {throw new UserException(UserException.ERR_INVALID_EMAIL);}
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) throws UserException {
        if (Period.between(birthDate,LocalDate.now()).getYears()<16) {
            throw new UserException(UserException.ERR_MIN_AGE);}
        this.birthDate = birthDate;
    }

    public double getDebt() {
        return debt;
    }

    private void setDebt(double debt) {
        this.debt = debt;
    }

    public void addDebt(double debt) throws UserException {
        if (debt<=0) {throw new UserException(UserException.ERR_ADD_INVALID_DEBT_VALUE);}
        this.debt+=debt;
    }

    public void resetDebt() {this.debt=0;}

    public boolean isPremium(){
        return premium;
    }

    public void subscribe() throws UserException {
        if(getDebt()>0) {throw new UserException(UserException.ERR_PREMIUM_SUBSCRIPTION);}
        this.premium=true;
    }

    public void unsubscribe() {this.premium=false;}

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            //Casting
            User objUsr = (User) obj;
            //Checks
            return (super.equals(obj) | objUsr.getEmail()==this.email);
        }
        return false;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/YYYY");
        String premiumYN = isPremium()? "Y" : "N";
        return ("\tName: " + getName() + System.lineSeparator() +
                "\te-mail: " + getEmail() + System.lineSeparator() +
                "\tBirth date: " + getBirthDate().format(formatter) + System.lineSeparator() +
                "\tPremium?: " + premiumYN + System.lineSeparator() +
                "\tAddress: " + getAddress() + System.lineSeparator());
    }

    public Order[] getOrders(){
        return orders;
    }

    public boolean addOrder(Order order){
        for (int i = 0; i<orders.length; i++){
            if(orders[i]==null) {
                orders[i]=order;
                return true;}
        }
        return false;
    }
}
