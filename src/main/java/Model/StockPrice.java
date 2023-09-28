package Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "stock_price")
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private double price;

    @Column(name = "price_change")
    private String priceChange;

    @Column(name = "price_date")
    @Temporal(TemporalType.DATE)
    private LocalDate priceDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Stock stock;

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setDate(LocalDate date){
        this.priceDate = date;
    }

    public StockPrice(double price, String priceChange, LocalDate priceDate) {
        this.price = price;
        this.priceChange = priceChange;
        this.priceDate = priceDate;
    }

    @Override
    public String toString() {
        return "StockPrice{" +
                "price=" + price +
                ", priceChange='" + priceChange + '\'' +
                ", priceDate=" + priceDate +
                '}';
    }
}
