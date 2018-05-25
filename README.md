## Example REST Microservices, using Java 8, AWS Lambda, and Amazon DynamoDB 

This code is a Java application based on:

- [Serverless Architectures with Java 8, AWS Lambda, and Amazon DynamoDB — Part 1](https://aws.amazon.com/blogs/startups/serverless-architectures-with-java-8-aws-lambda-and-amazon-dynamodb-part-1/).
- [Serverless Architectures with Java 8, AWS Lambda, and Amazon DynamoDB — Part 2](https://aws.amazon.com/blogs/startups/serverless-architectures-with-java-8-aws-lambda-and-amazon-dynamodb-part-2/).

In summary, we are looking at:

* An EVENTS table in a DynamoDB (DynamoDB = AWS's own scalable NoSQL database)
* A Java 8 application, desgined to be deployed as 5 AWS lambda functions, that query, create, update and delete the EVENTS table
* Serveral steps to setup the database, create the lambda functions and create the REST API in AWS


### Requirements
* Java 8
* Maven
* AWS Account
* Knowledge of basic AWS services, roles, permissions, etc

### How To Use

1. Build the project
    1. mvn clean install
    2. the build produces a jar
2. In AWS, DynmaoDB Service
    1. Create the EVENT table, as described in the links above
3. In AWS, Lambda Service
    1. Create 5 new Java 8 Lambda functions
    2. Each function should correspond to a method in the EventFunctions class
    3. Upload the jar (produced in step 1) for each function 
4. In AWS, API Gateway Service
    1. Create a new "Events" API
    2. Create resources in the API (eg events/list/city/{cityName} and events/list/team/{teamName})
    3. Create methods in the API (eg get, post and delete under the appropriate resources)
    4. If using path parameters, add body mapping templates in the method's integration request to map the parametrs to the lambda functions expected JSON 
    5. Create a stage for the API
    6. Deploy the "Events" API to the stage
5. Test the REST API using Postman tool


