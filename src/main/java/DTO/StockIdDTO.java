package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@Getter
public class StockIdDTO {

    private int count;
    private int pages;
    private List<ApiResult> results;
}
