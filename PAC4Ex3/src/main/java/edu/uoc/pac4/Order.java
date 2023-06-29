package edu.uoc.pac4;

import edu.uoc.pac4.exception.OrderException;
import edu.uoc.pac4.exception.OrderItemException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

public class Order implements Billable,Comparable {
    private UUID id;
    private LocalDate orderDate;
    private LocalDate deliveryDate = null;
    public static final int MAX_ORDER_ITEMS = 100;
    private OrderItem[] orderItems;
    private User user;

    public Order(User user, LocalDate orderDate) throws OrderException {
        setOrderDate(orderDate);
        setUser(user);
        setId();
        this.orderItems = new OrderItem[MAX_ORDER_ITEMS];
    }

    public UUID getId() {
        return id;
    }

    private void setId() {
        this.id = UUID.randomUUID();
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) throws OrderException {
        if (user==null) {throw new OrderException(OrderException.ERR_NULL_USER);}
        this.user = user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) throws OrderException {
        if (deliveryDate.isBefore(orderDate)) {throw new OrderException(OrderException.ERR_WRONG_DELIVERY_DATE);}
        this.deliveryDate = deliveryDate;
    }

    public double getTotalPrice(){
        double sum = 0;
        for (OrderItem orderItem : orderItems) {
            if (orderItem == null) {
                continue;
            }
            sum += orderItem.getTotalPrice();
        }
        return sum;
    }

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    private int getOrderItemIndex (Product product){
        for (int i = 0; i<orderItems.length; i++){
            if(orderItems[i]==null) {continue;}
            if(orderItems[i].getProduct()==product) {return i;}
        }
        return -1;
    }

    public boolean addOrderItem(Product product, int quantity) throws OrderItemException {
        int pIndex = getOrderItemIndex(product);
        if (pIndex==-1){  // product does not exist in order
            // Finding first null
            int i=0;
            boolean nullFound=false;
            while (i<orderItems.length & !nullFound) {
                nullFound=orderItems[i]==null;
                i++;}

            if (!nullFound) { // null not found, meaning no empty positions in array
                return false;}

            orderItems[i-1] = new OrderItem(this, product, quantity);
        } else { // product does exist --> update quantity
            orderItems[pIndex].setQuantity(orderItems[pIndex].getQuantity()+quantity);
        }
        return true;
    }

    public void removeOrderItem(Product product, int quantity) {
        int pIndex = getOrderItemIndex(product);
        if (pIndex != -1) { // product does exist --> check quantity
            if (quantity >= orderItems[pIndex].getQuantity()) {
                orderItems[pIndex] = null;
            } else {
                orderItems[pIndex].setQuantity(orderItems[pIndex].getQuantity() - quantity);
            }
        }
    }

    @Override
    public String bill() {
        StringBuilder out = null;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat format = new DecimalFormat("0.##",symbols);

        for (int i=1; i<=orderItems.length; i++){
            if (orderItems[i-1]==null) {continue;}
            if (out==null){
                out = new StringBuilder("#" + i + ": " + orderItems[i - 1].bill() + System.lineSeparator());
            } else {
                out.append("#").append(i).append(": ").append(orderItems[i - 1].bill()).append(System.lineSeparator());
            }
        }
        out.append("TOTAL = ").append(getTotalPrice()).append(" | Tax: ").append(format.format(taxValue(getTotalPrice())));
        return out.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (o==null) {throw new NullPointerException();}

        Order objOrd = (Order) o;

        if (this.getOrderDate().isBefore(objOrd.getOrderDate())) {return -1;}
        else if (this.getOrderDate().isAfter(objOrd.getOrderDate())) { return 1;}
        else {
            if (this.getTotalPrice()<objOrd.getTotalPrice()) {return -1;}
            else if (this.getTotalPrice()==objOrd.getTotalPrice()) {return 0;}
        }
        return 1;
    }
}
