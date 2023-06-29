package edu.uoc.pac4;

import java.time.LocalDate;

public class PaymentCard implements Cloneable {

    private String number;
    private LocalDate expireDate;

    public PaymentCard(String number, LocalDate expireDate) {
        setNumber(number);
        setExpireDate(expireDate);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public Object clone()throws CloneNotSupportedException{
        return (PaymentCard)super.clone();
    }

}
