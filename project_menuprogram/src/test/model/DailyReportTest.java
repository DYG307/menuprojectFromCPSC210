package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DailyReportTest{

    @Test
    public void testCalculateTotalRevenue() {

        DailyReport dailyReport = new DailyReport();
        Transaction transaction1 = new Transaction(10.99);
        Transaction transaction2 = new Transaction(5.99);

        dailyReport.addTransaction(transaction1);
        dailyReport.addTransaction(transaction2);

        assertEquals(16.98, dailyReport.calculateTotalRevenue(), 0.001);
    }


}