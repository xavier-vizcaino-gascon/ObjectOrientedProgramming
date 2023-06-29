package edu.uoc.pac4;

import edu.uoc.pac4.exception.OrderItemException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class OrderItem implements Billable {
    private int quantity;
    private Product product;
    private Order order;

    public OrderItem(Order order, Product product, int quantity) throws OrderItemException {
        setQuantity(quantity);
        setProduct(product);
        setOrder(order);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity<=0 ? 1 : quantity;
    }

    public double getTotalPrice(){
        return getQuantity()*product.getPrice();
    }

    public Order getOrder() {
        return order;
    }

    private void setOrder(Order order) throws OrderItemException {
        if (order==null) {throw new OrderItemException(OrderItemException.ERR_NULL_ORDER);}
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    private void setProduct(Product product) throws OrderItemException {
        if (product==null) {throw new OrderItemException(OrderItemException.ERR_NULL_PRODUCT);}
        this.product = product;
    }

    @Override
    public String bill() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat format = new DecimalFormat("0.##",symbols);

        return "Product: " + product.getName() + " | " +
                "Quantity: " + getQuantity() + " | " +
                "Price: " + String.format(Locale.ROOT, "%.1f", getTotalPrice()) + " | " +
                "Tax: " + format.format(taxValue(getTotalPrice()));
    }
}
