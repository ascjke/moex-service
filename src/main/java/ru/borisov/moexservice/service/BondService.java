package ru.borisov.moexservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.borisov.moexservice.dto.BondDto;
import ru.borisov.moexservice.dto.StocksDto;
import ru.borisov.moexservice.dto.TickersDto;
import ru.borisov.moexservice.exception.LimitRequestsException;
import ru.borisov.moexservice.model.Currency;
import ru.borisov.moexservice.model.Stock;
import ru.borisov.moexservice.moexclient.CorporateBondsClient;
import ru.borisov.moexservice.moexclient.GovBondsClient;
import ru.borisov.moexservice.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BondService {

    private final CorporateBondsClient corporateBondsClient;
    private final GovBondsClient govBondsClient;
    private final Parser parser;

    public StocksDto getBondsFromMoex(TickersDto tickersDto) {
        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(getCorporateBonds());
        allBonds.addAll(getGovBonds());

        List<BondDto> resultBonds = allBonds.stream()
                .filter(b -> tickersDto.getTickers().contains(b.getTicker()))
                .collect(Collectors.toList());

        List<Stock> stocks = resultBonds.stream()
                .map(b -> {
                    return Stock.builder()
                            .ticker(b.getTicker())
                            .name(b.getName())
                            .figi(b.getTicker())
                            .type("Bond")
                            .currency(Currency.RUB)
                            .source("MOEX")
                            .build();
                })
                .collect(Collectors.toList());

        return new StocksDto(stocks);
    }

    public List<BondDto> getCorporateBonds() {
        log.info("Getting corporate bonds from Moex");
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> corporateBondDtos = parser.parse(xmlFromMoex);
        if (corporateBondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting corporate bonds.");
            throw new LimitRequestsException("Moex isn't answering for getting corporate bonds.");
        }

        return corporateBondDtos;
    }

    public List<BondDto> getGovBonds() {
        log.info("Getting government bonds from Moex");
        String xmlFromMoex = govBondsClient.getBondsFromMoex();
        List<BondDto> govBondDtos = parser.parse(xmlFromMoex);
        if (govBondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting government bonds.");
            throw new LimitRequestsException("Moex isn't answering for getting government bonds.");
        }

        return govBondDtos;
    }

}
