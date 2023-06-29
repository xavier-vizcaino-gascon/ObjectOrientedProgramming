package edu.uoc.pac4;

import java.time.LocalDate;

public class User implements Cloneable {

    private String name;
    public Address address;
    public PaymentCard paymentCard;

    public User(String name, String street, String zipCode, String cardNumber, LocalDate cardExpireDate) {
        setName(name);
        this.address = new Address(street, zipCode);
        this.paymentCard = new PaymentCard(cardNumber, cardExpireDate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public Object clone()throws CloneNotSupportedException{
        User usr = (User)super.clone();
        usr.address=(Address) this.address.clone();
        usr.paymentCard=(PaymentCard)  this.paymentCard.clone();
        return usr;
    }

}
