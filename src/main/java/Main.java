import Exceptions.ApiException;
import Model.Stock;
import Util.StockAPIEnricher;
import Util.Webscraping;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Stock> stocks = new ArrayList<>();

        stocks.addAll(Webscraping.getInstance().findBestStocks());
        stocks.addAll(Webscraping.getInstance().findWorstStocks());

        for (Stock s : stocks) {
            try {
                Thread.sleep(5500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            try {
                StockAPIEnricher.getInstance().getIdForStock(s);
                StockAPIEnricher.getInstance().getRiskForStock(s);
            } catch (ApiException e) {
                System.out.println(e.getMessage());
            }
        }

        for (Stock s : stocks) {
            if (IndustryDAO.getInstance().doesIndustryExist(s.getIndustry())) {
                if(StockDAO.getInstance().doesStockExist(s.getId())){
                    StockPriceDAO.getInstance().persist(s.getStockPrices().get(0));
                    StockRiskDAO.getInstance().persist(s.getStockRisks().get(0));
                } else {
                    StockDAO.getInstance().persist(s);
                }
            } else {
                IndustryDAO.getInstance().persist(s.getIndustry());
                StockDAO.getInstance().persist(s);
            }
        }
    }
}