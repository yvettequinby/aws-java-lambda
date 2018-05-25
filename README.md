## Example REST Microservices, using Java 8, AWS Lambda, and Amazon DynamoDB 

This code is a Java application based on:

- [Serverless Architectures with Java 8, AWS Lambda, and Amazon DynamoDB — Part 1](https://aws.amazon.com/blogs/startups/serverless-architectures-with-java-8-aws-lambda-and-amazon-dynamodb-part-1/).
- [Serverless Architectures with Java 8, AWS Lambda, and Amazon DynamoDB — Part 2](https://aws.amazon.com/blogs/startups/serverless-architectures-with-java-8-aws-lambda-and-amazon-dynamodb-part-2/).

The Java application can be built as a Jar and deployed as 5 AWS lambda functions.

The steps to take are as follows:

(1) Build the project (mvn clean install)
(2) In AWS, create the DynamoDB EVENT table, as described in the links above
(3) In AWS, create 5 new Java8 lambda functions (one for each method in the EventFunctions class), uploading the Jar (built in step 1) for each function. 
(4) In AWS, create a new "Events" API, in the API Gateway service.
(5) In AWS (API Gateway), create the desired resources and methods. If using path parameters, use body mapping templates in the method's integration request to map the parametrs to the lambda functions expected JSON.
(6) In AWS (API Gateway), create a stage for the API.
(7) In AWS (API Gateway), deploy the API to the stage.

