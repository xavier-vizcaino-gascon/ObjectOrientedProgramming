package edu.uoc.pac4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class OrderBatchTest {

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
            order1.addOrderItem(p2, 3);
            order1.addOrderItem(p3, 5);
            order2.addOrderItem(p2, 1);
            order2.addOrderItem(p3, 1);
            order3.addOrderItem(p4, 2);
            order4.addOrderItem(p5, 1);
            order5.addOrderItem(p3, 5);
            order5.addOrderItem(p1, 1);
            order5.addOrderItem(p2, 1);

            orderBatch2 = new OrderBatch("Printed books", "Collection of printed book orders");
            orderBatch1 = new OrderBatch("All orders", "Collection of all orders registered in the system");

        } catch (Exception e) {
            e.printStackTrace();
            fail("There was some error with the orderItem constructors");
        }
    }

    @Test
    public void testOrderBatchConstructor() {
        assertEquals("Printed books", orderBatch2.getName());
        assertEquals("Collection of printed book orders", orderBatch2.getDescription());
        assertEquals(1000, orderBatch2.getMaxSize());
        assertEquals(0, orderBatch2.getOrders().size());

        assertEquals("All orders", orderBatch1.getName());
        assertEquals("Collection of all orders registered in the system", orderBatch1.getDescription());
        assertEquals(1000, orderBatch1.getMaxSize());
        assertEquals(0, orderBatch1.getOrders().size());
    }

    @Test
    public void testSetName() {
        orderBatch2.setName("Printed books (Song of Ice and Fire Saga)");
        assertEquals("Printed books (Song of Ice and Fire Saga)", orderBatch2.getName());
    }

    @Test
    public void testSetDescription() {
        orderBatch1.setDescription("Collection of all orders registered in the system (from 2023)");
        assertEquals("Collection of all orders registered in the system (from 2023)", orderBatch1.getDescription());
    }

    @Test
    public void testAddOrder() {
        try {
            assertTrue(orderBatch1.addOrder(order1));
            assertEquals(1, orderBatch1.getOrders().size());

            assertTrue(orderBatch1.addOrder(order2));
            assertEquals(2, orderBatch1.getOrders().size());

            for (int i = 0; i < 998; ++i) {
                assertTrue(orderBatch1.addOrder(new Order(user, LocalDate.now())));
            }

            assertFalse(orderBatch1.addOrder(new Order(user, LocalDate.now())));

            Exception e = assertThrows(NullPointerException.class, () -> orderBatch1.addOrder(null));
            assertEquals(e.getMessage(), "[ERROR] The Order object cannot be null");
        } catch (Exception e) {
            e.printStackTrace();
            fail("There was some error with testAddOrder");
        }

    }

    @Test
    public void testRemoveOrder() {
        orderBatch1.addOrder(order1);
        orderBatch1.addOrder(order2);
        orderBatch1.addOrder(order3);
        assertTrue(orderBatch1.remove(order1));
        assertEquals(2, orderBatch1.getOrders().size());
        assertFalse(orderBatch1.remove(order1));
        assertEquals(2, orderBatch1.getOrders().size());
        assertTrue(orderBatch1.remove(order2));
        assertTrue(orderBatch1.remove(order3));
        assertFalse(orderBatch1.remove(order4));
        assertEquals(0, orderBatch1.getOrders().size());

        Exception e = assertThrows(NullPointerException.class, () -> orderBatch1.remove(null));
        assertEquals(e.getMessage(), "[ERROR] The Order object cannot be null");
    }

    @Test
    public void testRemoveAll() {
        try {
            for (int i = 0; i < 1000; ++i) {
                assertTrue(orderBatch1.addOrder(new Order(user, LocalDate.now())));
            }
            orderBatch1.remove();
            assertEquals(0, orderBatch1.getOrders().size());
        } catch (Exception e) {
            e.printStackTrace();
            fail("There was some error with testRemoveAll");
        }
    }

    @Test
    public void testExists() {
        orderBatch1.addOrder(order1);
        orderBatch1.addOrder(order2);
        orderBatch1.addOrder(order3);
        assertTrue(orderBatch1.exists(order1));
        assertTrue(orderBatch1.exists(order2));
        assertTrue(orderBatch1.exists(order3));
        assertFalse(orderBatch1.exists(order4));
        assertFalse(orderBatch1.exists(order5));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(orderBatch1.isEmpty());
        orderBatch1.addOrder(order1);
        assertFalse(orderBatch1.isEmpty());
        orderBatch1.remove(order2);
        assertFalse(orderBatch1.isEmpty());
        orderBatch1.remove(order1);
        assertTrue(orderBatch1.isEmpty());
    }

    @Test
    public void testIsFull() {
        try {
            assertFalse(orderBatch1.isFull());
            for (int i = 0; i < 999; ++i) {
                assertTrue(orderBatch1.addOrder(new Order(user, LocalDate.now())));
            }
            assertFalse(orderBatch1.isFull());
            orderBatch1.addOrder(new Order(user, LocalDate.now()));
            assertTrue(orderBatch1.isFull());
        } catch (Exception e) {
            e.printStackTrace();
            fail("There was some error with testIsFull");
        }
    }

    @Test
    public void testToString() {
        orderBatch1.addOrder(order1);
        orderBatch1.addOrder(order2);
        orderBatch1.addOrder(order3);
        orderBatch1.addOrder(order4);
        orderBatch1.addOrder(order5);
        assertEquals("###" + System.lineSeparator() +
                "#1: Product: A Song of Ice and Fire - Winds of Winter | Quantity: 5 | Price: 147.5 | Tax: 25.6" + System.lineSeparator() +
                "#2: Product: Dungeons and Dragons - Game Master Book | Quantity: 1 | Price: 35.0 | Tax: 6.07" + System.lineSeparator() +
                "#3: Product: Dice set (6 units) | Quantity: 1 | Price: 6.5 | Tax: 1.13" + System.lineSeparator() +
                "TOTAL = 189.0 | Tax: 32.8" + System.lineSeparator() +
                "###" + System.lineSeparator() +
                "#1: Product: White Walker Funko Pop | Quantity: 1 | Price: 14.5 | Tax: 2.52" + System.lineSeparator() +
                "TOTAL = 14.5 | Tax: 2.52" + System.lineSeparator() +
                "###" + System.lineSeparator() +
                "#1: Product: A Song of Ice and Fire - Game of Thrones | Quantity: 2 | Price: 43.0 | Tax: 7.46" + System.lineSeparator() +
                "TOTAL = 43.0 | Tax: 7.46" + System.lineSeparator() +
                "###" + System.lineSeparator() +
                "#1: Product: Dice set (6 units) | Quantity: 1 | Price: 6.5 | Tax: 1.13" + System.lineSeparator() +
                "#2: Product: A Song of Ice and Fire - Winds of Winter | Quantity: 1 | Price: 29.5 | Tax: 5.12" + System.lineSeparator() +
                "TOTAL = 36.0 | Tax: 6.25" + System.lineSeparator() +
                "###" + System.lineSeparator() +
                "#1: Product: Dungeons and Dragons - Game Master Book | Quantity: 2 | Price: 70.0 | Tax: 12.15" + System.lineSeparator() +
                "#2: Product: Dice set (6 units) | Quantity: 3 | Price: 19.5 | Tax: 3.38" + System.lineSeparator() +
                "#3: Product: A Song of Ice and Fire - Winds of Winter | Quantity: 5 | Price: 147.5 | Tax: 25.6" + System.lineSeparator() +
                "TOTAL = 237.0 | Tax: 41.13" + System.lineSeparator() +
                "###", orderBatch1.toString());

        orderBatch2.addOrder(order1);
        orderBatch2.addOrder(order2);
        orderBatch2.addOrder(order3);

        assertEquals("###" + System.lineSeparator() +
                "#1: Product: A Song of Ice and Fire - Game of Thrones | Quantity: 2 | Price: 43.0 | Tax: 7.46" + System.lineSeparator() +
                "TOTAL = 43.0 | Tax: 7.46" + System.lineSeparator() +
                "###" + System.lineSeparator() +
                "#1: Product: Dice set (6 units) | Quantity: 1 | Price: 6.5 | Tax: 1.13" + System.lineSeparator() +
                "#2: Product: A Song of Ice and Fire - Winds of Winter | Quantity: 1 | Price: 29.5 | Tax: 5.12" + System.lineSeparator() +
                "TOTAL = 36.0 | Tax: 6.25" + System.lineSeparator() +
                "###" + System.lineSeparator() +
                "#1: Product: Dungeons and Dragons - Game Master Book | Quantity: 2 | Price: 70.0 | Tax: 12.15" + System.lineSeparator() +
                "#2: Product: Dice set (6 units) | Quantity: 3 | Price: 19.5 | Tax: 3.38" + System.lineSeparator() +
                "#3: Product: A Song of Ice and Fire - Winds of Winter | Quantity: 5 | Price: 147.5 | Tax: 25.6" + System.lineSeparator() +
                "TOTAL = 237.0 | Tax: 41.13" + System.lineSeparator() +
                "###", orderBatch2.toString());
    }

}
