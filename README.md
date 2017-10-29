# Restaurant Reservation System [![CircleCI](https://circleci.com/gh/MatanRubin/restaurant-reservations/tree/master.svg?style=svg)](https://circleci.com/gh/MatanRubin/restaurant-reservations/tree/master)

## Overview
This is a simple restaurant reservation system written for eductional purposes - 
practicing building web services using Java's Spring/Sprint Boot and Hibernate. 
Most endpoints are simple CRUD operations.  
The application exposes a REST API to be used by someone who manages reservations
for multiple restaurants. The application allows adding/removing restaurants, 
editing the tables for each restaurant, and creating and manipulating reservations
in restaurants.

The API is documented using swagger-ui.

Technologies used:
* Spring 4 with Spring Boot
* Hibernate
* H2 in-memory database
* Swagger and Swagger UI
* Tomcat
* JUnit
* Mockito
* Project Lombok


## Running the Project
To compile the project, issue the following command: 

```
mvn clean install
```

To run all tests, issue the following command: 

```
mvn test
```

After compiling the project you may run it using:

```
java -jar ./target/restaurant-reservations-0.0.1-SNAPSHOT.jar
```

The application should now be available on `localhost:8080`.
You can now browse the documentaion at `localhost:8080/swagger-ui.html`.

## Example Scenario
Let's start by looking at the available restaurants:

```
GET  localhost:8080/api/restaurants/

{
    "restaurants": [
        {
            "id": 1,
            "name": "Zozobra",
            "address": "Shenkar St.",
            "tableDtos": [
                {
                    "id": 1,
                    "name": "Round Table",
                    "capacity": 6
                }
            ]
        },
        {
            "id": 2,
            "name": "Moses",
            "address": "Aba Even St.",
            "tableDtos": [
                {
                    "id": 2,
                    "name": "Table Table",
                    "capacity": 4
                }
            ]
        }
    ]
}
```

Let's see which tables are available at Zozobra:

```
GET localhost:8080/api/restaurants/1/tables

{
    "tables": [
        {
            "id": 1,
            "name": "Round Table",
            "capacity": 6
        }
    ]
}
``` 

Let's make a reservation for the Round Table at Zozobra:

```
POST localhost:8080/api/restaurants/1/reservations
Request body:
{
	"name": "Rubin",
	"startTime": "2007-12-03T19:00:00",
	"endTime": "2007-12-03T21:00:00",
	"tableName": "Round Table",
	"nPeople": 3
}
```

Similarly, you can try adding/removing/listing some tables, restaurats or reservations.
