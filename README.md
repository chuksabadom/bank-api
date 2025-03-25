### Instructions

1. **Clone the Repository**:
 ```bash
   git clone https://github.com/chuksabadom/bank-api.git
   cd bank-api
 ```

2. **Build the Application**:
```bash
   ./gradlew clean build
```

3. **Run Tests**:
```bash
   ./gradlew test
```

4. **Run the Application**:
```bash
   ./gradlew bootRun
```

5. **Test the API**:
- Use the frontend at `http://localhost:8080/index.html`.
- Check data.sql for the customer insert queries.
- To get a customer from the already existing list of customers, you can either:
  - query the H2 database (see 6) for the list of customers or  
  - use the following endpoint to get a list of customers: `http://localhost:8080/api/v1/customers` or
  - using swagger-ui (see 7), you can run the 'GET api/v1/customers' endpoint to get a list of customers.
- Using the customerId from the list of customers, create an account for the customer using an initial credit of your choice.

6. **Access H2 DB**:
To access H2 console during development: `http://localhost:8080/h2-console`
Use JDBC URL: `jdbc:h2:mem:bankdb`

7. **Access the Swagger UI**:
- With the application running, visit: `http://localhost:8080/swagger-ui/index.html`
- You can also check the raw API docs at: `http://localhost:8080/v3/api-docs`
