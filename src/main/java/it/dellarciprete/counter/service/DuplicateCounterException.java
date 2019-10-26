package it.dellarciprete.counter.service;

public class DuplicateCounterException extends IllegalStateException {

    DuplicateCounterException(String counter) {
        super("Counter " + counter + " already exists.");
    }
}
