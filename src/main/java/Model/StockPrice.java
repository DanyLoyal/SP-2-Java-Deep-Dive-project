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

    @ManyToOne
    private Stock stock;
}
