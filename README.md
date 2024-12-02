## Technologies used

<ul>
  <li>Java - 17</li>
  <li>Spring boot - 3.4.0</li>
  <li>Junit - 5.11.3</li>
  <li>Mockito - 5.14.2</li>
  <li>Log4j - 2.20.0</li>
  <li>Lombok</li>
  <li>Wiremock</li>
  <li>Running in port 8080</li>
</ul>

## Endpoints

```/baskets/create``` - **POST** - Create a new basket\
```/baskets/add/{id}``` - **PATCH** - Add an item into an already existing basket\
```/baskets/totalCostApplyingPromotion/{id}``` - **GET** - Return total cost applying promotions\
```/totalPromotion/{id}``` - **GET** - Returns the sum of promotions\


# Follow-up questions
### 1. How long did you spend on the test? What would you add if you had more time?
I have spend around 15 hours.
I would add continuous integration testing, performance testing and after a conversation with sales, I would start modeling the database for a BI solution.

### 2. What was the most useful feature that was added to the latest version of your chosen language? Please include a snippet of code that shows how you've used it.
**** I'd clean up the controller moving all private methods to service, I'd add more unit tests and I'd add a CI/CD pipeline in github.

### 3. What did you find most difficult?
WireMockServer Configuration.

### 4. What mechanism did you put in place to track down issues in production on this code? If you didn’t put anything, write down what you could do.
I configured Log4j and put some logs in the application

### 5. Explain your interpretation of the list of requirements and what was delivered or not delivered and why
Develop a supermarket checkout system, where:
<ul>
  <li>The customer could add items, in any order. <strong>Done</strong></li>
  <li>Each item may or may not have a promotion. If so, it should be applied. <strong>Done</strong></li>  
  <li>The system should display the amount the customer saved. <strong>Done</strong></li>
  <li>New prices and promotions could be inserted. <strong>Done</strong>, but with a caveat. If there is another type of promotion, the system will not calculate it.</li>
  <li>Prices should be indicated in cents. <strong>Done</strong></li>
</ul>

