package Util;

import Model.Stock;
import Model.StockPrice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class Webscraping {

    public static void main(String[] args) {
        Webscraping webscraping = new Webscraping();
        List<Stock> bestStocks = webscraping.findBestStocks();
        List<Stock> worstStocks = webscraping.findWorstStocks();
        System.out.println("best stocks: " + bestStocks);
        System.out.println("worst stocks: " + worstStocks);


    }

    //Method that returns a list of the 2 best performing stocks of the day
    public List<Stock> findBestStocks(){
        String url = "https://www.nordnet.dk/markedet/aktiekurser?sortField=diff_pct&sortOrder=descending&exchangeCountry=US&exchangeList=us%3Anq";
        List<Stock> bestPerformers = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                    .get();
            Thread.sleep(1000); // 1 second

            Elements stock1 = doc.select("div.dnLFDN.Rows__AlignedRow-sc-1udgki9-0.bhUzoF.Row__StyledRow-sc-1iamenj-0.fBJmAV.Flexbox__StyledFlexbox-sc-1ob4g1e-0:nth-of-type(2)");
            Elements stock2 = doc.select("div.dnLFDN.Rows__AlignedRow-sc-1udgki9-0.bhUzoF.Row__StyledRow-sc-1iamenj-0.fBJmAV.Flexbox__StyledFlexbox-sc-1ob4g1e-0:nth-of-type(3)");
            String name1 = stock1.select(".hZYbiE.NameCell__StyledLink-sc-qgec4s-0.foCaAq.Link__StyledLink-sc-apj04t-0").text();
            String name2 = stock2.select(".hZYbiE.NameCell__StyledLink-sc-qgec4s-0.foCaAq.Link__StyledLink-sc-apj04t-0").text();

            //I use NumberFormat to convert the price from a string to a double with commas instead of dots
            String priceString1 = stock1.select(".hlZdvv.NumberCell__StyledFlexTableCell-sc-icuiuz-0.ecVIUb.Cell__StyledFlexbox-sc-icfddc-0.dcnKuH.Flexbox__StyledFlexbox-sc-1ob4g1e-0 > .fUxmLS.StyledTruncateTooltip-sc-4za66m-0.fSPWzG.TruncateWithTooltip__StyledDiv-sc-cceovq-0 > .ghiywe.Text__StyledTypography-sc-1thuey1-0.dIJvcf.Typography__StyledTypography-sc-10mju41-1.kFeDsT.Typography__Span-sc-10mju41-0").text();
            String priceString2 = stock2.select(".hlZdvv.NumberCell__StyledFlexTableCell-sc-icuiuz-0.ecVIUb.Cell__StyledFlexbox-sc-icfddc-0.dcnKuH.Flexbox__StyledFlexbox-sc-1ob4g1e-0 > .fUxmLS.StyledTruncateTooltip-sc-4za66m-0.fSPWzG.TruncateWithTooltip__StyledDiv-sc-cceovq-0 > .ghiywe.Text__StyledTypography-sc-1thuey1-0.dIJvcf.Typography__StyledTypography-sc-10mju41-1.kFeDsT.Typography__Span-sc-10mju41-0").text();
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number number = format.parse(priceString1);
            Number number2 = format.parse(priceString2);
            double price1 = number.doubleValue();
            double price2 = number2.doubleValue();

            String priceChange1 = stock1.select(".ecVIUb.Cell__StyledFlexbox-sc-icfddc-0.eDgWiq.Flexbox__StyledFlexbox-sc-1ob4g1e-0 > .fUxmLS.StyledTruncateTooltip-sc-4za66m-0.fSPWzG.TruncateWithTooltip__StyledDiv-sc-cceovq-0 > .ghiywe.Text__StyledTypography-sc-1thuey1-0.dIJvcf.Typography__StyledTypography-sc-10mju41-1.kFeDsT.Typography__Span-sc-10mju41-0 > .crKnUo.Development__StyledDevelopment-sc-hnn1ri-0").text();
            String PriceChange2 = stock2.select(".ecVIUb.Cell__StyledFlexbox-sc-icfddc-0.eDgWiq.Flexbox__StyledFlexbox-sc-1ob4g1e-0 > .fUxmLS.StyledTruncateTooltip-sc-4za66m-0.fSPWzG.TruncateWithTooltip__StyledDiv-sc-cceovq-0 > .ghiywe.Text__StyledTypography-sc-1thuey1-0.dIJvcf.Typography__StyledTypography-sc-10mju41-1.kFeDsT.Typography__Span-sc-10mju41-0 > .crKnUo.Development__StyledDevelopment-sc-hnn1ri-0").text();
            LocalDate pricedate = LocalDate.now();

            System.out.println("price1 = " + price1);
            System.out.println("priceChange1 = " + priceChange1);

            new StockPrice(price1, priceChange1, pricedate);
            new StockPrice(price2, PriceChange2, pricedate);
            stock1.ad

            bestPerformers.add(new Stock(name1));
            bestPerformers.add(new Stock(name2));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bestPerformers;
    }
    public List<Stock> findWorstStocks(){
        String url = "https://www.nordnet.dk/markedet/aktiekurser?sortField=diff_pct&sortOrder=ascending&exchangeCountry=US&exchangeList=us%3Anq";
        List<Stock> worstPerformers = new ArrayList<>();

        try{
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                    .get();
            Thread.sleep(1000); // 1 second

            Elements stock1 = doc.select("div.dnLFDN.Rows__AlignedRow-sc-1udgki9-0.bhUzoF.Row__StyledRow-sc-1iamenj-0.fBJmAV.Flexbox__StyledFlexbox-sc-1ob4g1e-0:nth-of-type(2)");
            Elements stock2 = doc.select("div.dnLFDN.Rows__AlignedRow-sc-1udgki9-0.bhUzoF.Row__StyledRow-sc-1iamenj-0.fBJmAV.Flexbox__StyledFlexbox-sc-1ob4g1e-0:nth-of-type(3)");
            String name1 = stock1.select(".hZYbiE.NameCell__StyledLink-sc-qgec4s-0.foCaAq.Link__StyledLink-sc-apj04t-0").text();
            String name2 = stock2.select(".hZYbiE.NameCell__StyledLink-sc-qgec4s-0.foCaAq.Link__StyledLink-sc-apj04t-0").text();
            worstPerformers.add(new Stock(name1));
            worstPerformers.add(new Stock(name2));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return worstPerformers;
    }

}
