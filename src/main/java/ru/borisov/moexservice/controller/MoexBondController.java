package ru.borisov.moexservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisov.moexservice.dto.FigiesDto;
import ru.borisov.moexservice.dto.StocksDto;
import ru.borisov.moexservice.dto.StocksPricesDto;
import ru.borisov.moexservice.dto.TickersDto;
import ru.borisov.moexservice.service.BondService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonds")
public class MoexBondController {

    private final BondService bondService;

    @PostMapping("/getBondsByTickers")
    public StocksDto getBondsFromMoex(@RequestBody TickersDto tickersDto) {
        return bondService.getBondsFromMoex(tickersDto);
    }

    @PostMapping("/prices")
    public StocksPricesDto getPricesByFigies(@RequestBody FigiesDto figiesDto) {
        return bondService.getPricesByFigies(figiesDto);
    }
}
