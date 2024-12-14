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

### Baskets
```/api/baskets/create``` - **POST** - Create a new basket\
```/api/baskets/add/{id}``` - **PATCH** - Add an item into an already existing basket\
```/api/baskets/totalCostApplyingPromotion/{id}``` - **GET** - Return total cost applying promotions\
```/api/baskets/totalPromotion/{id}``` - **GET** - Returns the sum of promotions

### Products
```/api/products``` - **GET** - Return all products\
```/api/products/{id}``` - **GET** - Return a product by id

## Step by step to run the API

<ul>
  <li>Run the class SupermarketApplication</li>
  <li>Call the endpoint - http://localhost:8080/api/baskets/create -  POST </li>
  <li>Call the endoint below as many times as you want, whether or not you modify the endpoint body</li>
  <li>Call the endpoint - http://localhost:8080/baskets/add/1 -  PACTH </li>
  <li>Call the endpoint to calculate the total cost of promotions -  http://localhost:8080/api/baskets/totalCostApplyingPromotion/1 -  GET </li>
  <li>Call the endpoint to calculate the sun of promotions -  http://localhost:8080/api/baskets/totalPromotion/1 -  GET </li>
</ul>

### Json referring to products added to the basket 

#### Pizza!
```json
{
    "id": "Dwt5F7KAhi",
    "name": "Amazing Pizza!",
    "price": 1099,
    "promotions": [
       {
          "id": "ibt3EEYczW",
          "type": "QTY_BASED_PRICE_OVERRIDE",
          "required_qty": 2,
          "price": 1799
      }
    ]
}
```
#### Burger!
```json
{
  "id": "PWWe3w1SDU",
  "name": "Amazing Burger!",
  "price": 999,
  "promotions": [
    {
      "id": "ZRAwbsO2qM",
      "type": "BUY_X_GET_Y_FREE",
      "required_qty": 2,
      "free_qty": 1
    }
  ]
}
```

#### Salad!
```json
{
  "id": "C8GDyLrHJb",
  "name": "Amazing Salad!",
  "price": 499,
  "promotions": [
    {
      "id": "Gm1piPn7Fg",
      "type": "FLAT_PERCENT",
      "amount": 10
    }
  ]
}
```

#### Boring Fries!
```json
{
  "id": "4MB7UfpTQs",
  "name": "Boring Fries!",
  "price": 199,
  "promotions": []
}
```

# Resources
1) Inside the directory src\main\resources\postman\collections you find the endpoints made in postman that help.\
2) Inside the directory src\main\resources\mockApi you will find a mock-api.zip file that you should unzip and read the README. This way, you will be able to call the endpoints that return a list of products or the details of a product.

# Follow-up questions
### 1. How long did you spend on the test? What would you add if you had more time?
I have spent around 15 hours.
I would add continuous integration testing and performance testing. After a conversation with sales, I would start modeling the database for a BI solution.

### 2. What was the most useful feature that was added to the latest version of your chosen language? Please include a snippet of code that shows how you've used it.
I used the SOLID resource - OCP — Open-Closed Principle. It was very helpful to see which promotion I should apply for.

```java
 
// The interface represents the common behavior that will be implemented by the different promotion classes. 
public interface PromotionService {    
    double applyDiscount(Product product, Promotion promotion, int quantity);
}

// The concrete classes represent the different types of promotions
@Service
public class QtyBasedPriceOverrideServiceImpl implements PromotionService {
    @Override
    public double applyDiscount(Product product, Promotion promotion, int quantity) {
        Integer requiredQty = promotion.getRequiredQty();

        if (quantity >= requiredQty) {
            int fullLots = quantity / requiredQty;
            int remainder = quantity % requiredQty;
            double discountedPrice = fullLots * promotion.getPrice();
            double regularPrice = remainder * product.getPrice();
            return discountedPrice + regularPrice;
        }
        return product.getPrice() * quantity;
    }
}
```
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
  <li>Integrate with WireMockServer. For unit testing and to query the products as well as the details of each one of them. <strong>Done</strong></li>
  <li>New prices and promotions could be inserted. <strong>Done</strong>, but with a caveat. If there is another type of promotion, the system will not calculate it.</li>
  <li>Prices should be indicated in cents. <strong>Done</strong></li>
</ul>

