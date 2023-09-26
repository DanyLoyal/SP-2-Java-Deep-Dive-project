package Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "stock")
public class Stock {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "stock", fetch = FetchType.EAGER)
    private List<StockPrice> stockPrices = new ArrayList<>();

    @OneToMany(mappedBy = "stock")
    private List<StockRisk> stockRisks = new ArrayList<>();

    @ManyToOne
    private Industry industry;

    public Stock (String id, String ticker, String name){
        this.id = id;
        this.ticker = ticker;
        this.name = name;
    }

    public void addStockPrice(StockPrice sp){
        if(sp != null) {
            stockPrices.add(sp);
            sp.setStock(this);
        }
    }

    public void addStockRisk(StockRisk sr){
        if(sr != null){
            stockRisks.add(sr);
            sr.setStock(this);
        }
    }

    public void setIndustry(Industry i){
        if(i != null) {
            this.industry = i;
            i.addStock(this);
        }
    }
}
