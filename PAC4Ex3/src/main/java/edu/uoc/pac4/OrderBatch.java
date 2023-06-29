package edu.uoc.pac4;

import edu.uoc.pac4.exception.OrderException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderBatch {
    private String name;
    private String description;
    private static final int MAX_SIZE=1000;
    private static final String MSG_ERR_NULL = "[ERROR] The Order object cannot be null";
    private Set orders;

    public OrderBatch(String name, String description) {
        setName(name);
        setDescription(description);
        orders=new HashSet<>(getMaxSize());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxSize(){
        return MAX_SIZE;
    }

    public List<Order> getOrders(){
        ArrayList<Order> orderList = new ArrayList<>(orders);
        orderList.sort(Order::compareTo);
        return orderList;
    }

    public boolean addOrder(Order order){
        if (order==null) {throw new NullPointerException(MSG_ERR_NULL);}
        if (isFull()) {return false;}
        return orders.add(order);
    }

    public boolean remove(Order order){
        if (order==null) {throw new NullPointerException(MSG_ERR_NULL);}
        return orders.remove(order);
    }

    public void remove(){
        orders.clear();
    }

    public boolean exists(Order order){
        return orders.contains(order);
    }

    public boolean isEmpty(){
        return orders.isEmpty();
    }

    public boolean isFull(){
        return (orders.size()>=getMaxSize());
    }

    @Override
    public String toString() {
        StringBuilder out = null;
        out = new StringBuilder();
        for (Order ordr:getOrders()){
            out.append("###").append(System.lineSeparator()).
                    append(ordr.bill()).append(System.lineSeparator());
        }
        return out.append("###").toString();
    }

    /**
     * This method updates the delivery date to today when order date is after parameter
     * @param orderDate date of order
     */
    public void deliverOrdersAfterDate(LocalDate orderDate){
        /**
         * This method generates an Order Stream from OrderBatch,
         * then OrderDate attribute is got and compared with parameter date to filter
         * later, for all the resulting objects, delivery date is updated
         */
        Stream<Order> st = orders.stream();
        st.filter(s -> s.getOrderDate().isAfter(orderDate))
                .forEach(s-> {
                    try {
                        s.setDeliveryDate(LocalDate.now());
                    } catch (OrderException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * This method provides the Orders with the maximum price
     * @return List of orders (if more than one) with maximium price
     */
    public List<Order> getLargestOrders(){
        /**
         * This method works in two steps:
         * First: An Order stream is generated and the object with the màximum
         * price is found, later this object is got and a method is applied to obtain
         * the maximum price value
         *
         * Second: The maximum price value from step one is used to apply a filter on
         * a fresh Order Stream to get maximum price objects again, later sorting is applied
         * using compareTo method on the Order class.
         *
         * Finally, objects are collected and returned as list.
         */

        Stream<Order> st1 = orders.stream();
        double maxPrice = st1.
                max(Comparator.comparingDouble(Order::getTotalPrice)).
                get().
                getTotalPrice();

        Stream<Order> st2 = orders.stream();
        List<Order> lOrders = st2.
                filter(u -> u.getTotalPrice()>=maxPrice).
                sorted(Order::compareTo).
                collect(Collectors.toList());
        return lOrders;
    }

    /**
     * This method calculates the total revenue for a given product
     * @param product product to audit
     * @return totalprice for this product considering all orders in order batch and all
     * order items (for this product) in each order
     */
    public double auditIncomeByProduct(Product product){
        /**
         * This method generates an Order Stream which is mapped with getOrderItems method
         * from Order class. Flatmap is later applied to flatten the OrderItem information of all
         * orders.
         *
         * Next, nonNull filtering is applied as well as product filtering thanks to getProduct method
         *
         * Later a new map (with method getTotalPrice of OrderItem class) is applied in the resulting
         * items to obtain the Total Price for each orderItem cointaining the selected product.
         *
         * The last poin is reduce the values applying a sum function to get the total amount.
         */
        Stream<Order> st = orders.stream();
        return st.map(Order::getOrderItems)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(p->p.getProduct()==product)
                .map(OrderItem::getTotalPrice)
                .reduce(Double.valueOf(0),Double::sum);
    }
}
