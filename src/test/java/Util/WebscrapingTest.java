package Util;

import Model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebscrapingTest {

    @BeforeEach
    void setup() {
        Webscraping.getInstance();
    }
    @Test
    void findBestStocks() {
        Webscraping webscraping = Webscraping.getInstance();

        List<Stock> list = webscraping.findBestStocks();

        assertNotNull(list.get(0).getName());
        assertNotNull(list.get(1).getName());
        assertNotNull(list.get(0).getStockPrices().get(0).getPrice());
        assertNotNull(list.get(1).getStockPrices().get(0).getPrice());
        assertNotNull(list.get(0).getStockPrices().get(0).getPriceChange());
        assertNotNull(list.get(1).getStockPrices().get(0).getPriceChange());
        //Testing to see if the price is correctly parsed as a double and not a string
        assertInstanceOf(Double.class,list.get(0).getStockPrices().get(0).getPrice());
    }

    @Test
    void findWorstStocks() {
        Webscraping webscraping = Webscraping.getInstance();
        List<Stock> list = webscraping.findWorstStocks();

        assertNotNull(list.get(0).getName());
        assertNotNull(list.get(1).getName());
        assertNotNull(list.get(0).getStockPrices().get(0).getPrice());
        assertNotNull(list.get(1).getStockPrices().get(0).getPrice());
        assertNotNull(list.get(0).getStockPrices().get(0).getPriceChange());
        assertNotNull(list.get(1).getStockPrices().get(0).getPriceChange());
        //Testing to see if the price is correctly parsed as a double and not a string
        assertInstanceOf(Double.class,list.get(0).getStockPrices().get(0).getPrice());
    }
}