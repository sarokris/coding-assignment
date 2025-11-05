# Backend Assessment

## Application

We would like you to create a java-based backend application using REST.
It should contain the following endpoints.
* GET /api/interest-rates (get a list of current interest rates)
* POST /api/mortgage-check (post the parameters to calculate for a mortgage check)

The list of current mortgage rates should be created in memory on application startup.
The mortgage rate object contains the fields: 

* maturityPeriod (integer)
* interestRate (Percentage)
* lastUpdate (Timestamp)

The posted data for the mortgage check contains at least the fields;
* income (Amount)
* maturityPeriod (integer)
* loanValue (Amount)
* homeValue (Amount)

The mortgage check return if the mortgage is feasible (boolean) and the montly costs
(Amount) of the mortgage.

## Business rules that apply are
- a mortgage should not exceed 4 times the income
- a mortgage should not exceed the home value

Use the frameworks as you see fit to build and test this.

## Implementation
Treat this application as a real MVP that should go to production.

## Duration
This assignment should take 4-5 hours at the most.

# Mortgage Application

A Spring Boot application to calculate mortgage payments based on user input and interest rates stored in the database.

---

## Table of Contents

- [API Documentation](#api-documentation)
- [Running Locally](#running-locally)
- [Running with Docker Compose](#running-with-docker-compose)

---

## API Documentation

You can access the Swagger UI for interactive API documentation:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

## Running Locally

### Prerequisites

- Java 17+
- Maven 3+
- H2 database (for local development)

### Steps

1. Clone the repository:

```bash
git clone https://github.com/sarokris/coding-assignment/tree/feature/mortgage-check
cd mortgage
```

## Running Locally with Docker Compose

```
docker-compose build
docker-compose up

```


