1. Getting Started
## Clone the Repository

git clone https://github.com/your-username/rule-engine-ast.git
cd rule-engine-ast

## Install Dependencies

Use Maven to build the project and download dependencies:

mvn clean install

## Database Setup

Ensure MySQL is running locally.

Create a database for this project:

CREATE DATABASE ruleengineapp;
=============================================================================
2. Project Structure
The project is organized as follows:

ast/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── ruleengine/
│   │   │           ├── RuleEngineApplication.java
│   │   │           ├── controller/
│   │   │           │   └── RuleController.java
│   │   │           ├── model/
│   │   │           │   ├── Node.java
│   │   │           │   └── Rule.java
│   │   │           ├── service/
│   │   │           │   └── RuleService.java
│   │   │           └── repository/
│   │   │               └── RuleRepository.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           └── index.html   <!-- Place your HTML file here -->
│   └── test/
│       └── java/
│           └── com/
│               └── ruleengine/
│                   └── RuleEngineApplicationTests.java
│
└── pom.xml
====================================================================================

3. Configuration

Edit the src/main/resources/application.properties file to configure database settings:

spring.datasource.url=jdbc:mysql://localhost:3306/ruleenginapp
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=8080
=====================================================================================

4. Running the Application
## Start the Spring Boot Application

mvn spring-boot:run

## Verify Table Creation in MySQL

Check if the rules table is created in ruleengineapp.

## Access the Application UI

Open your browser and go to http://localhost:8080/index.html.
====================================================================================

5. API Endpoints and Sample Test Cases
The application has three main API endpoints, which you can test using Postman.

Endpoint 1: Create Rule
Method: POST
URL: http://localhost:8080/api/rules/create
Body (JSON):
{
  "ruleString": "((age > 30 AND department = 'Marketing') AND (salary > 20000 OR experience > 5))"
}

Response:

{
  "id": 1,
  "ruleString": "((age > 30 AND department = 'Marketing') AND (salary > 20000 OR experience > 5))",
  "createdAt": "2024-10-26T16:49:05.6941412"
}

Endpoint 2: Combine Rules
Method: POST
URL: http://localhost:8080/api/rules/combine
Body (JSON):
{
  "rules": [
    "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing'))",
    "((age > 30 AND department = 'Marketing') AND (salary > 20000 OR experience > 5))"
  ]
}

Response (sample combined AST root node):

{
  "type": "operator",
  "value": "AND",
  "left": { ... },
  "right": { ... }
}

Endpoint 3: Evaluate Rule
Method: POST
URL: http://localhost:8080/api/rules/evaluate/{id}
Path Variable: id - ID of the rule to evaluate (e.g., 1)
Body (JSON):
{
  "age": 35,
  "department": "Sales",
  "salary": 60000,
  "experience": 3
}
Response:

{
  "evaluationResult": true
}
========================================================================================
6. UI Access
Open http://localhost:8080/index.html in your browser.

The UI provides an interface to:
a) Create a new rule.
b) Combine multiple rules.
c) Evaluate a rule by entering specific user data attributes.
==========================================================================================

7. Troubleshooting
Database Connection Errors: Ensure MySQL is running, and database credentials in application.properties are correct.
Port Conflicts: Change the server.port in application.properties if 8080 is already in use.