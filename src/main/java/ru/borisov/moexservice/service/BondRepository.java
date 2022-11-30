package ru.borisov.moexservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.borisov.moexservice.dto.BondDto;
import ru.borisov.moexservice.exception.LimitRequestsException;
import ru.borisov.moexservice.moexclient.CorporateBondsClient;
import ru.borisov.moexservice.moexclient.GovBondsClient;
import ru.borisov.moexservice.parser.Parser;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BondRepository {

    private final CorporateBondsClient corporateBondsClient;
    private final GovBondsClient govBondsClient;
    private final Parser parser;

    @Cacheable(value = "corps")
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

    @Cacheable(value = "govs")
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
