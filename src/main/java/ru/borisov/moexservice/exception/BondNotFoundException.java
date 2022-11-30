package ru.borisov.moexservice.exception;

public class BondNotFoundException extends RuntimeException {

    public BondNotFoundException(String m) {
        super(m);
    }
}
