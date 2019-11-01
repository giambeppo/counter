package it.dellarciprete.counter.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.dellarciprete.counter.service.CounterService;
import it.dellarciprete.counter.service.DuplicateCounterException;
import it.dellarciprete.counter.service.MissingCounterException;

@RestController
public class CounterController {

    private static final String INCREMENT = "increment";
    private static final String DECREMENT = "decrement";
    private static final String SET = "set";

    private final CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @RequestMapping(method = GET, path = "/{counterName}")
    public long get(@PathVariable String counterName, @RequestParam(required = false) String after, @RequestParam(required = false) Long newValue) {
        if (StringUtils.isEmpty(after)) {
            return counterService.get(counterName);
        } else {
            switch (after) {
            case INCREMENT:
                return counterService.increment(counterName);
            case DECREMENT:
                return counterService.decrement(counterName);
            case SET:
                if (newValue != null) {
                    return counterService.set(counterName, newValue);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameter 'newValue' is mandatory when using 'after=set'");
                }
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unrecognized value for parameter 'after': " + after);
            }
        }
    }

    @RequestMapping(method = PUT, path = "/{counterName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable String counterName) {
        counterService.create(counterName);
    }

    @RequestMapping(method = DELETE, path = "/{counterName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String counterName) {
        counterService.delete(counterName);
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