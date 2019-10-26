package it.dellarciprete.counter.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.dellarciprete.counter.service.CounterService;
import it.dellarciprete.counter.service.DuplicateCounterException;
import it.dellarciprete.counter.service.MissingCounterException;

@RestController
public class CounterController {

    private final CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @RequestMapping(method = GET, path = "/{counterName}")
    public long get(@PathVariable String counterName) {
        return counterService.get(counterName);
    }

    @RequestMapping(method = PUT, path = "/{counterName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable String counterName) {
        counterService.create(counterName);
    }

    @RequestMapping(method = GET, path = "/{counterName}/+")
    public long increment(@PathVariable String counterName) {
        return counterService.increment(counterName);
    }

    @RequestMapping(method = GET, path = "/{counterName}/-")
    public long decrement(@PathVariable String counterName) {
        return counterService.decrement(counterName);
    }

    @RequestMapping(method = POST, path = "/{counterName}")
    public long set(@PathVariable String counterName, @RequestBody long newValue) {
        return counterService.set(counterName, newValue);
    }

    @ExceptionHandler({MissingCounterException.class})
    public ResponseEntity<String> missingCounterHandler(MissingCounterException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DuplicateCounterException.class})
    public ResponseEntity<String> duplicateCounterHandler(DuplicateCounterException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}