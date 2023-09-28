import DAO.IndustryDAO;
import DAO.StockDAO;
import DAO.StockPriceDAO;
import DAO.StockRiskDAO;
import DBConfig.HibernateConfig;
import Exceptions.ApiException;
import Model.Industry;
import Model.Stock;
import Util.StockAPIEnricher;
import Util.Webscraping;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("stock_db");
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

       for (Stock s : stocks) {
            try {
                Thread.sleep(5500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            try {
                StockAPIEnricher.getInstance().getIdForStock(s);
                if(!s.getId().contains(s.getName())) {
                    StockAPIEnricher.getInstance().getRiskForStock(s);
                }
            } catch (ApiException e) {
                System.out.println(e.getMessage());
            }
        }

        for (Stock s : stocks) {
            if (IndustryDAO.getInstance(emf).doesIndustryExist(s.getIndustry().getName())) {
                if(StockDAO.getInstance(emf).doesStockExist(s.getName())){
                    StockPriceDAO.getInstance(emf).persist(s.getStockPrices().get(0));
                    StockRiskDAO.getInstance(emf).persist(s.getStockRisks().get(0));
                } else {
                    Industry industry = IndustryDAO.getInstance(emf).findByName(s.getIndustry().getName(), Industry.class, "Industry");
                    s.setIndustry(industry);
                    StockDAO.getInstance(emf).persist(s);
                }
            } else {
                IndustryDAO.getInstance(emf).persist(s.getIndustry());
                StockDAO.getInstance(emf).persist(s);
            }
        }
    }
}