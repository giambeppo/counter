package it.dellarciprete.counter.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

@Component
public class InMemoryCounterService implements CounterService {

    private final Map<String, AtomicLong> counters;

    public InMemoryCounterService() {
        counters = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void create(String counterName) {
        if (counters.containsKey(counterName)) {
            throw new DuplicateCounterException(counterName);
        } else {
            counters.put(counterName, new AtomicLong(0));
        }
    }

    @Override
    public long get(String counterName) {
        return getCounter(counterName).get();
    }

    @Override
    public long increment(String counterName) {
        return getCounter(counterName).incrementAndGet();
    }

    @Override
    public long decrement(String counterName) {
        return getCounter(counterName).decrementAndGet();
    }

    @Override
    public long set(String counterName, long newValue) {
        return getCounter(counterName).updateAndGet(oldValue -> newValue);
    }

    private AtomicLong getCounter(String counterName) {
        AtomicLong counter = counters.get(counterName);
        if (counter != null) {
            return counter;
        } else {
            throw new MissingCounterException(counterName);
        }
    }
}
