<ul>
  <li>Java - 17</li>
  <li>Spring boot - 3.4.0</li>  
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

### 2. What was the most useful feature that was added to the latest version of your chosen language? Please include a snippet of code that shows how you've used it.
**** I'd clean up the controller moving all private methods to service, I'd add more unit tests and I'd add a CI/CD pipeline in github.

### 3. What did you find most difficult?
*** I'd send the price in pound instead of pennies, add a Dockerfile to make create a docker-compose file easy with all apps.

### 4. What mechanism did you put in place to track down issues in production on this code? If you didn’t put anything, write down what you could do.
*** I've found difficult in make the code more clean.

### 5. Explain your interpretation of the list of requirements and what was delivered or not delivered and why
*** In general was a very rich experience. To implement that, I've need to remember a lot of concepts. I believe that with that implementation, you can know a lot about how I code.