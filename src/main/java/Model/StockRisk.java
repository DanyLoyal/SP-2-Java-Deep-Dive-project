package Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@NamedQueries({
@NamedQuery(name = "StockRisk.RetrieveAllRisks", query = "SELECT s FROM StockRisk s "),
})
@Table(name = "stock_risk")
public class StockRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "rating")
    private double rating;

    @Column(name = "profile")
    private String profile;

    @Column(name = "risk_date")
    @Temporal(TemporalType.DATE)
    private LocalDate riskDate;

    @ManyToOne
    private Stock stock;

    public StockRisk(double rating, String profile, LocalDate date){
        this.rating = rating;
        this.profile = profile;
        this.riskDate = date;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
