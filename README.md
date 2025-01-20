# Tool Depot Checkout Microservice

[Tool Depot Rental Checkout Microservice Software Design Document](ToolDepotRentalCheckoutMicroserviceSDD.pdf)

## Build requirements
- JDK 21
- Maven 3.6.x or later

## Build & unit test instructions

From command line at the root of the project, run the following command:

```mvn clean install```

If the build and unit tests are successful, then the build jar will be created in the target directory.

## To run checkout microservice
First build the jar using the build instructions.

Then run the following command from the root of the project:

```java -jar target/checkout-service-0.0.1-SNAPSHOT.jar```

## To access test UI

Build and run the microservice.

Open a browser and navigate to:

```http://localhost:8100/ui/checkout```

## REST Endpoints

Inputs and outputs can be found in the [SDD](ToolDepotRentalCheckoutMicroserviceSDD.pdf).

### Checkout

POST http://localhost:8100/api/rentals

### Find all tools

GET http://localhost:8100/api/tools

### Get tool

GET http://localhost:8100/api/tools/{toolCode}
