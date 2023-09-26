package Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "stock")
@ToString
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "stock")
    private List<StockPrice> stockPrices = new ArrayList<>();

    @OneToMany(mappedBy = "stock")
    private List<StockRisk> stockRisks = new ArrayList<>();

    @ManyToOne
    private Industry industry;

    public Stock(String name) {
        this.name = name;
    }
}
