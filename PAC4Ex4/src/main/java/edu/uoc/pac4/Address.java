package edu.uoc.pac4;

public class Address implements Cloneable {

    private String street;
    private String zipCode;

    public Address(String street, String zipCode) {
        setStreet(street);
        setZipCode(zipCode);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Object clone()throws CloneNotSupportedException{
        return (Address)super.clone();
    }

}
