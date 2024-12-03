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

