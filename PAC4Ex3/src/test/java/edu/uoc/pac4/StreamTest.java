package edu.uoc.pac4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StreamTest {

    User user;
    Order order1;
    Order order2;
    Order order3;
    Order order4;
    Order order5;
    Product p1;
    Product p2;
    Product p3;
    Product p4;
    Product p5;

    OrderBatch orderBatch1;
    OrderBatch orderBatch2;

    @BeforeEach
    public void orderBatchSetUp() {
        try {
            LocalDate birthDate = LocalDate.of(1995, 6, 13);
            user = new User("Quim Motger", "jmotger@uoc.edu", birthDate, Gender.MALE);

            LocalDate orderDate1 = LocalDate.now();
            LocalDate orderDate2 = LocalDate.of(2023, 4, 1);
            LocalDate orderDate3 = LocalDate.of(2023, 3, 1);
            LocalDate orderDate4 = LocalDate.of(2023, 2, 1);
            LocalDate orderDate5 = LocalDate.of(2023, 1, 1);

            order1 = new Order(user, orderDate1);
            order2 = new Order(user, orderDate2);
            order3 = new Order(user, orderDate3);
            order4 = new Order(user, orderDate4);
            order5 = new Order(user, orderDate5);

            p1 = new PrintedBook("Dungeons and Dragons - Game Master Book", 35.0, 9.5);
            p2 = new Merchandising("Dice set (6 units)", 6.5, 1.9, 0.2);
            p3 = new PrintedBook("A Song of Ice and Fire - Winds of Winter", 29.5, 8.5);
            p4 = new PrintedBook("A Song of Ice and Fire - Game of Thrones", 21.5, 7.4);
            p5 = new Merchandising("White Walker Funko Pop", 14.5, 5.5, 0.6);

            order1.addOrderItem(p1, 2);
            order1.addOrderItem(p3, 3);
            order1.addOrderItem(p4, 5);
            order2.addOrderItem(p3, 1);
            order2.addOrderItem(p4, 1);
            order3.addOrderItem(p4, 2);
            order4.addOrderItem(p5, 1);
            order5.addOrderItem(p3, 5);
            order5.addOrderItem(p1, 1);
            order5.addOrderItem(p2, 1);

            orderBatch1 = new OrderBatch("All orders", "Collection of all orders registered in the system");
            orderBatch2 = new OrderBatch("Printed books", "Collection of printed book orders");

            orderBatch1.addOrder(order1);
            orderBatch1.addOrder(order2);
            orderBatch1.addOrder(order3);
            orderBatch1.addOrder(order4);
            orderBatch1.addOrder(order5);

            orderBatch2.addOrder(order1);
            orderBatch2.addOrder(order2);
            orderBatch2.addOrder(order3);

        } catch (Exception e) {
            e.printStackTrace();
            fail("There was some error with the orderItem constructors");
        }
    }

    @Test
    public void testAuditIncomeByProduct() {
        assertEquals(105.0, orderBatch1.auditIncomeByProduct(p1));
        assertEquals(6.5, orderBatch1.auditIncomeByProduct(p2));
        assertEquals(265.5, orderBatch1.auditIncomeByProduct(p3));
        assertEquals(172.0, orderBatch1.auditIncomeByProduct(p4));
        assertEquals(14.5, orderBatch1.auditIncomeByProduct(p5));

        assertEquals(70.0, orderBatch2.auditIncomeByProduct(p1));
        assertEquals(0.0, orderBatch2.auditIncomeByProduct(p2));
        assertEquals(118.0, orderBatch2.auditIncomeByProduct(p3));
        assertEquals(172.0, orderBatch2.auditIncomeByProduct(p4));
        assertEquals(0.0, orderBatch2.auditIncomeByProduct(p5));
    }

    @Test
    public void testDeliverOrdersAfterDate() {
        LocalDate orderDate = LocalDate.of(2023, 3, 1);
        orderBatch1.deliverOrdersAfterDate(orderDate);

        for (Order order : orderBatch1.getOrders()) {
            if (order.getOrderDate().compareTo(orderDate) > 0) {
                assertEquals(LocalDate.now(), order.getDeliveryDate());
                assertNotNull(order.getDeliveryDate());
            } else
                assertNull(order.getDeliveryDate());
        }
    }

    @Test
    public void testGetLargestOrders() {
        try {
            List<Order> ordersFromBatch1 = orderBatch1.getLargestOrders();
            assertEquals(1, ordersFromBatch1.size());
            assertEquals(266, ordersFromBatch1.get(0).getTotalPrice());

            List<Order> ordersFromBatch2 = orderBatch2.getLargestOrders();
            assertEquals(1, ordersFromBatch2.size());
            assertEquals(266, ordersFromBatch2.get(0).getTotalPrice());

            LocalDate d = LocalDate.of(2023, 1, 1);
            Order newOrder = new Order(user, d);
            newOrder.addOrderItem(p1, 2);
            newOrder.addOrderItem(p3, 3);
            newOrder.addOrderItem(p4, 5);
            orderBatch2.addOrder(newOrder);

            ordersFromBatch2 = orderBatch2.getLargestOrders();
            assertEquals(2, ordersFromBatch2.size());
            assertEquals(266, ordersFromBatch2.get(0).getTotalPrice());
            assertEquals(266, ordersFromBatch2.get(1).getTotalPrice());
            assertEquals(d, ordersFromBatch2.get(0).getOrderDate());
            assertEquals(LocalDate.now(), ordersFromBatch2.get(1).getOrderDate());
        } catch (Exception e) {
            e.printStackTrace();
            fail("There was some error with testGetLargestOrders");
        }

    }

}
