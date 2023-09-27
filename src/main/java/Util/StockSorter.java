package Util;

import Model.Stock;

import java.util.List;

@FunctionalInterface
public interface StockSorter {

    List<Stock> sort(List<Stock> stock);
}
