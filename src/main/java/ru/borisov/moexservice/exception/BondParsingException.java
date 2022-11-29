package ru.borisov.moexservice.exception;

public class BondParsingException extends RuntimeException{

    public BondParsingException(Exception ex) {
        super(ex);
    }
}
