package Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "industry")
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "industry", fetch = FetchType.EAGER)
    private Set<Stock> stocks = new HashSet<>();

    public Industry(String name){
        this.name = name;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }
}
