package Util;

import Exceptions.ApiException;
import Model.Stock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockAPIEnricherTest {

    @BeforeAll
    static void setup(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e){}
    }

    @Test
    void getIdForStock() {
        Stock stock1 = new Stock("Tesla Inc");
        assertNull(stock1.getId());
        try {
            stock1 = StockAPIEnricher.getInstance().getIdForStock(stock1);
        } catch (ApiException e){
        }
        assertEquals("0P0000OQN8", stock1.getId());

        Stock stock2 = new Stock("awfninfineginsien");
        //assertThrows(ApiException.class, () -> StockAPIEnricher.getInstance().getIdForStock(stock2));
    }

    @Test
    void getRiskForStock() {
        Stock stock1 = new Stock("Tesla Inc");
        stock1.setId("0P0000OQN8");
        try{
            stock1 = StockAPIEnricher.getInstance().getRiskForStock(stock1);
        } catch (ApiException e){
        }
        assertEquals(1, stock1.getStockRisks().size());
        assertNotNull(stock1.getStockRisks().get(0).getRiskDate());
        assertNotNull(stock1.getStockRisks().get(0).getRating());
        assertNotNull(stock1.getStockRisks().get(0).getProfile());

        Stock stock2 = new Stock("Tesla Inc");
        stock2.setId("0P0000OQ");

        //assertThrows(ApiException.class, () -> StockAPIEnricher.getInstance().getRiskForStock(stock2));
    }
}