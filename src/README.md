# Resin Jewelry Store

## Description
Resin Jewelry Store is a REST API built with Java and Spring Boot. It manages customers, products, and orders for an online resin jewelry shop. Customers can browse products, add them to a cart, and checkout orders. The backend follows best practices for REST APIs and persists data in a MySQL database.

---

## Class Diagram 

```
             ┌────────────┐
             │  Product   │
             │------------│
             │ id: Long   │
             │ name: String │
             │ price: double│
             │ category: ProductCategory │
             └────────────┘
                   ▲
   ┌───────────────┼───────────────┐
   │               │               │
┌─────────┐   ┌─────────┐   ┌─────────┐
│Necklace │   │Ring     │   │Bracelet │
└─────────┘   └─────────┘   └─────────┘
                   │
                 Earrings

┌──────────────┐      ┌──────────────┐
│  Customer    │      │    Order     │
│--------------│      │--------------│
│ id: Long     │      │ id: Long     │
│ name: String │      │ customer: Customer │
│ email: String│      │ products: List<Product> │
│ cart: List<Product>│ │ totalAmount: Double │
└──────────────┘      │ status: OrderStatus │
                      │ orderDate: LocalDateTime │
                      └──────────────┘
```

---

## Use Case Diagram 

```
          +-----------------------+
          |      Customer         |
          +-----------------------+
                 |         ^
                 |         |
                 v         |
      +------------------+ |
      | Browse Products   | |
      +------------------+ |
                 |         |
                 v         |
      +------------------+ |
      | Add to Cart       | |
      +------------------+ |
                 |         |
                 v         |
      +------------------+ |
      | Remove from Cart  | |
      +------------------+ |
                 |         |
                 v         |
      +------------------+ |
      | Checkout Cart     | |
      +------------------+ |
                 | 
                 v
           +----------------+
           |   Order Placed |
           +----------------+
```





## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Spring Security (Bearer Authentication)
- Maven

---

## Controllers and Routes

### Products
| Method | Route | Description |
|--------|-------|-------------|
| GET | /api/products | List all products |
| GET | /api/products/{id} | Get product by ID |
| POST | /api/products | Add new product |
| PUT | /api/products/{id} | Update product |
| DELETE | /api/products/{id} | Delete product |

### Customers
| Method | Route | Description |
|--------|-------|-------------|
| GET | /api/customers | List all customers |
| GET | /api/customers/{id} | Get customer by ID |
| POST | /api/customers | Add new customer |
| PUT | /api/customers/{id} | Update customer |
| DELETE | /api/customers/{id} | Delete customer |

### Orders
| Method | Route | Description |
|--------|-------|-------------|
| GET | /api/orders | List all orders |
| GET | /api/orders/{id} | Get order by ID |
| POST | /api/orders | Create new order |
| PUT | /api/orders/{id} | Update order |
| DELETE | /api/orders/{id} | Delete order |

### Cart
| Method | Route | Description |
|--------|-------|-------------|
| GET | /api/carts/{customerId} | View customer cart |
| POST | /api/carts/{customerId}/add/{productId} | Add product to cart |
| POST | /api/carts/{customerId}/remove/{productId} | Remove product from cart |
| POST | /api/carts/{customerId}/checkout | Checkout cart and place order |

---

## Future Work

- Add payment gateway integration
- Add product reviews and ratings
- Improve frontend integration (React or Angular)

---

## Team Members

- Skylar Freeland

---

## Deliverables

1. A fully working REST API
2. GitHub repository URL: https://github.com/skylarf16-lab/ResinJewelryStore.git
3. Slides URL: https://docs.google.com/presentation/d/1YhMstdvpgyq02zw5Yvc6-YqBUu9I7JRYcpmfmvgfhes/edit?usp=sharing
4. Task Management App URl: https://trello.com/invite/b/690df8b9c71860600b4458bd/ATTIa6893a750003ac8c67f0332d2b0ff8a13FF3F11D/my-trello-board
5. Security (added measures)
6. Complete README.md with class & use case diagrams
