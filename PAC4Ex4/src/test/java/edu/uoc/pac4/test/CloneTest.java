package edu.uoc.pac4.test;

import edu.uoc.pac4.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CloneTest {

    @Test
    void testClone() {
        LocalDate expireDate = LocalDate.of(2024, 6, 27);
        User u1 = new User("Quim Motger","Av. Diagonal, 23", "08034", "1234-5678-9012-3456", expireDate);
        try {
            User u2 = (User) u1.clone();
            assertNotEquals(u1, u2);
            assertEquals(u1.getName(),u2.getName());
            assertNotEquals(u1.getAddress(),u2.getAddress());
            assertEquals(u1.getAddress().getStreet(),u2.getAddress().getStreet());
            assertEquals(u1.getAddress().getZipCode(),u2.getAddress().getZipCode());
            assertNotEquals(u1.getPaymentCard(),u2.getPaymentCard());
            assertEquals(u1.getPaymentCard().getNumber(),u2.getPaymentCard().getNumber());
            assertEquals(u1.getPaymentCard().getExpireDate(),u2.getPaymentCard().getExpireDate());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            fail("test failed");
        }
    }
    
}
