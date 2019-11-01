package it.dellarciprete.counter.service;

public interface CounterService {

    void create(String counterName);

    void delete(String counterName);

    long get(String counterName);

    long increment(String counterName);

    long decrement(String counterName);

    long set(String counterName, long newValue);

}
