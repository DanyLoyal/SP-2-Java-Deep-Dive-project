package Util;

import Model.Stock;
import Model.StockPrice;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StockSorterImpl {


    static StockSorter sortByPrice = stocks -> {
         return stocks
                .stream()
                .sorted(Comparator.comparing(stock ->
                    stock.getStockPrices().get(stock.getStockPrices().size()-1).getPrice()))
                 .collect(Collectors.toList());
    };

    static StockSorter sortByPriceChange = stocks -> {
        return stocks
                .stream()
                .sorted(Comparator.comparing(stock ->
                        stock.getStockPrices().get(stock.getStockPrices().size()-1).getPriceChange()))
                .collect(Collectors.toList());
    };

    static StockSorter sortByRiskDate = stocks -> {
        return stocks
                .stream()
                .sorted(Comparator.comparing(stock ->
                        stock.getStockRisks().get(stock.getStockRisks().size() -1).getRiskDate()))
                .collect(Collectors.toList());
    };

    static List<Stock> sortStocks(List<Stock> stocks, StockSorter sorter){
        return sorter.sort(stocks);
    }

}
