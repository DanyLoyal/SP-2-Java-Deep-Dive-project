package Util;

import DTO.StockIdDTO;
import Model.Stock;
import Exceptions.ApiException;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class StockAPIEnricher {

    private static StockAPIEnricher instance;

    public static StockAPIEnricher getInstance(){
        if(instance != null){
            return instance;
        }
        return new StockAPIEnricher();
    }

    private OkHttpClient client = new OkHttpClient();
    private String apiKey = System.getenv("APIKEY");
    private Gson gson = new Gson();

    public Stock getIdForStock(Stock stock) throws ApiException{
        Request request = new Request.Builder()
                .url("https://morning-star.p.rapidapi.com/market/v2/auto-complete?q=" + stock.getName())
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "morning-star.p.rapidapi.com")
                .build();
        try {
            Response response = client.newCall(request).execute();
            stock.setId(gson.fromJson(response.body().string(), StockIdDTO.class).getResults().get(0).getPerformanceId());
        } catch (IOException e){
            throw new ApiException("Something went wrong with the API request: " + e.getMessage());
        }
        return stock;
    }

}
