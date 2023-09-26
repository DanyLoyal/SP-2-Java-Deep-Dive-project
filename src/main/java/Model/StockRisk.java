package Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "stock_risk")
public class StockRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rating")
    private int rating;

    @Column(name = "profile")
    private String profile;

    @Column(name = "risk_date")
    @Temporal(TemporalType.DATE)
    private LocalDate riskDate;

    @ManyToOne
    private Stock stock;

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
