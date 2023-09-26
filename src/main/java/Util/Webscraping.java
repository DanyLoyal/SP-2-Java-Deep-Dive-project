package Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Webscraping {

    public static void main(String[] args) {
        Webscraping webscraping = new Webscraping();
        Map<String, List<String>> stockURLs = webscraping.stockURLCreator();

    }

    //Method that returns a list of the 2 best and 2 worst performing stocks of the day
    public Map<String, List<String>> stockURLCreator(){
        String url = "https://www.nasdaq.com/market-activity/stocks";
        List<String> bestPerformers = new ArrayList<>();
        List<String> worstPerformers = new ArrayList<>();
        Map<String, List<String>> stockURLs = new HashMap<>();
        
        try{
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36")
                    .get();
            Thread.sleep(1000); // 1 second



        }
        catch (Exception e){
            e.printStackTrace();
        }

        return stockURLs;
    }

}
