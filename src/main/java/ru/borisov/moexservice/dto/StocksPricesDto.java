package ru.borisov.moexservice.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Value
public class StocksPricesDto {
    private List<StockPrice> prices;
}
