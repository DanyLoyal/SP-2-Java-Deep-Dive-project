package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockRiskDTO {

    private double susEsgRiskScore;
    private String susEsgRiskCategory;
    private String asOfDate;
    private String subIndustry;
}
