package Util;

import DTO.StockIdDTO;
import DTO.StockRiskDTO;
import Model.Industry;
import Model.Stock;
import Exceptions.ApiException;
import Model.StockRisk;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

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
            StockIdDTO dto = gson.fromJson(response.body().string(), StockIdDTO.class);
            if(dto.getResults().size() != 0) {
                stock.setId(dto.getResults().get(0).getPerformanceId());
            } else {
                Random r = new Random();
                stock.setId(stock.getName() + r.nextInt(101));
            }
        } catch (IOException e){
            throw new ApiException("Something went wrong with the API request: " + e.getMessage());
        }
        return stock;
    }

    public Stock getRiskForStock(Stock stock) throws ApiException{
        Request request = new Request.Builder()
                .url("https://morning-star.p.rapidapi.com/stock/v2/get-risk-rating-assessment?performanceId=" + stock.getId())
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "morning-star.p.rapidapi.com")
                .build();
        try{
            Response response = client.newCall(request).execute();
            StockRiskDTO dto = gson.fromJson(response.body().string(), StockRiskDTO.class);
            if(dto.getSusEsgRiskCategory() != null && dto.getSusEsgRiskScore() != 0 && dto.getAsOfDate() != null && dto.getSubIndustry() != null) {
                StockRisk sr = new StockRisk(dto.getSusEsgRiskScore(), dto.getSusEsgRiskCategory(), LocalDate.of(Integer.parseInt(dto.getAsOfDate().substring(0, 4)), Integer.parseInt(dto.getAsOfDate().substring(5, 7)), Integer.parseInt(dto.getAsOfDate().substring(8, 10))));
                stock.addStockRisk(sr);
                stock.setIndustry(new Industry(dto.getSubIndustry()));
            } else {
                StockRisk sr = new StockRisk(0, "Doesn't exist", LocalDate.now());
                stock.addStockRisk(sr);
                if(dto.getSubIndustry() != null) {
                    stock.setIndustry(new Industry(dto.getSubIndustry()));
                } else {
                    stock.setIndustry(new Industry("Unknown industry"));
                }
            }
        } catch (IOException e){
            throw new ApiException("Something went wrong with the API request: " + e.getMessage());
        }
        return stock;
    }

}
