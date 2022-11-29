package ru.borisov.moexservice.parser;

import ru.borisov.moexservice.dto.BondDto;

import java.util.List;

public interface Parser {

    List<BondDto> parse(String ratesAsString);
}
