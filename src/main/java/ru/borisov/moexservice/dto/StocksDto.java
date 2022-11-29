package ru.borisov.moexservice.dto;

import lombok.Value;
import ru.borisov.moexservice.model.Stock;

import java.util.List;

@Value
public class StocksDto {

    List<Stock> stocks;
}
