# tl1124 - Tool Depot Checkout Service

## Build & unit test instructions

From command line at the root of the project, run the following command:

```mvn clean install```

If the build and unit tests are successful, then the build jar will be created in the target directory.

## To run checkout service
First build the jar using the build instructions.

Then run the following command from the root of the project:

```java -jar target/checkout-service-0.0.1-SNAPSHOT.jar```

## To access test UI

Build and run the service.

Open a browser and navigate to:

```http://localhost:8100/ui/checkout```

