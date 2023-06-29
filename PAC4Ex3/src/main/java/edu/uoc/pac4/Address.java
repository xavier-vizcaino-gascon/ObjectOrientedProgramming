package edu.uoc.pac4;

import edu.uoc.pac4.exception.AddressException;

public class Address {
    private String street;
    private int number;
    private String zipCode;
    private String city;

    public Address(String street, int number, String zipCode, String city) throws AddressException {
        setStreet(street);
        setNumber(number);
        setZipCode(zipCode);
        setCity(city);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) throws AddressException {
        if (number<=0) {throw new AddressException(AddressException.ERR_STREET_NUMBER);}
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) throws AddressException {
        if (!zipCode.matches("^[a-zA-Z0-9]*$")) {throw new AddressException(AddressException.ERR_INVALID_ZIPCODE);}
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isInternational(){
        return !(this.zipCode.length() == 5 & this.zipCode.matches("^[a-zA-Z0-9]*$"));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Address) {
            //Casting
            Address objAds = (Address) obj;
            //Checks
            return (super.equals(obj) |
                    (objAds.getStreet()==this.street &
                    objAds.getNumber()==this.number &
                    objAds.getZipCode()==this.zipCode));
        }
        return false;
    }

    @Override
    public String toString() {
        return (getStreet() + ", " + getNumber() + ", " + getCity() + " (" + getZipCode() + ")");
    }
}
