package Util;

import Model.Stock;
import Model.StockPrice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Webscraping {
    public enum StockType {
        BEST, WORST
    }

    private static Webscraping webscraping_instance = null;

    private Webscraping() {
    }

    public static Webscraping getInstance() {
        if (webscraping_instance == null)
            webscraping_instance = new Webscraping();
        return webscraping_instance;
    }

    public static void main(String[] args) {
        List<Stock> testListBest = Webscraping.getInstance().findStocks(4, StockType.BEST);
        testListBest.forEach(stock -> System.out.println(stock.getName() + " " + stock.getStockPrices().get(0).getPrice() + " " + stock.getStockPrices().get(0).getPriceChange()));
        List<Stock> testListWorst = Webscraping.getInstance().findStocks(4, StockType.WORST);
        testListWorst.forEach(stock -> System.out.println(stock.getName() + " " + stock.getStockPrices().get(0).getPrice() + " " + stock.getStockPrices().get(0).getPriceChange()));
    }


    //Method that returns a list of stocks from Nordnet.dk
    //numberOfStocks is the number of stocks you want to get
    //stockType is the type of stocks you want to get (best or worst)
    public List<Stock> findStocks(int numberOfStocks, StockType stockType){
        String url = "";
        if(stockType == StockType.BEST) {
            url = "https://www.nordnet.dk/markedet/aktiekurser?sortField=diff_pct&sortOrder=descending&exchangeCountry=US&exchangeList=us%3Anq";
        } else if (stockType == StockType.WORST){
            url = "https://www.nordnet.dk/markedet/aktiekurser?sortField=diff_pct&sortOrder=ascending&exchangeCountry=US&exchangeList=us%3Anq";
        } else {
            System.out.println("StockType not found");
        }

        List<Stock> stockList = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                    .get();
            Thread.sleep(1000); // 1 second

            Elements rows = doc.select("div.FlexTable__StyledDiv-sc-v6wpic-0.JIqfK div");
            for(Element e : rows){
                if(e.getElementsByAttributeValueContaining("role", "row").isEmpty()){
                    continue;
                }
                else {
                    String name = e.select(".hZYbiE.NameCell__StyledLink-sc-qgec4s-0.foCaAq.Link__StyledLink-sc-apj04t-0").text();
                    String priceString = e.select(".hlZdvv.NumberCell__StyledFlexTableCell-sc-icuiuz-0.ecVIUb.Cell__StyledFlexbox-sc-icfddc-0.dcnKuH.Flexbox__StyledFlexbox-sc-1ob4g1e-0").text();
                    priceString = priceString.replace(",", ".");
                    double price = 0;
                    if(!priceString.isEmpty()) {
                        price = Double.parseDouble(priceString);
                    }
                    String priceChange = (e.select(".ecVIUb.Cell__StyledFlexbox-sc-icfddc-0.eDgWiq.Flexbox__StyledFlexbox-sc-1ob4g1e-0").text());

                    if(name!= null && price != 0 && priceChange != null){
                        Stock stock = new Stock(name);
                        stock.addStockPrice(new StockPrice(price, priceChange, LocalDate.now()));
                        stockList.add(stock);
                    }
                }

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        List<Stock> returnList =  stockList.subList(0, numberOfStocks);
        return returnList;
    }

}
