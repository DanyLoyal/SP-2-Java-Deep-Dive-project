import Exceptions.ApiException;
import Model.Stock;
import Util.StockAPIEnricher;
import Util.Webscraping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        List<Stock> stocks = new ArrayList<>();

    ExecutorService es = new ThreadPoolExecutor(0,2,200,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(128));
    try {
        es.awaitTermination(200, TimeUnit.MILLISECONDS);
    }catch (InterruptedException e){
        es.shutdown();
    }

    Future<List<Stock>> webscrapingBestFuture = es.submit(
            () -> Webscraping.getInstance().findStocks(2, Webscraping.StockType.BEST)
    );

    Future<List<Stock>> webscrapingWorstFuture = es.submit(
            () -> Webscraping.getInstance().findStocks(2, Webscraping.StockType.WORST)
    );

    try {
        List<Stock> bestStocks = webscrapingBestFuture.get();
        List<Stock> worstStock = webscrapingWorstFuture.get();

        stocks.addAll(bestStocks);
        stocks.addAll(worstStock);
        stocks.forEach(stock -> System.out.println(stock.getName() + " " + stock.getStockPrices().get(0).getPrice() + " " + stock.getStockPrices().get(0).getPriceChange()));

    } catch (Exception e) {
    }

//       for (Stock s : stocks) {
//            try {
//                Thread.sleep(5500);
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
//            try {
//                StockAPIEnricher.getInstance().getIdForStock(s);
//                StockAPIEnricher.getInstance().getRiskForStock(s);
//            } catch (ApiException e) {
//                System.out.println(e.getMessage());
//            }
//        }

//        for (Stock s : stocks) {
//            if (IndustryDAO.getInstance().doesIndustryExist(s.getIndustry())) {
//                if(StockDAO.getInstance().doesStockExist(s.getId())){
//                    StockPriceDAO.getInstance().persist(s.getStockPrices().get(0));
//                    StockRiskDAO.getInstance().persist(s.getStockRisks().get(0));
//                } else {
//                    StockDAO.getInstance().persist(s);
//                }
//            } else {
//                IndustryDAO.getInstance().persist(s.getIndustry());
//                StockDAO.getInstance().persist(s);
//            }
//        }
    }
}