package edu.uoc.pac4;

public interface Billable {
    public static final double TAX = 0.21;

    public default double taxValue(double totalPrice) {
        return (totalPrice-(totalPrice/(1+TAX)));
    }

    public String bill();
}
