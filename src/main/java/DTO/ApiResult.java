package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResult {
    private String id;
    private String name;
    private String description;
    private String exchange;
    private String performanceId;
    private String securityType;
    private String ticker;
    private String type;
    private String url;
}
