package it.dellarciprete.counter.service;

public class MissingCounterException extends IllegalStateException {

    MissingCounterException(String counterName) {
        super("Counter " + counterName + " does not exist.");
    }
}
