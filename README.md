# Counter.io
A simple counter service.

Allows to create and update counters that are persisted and can be shared.

## Build and run
Build with ```mvn package```

Run with ```java -jar target/counter-1.0-SNAPSHOT.jar```

## Available methods

### Create a counter
* `PUT` http://localhost:8080/myCounter

Returns `HTTP 201` if successful, `HTTP 409` if the counter exists already.

New counters are initialized with value 0.

### Get a counter value
* `GET` http://localhost:8080/myCounter

Returns the value of the counter, or `HTTP 404` if the counter does not exist.

### Update a counter value
All the following methods return `HTTP 404` if the counter does not exist.

#### Increase a counter value
* `GET` http://localhost:8080/myCounter/+
* `GET` http://localhost:8080/myCounter?after=increment

Increment the value of a counter by 1, returning the new value.

#### Decrease a counter value
* `GET` http://localhost:8080/myCounter/-
* `GET` http://localhost:8080/myCounter?after=decrement

Decrement the value of a counter by 1, returning the new value.

#### Set a specific counter value
* `POST` http://localhost:8080/myCounter
* `GET` http://localhost:8080/myCounter?after=set&newValue=666

Sets the counter value to the long provided in the body or in the query parameter, respectively, and returns it. 

### Delete a counter
* `DELETE` http://localhost:8080/myCounter

Returns `HTTP 204` if successful, or `HTTP 404` if the counter does not exist.
