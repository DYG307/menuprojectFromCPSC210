package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTransaction {

    @Test
    public void TestTransaction () {
        Transaction testAmt = new Transaction(5.00);
        assertEquals(5.00, testAmt.getAmount());
    }
}
