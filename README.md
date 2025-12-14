# SweetShopApplication

Sweet Shop Management System

A full-stack Sweet Shop Management System built using Spring Boot, React (Vite),
and JWT-based authentication.

The application allows users to view available sweets and purchase them securely
after logging in, with inventory updated in real time.


Tech Stack

Backend
- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL

Frontend
- React (Vite)
- HTML / CSS


Implemented Features

Authentication
- User registration
- User login
- JWT token generation
- Frontend login state handling
- Purchase actions disabled when user is not logged in

Sweets and Inventory
- View all available sweets
- Purchase sweets by specifying quantity
- Quantity decreases after successful purchase
- Prevents invalid purchases
- Total amount calculation handled safely (never shows NaN)

Frontend UI
- Quantity input per sweet
- Quantity input disabled when user is not logged in
- Buy button enabled only for authenticated users
- Clean and responsive UI


Setup Instructions

Backend Setup (Spring Tool Suite – STS)

Prerequisites
- Java
- Spring Tool Suite (STS)
- MySQL

Steps

1. Clone the repository:
   https://github.com/PrajwalGowdaBK/SweetShopApplication.git

2. Open Spring Tool Suite (STS)

3. Import the backend project:
   - File -> Import
   - Maven -> Existing Maven Projects
   - Select the Backend folder
   - Click Finish

4. Configure database
   Open src/main/resources/application.properties and update:

   spring.datasource.url=jdbc:mysql://localhost:3306/sweetshop
   spring.datasource.username=YOUR_DB_USERNAME
   spring.datasource.password=YOUR_DB_PASSWORD

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

5. Run the application:
   - Right-click the project
   - Run As -> Spring Boot App

Backend runs at:
http://localhost:8080


Frontend Setup

1. Open terminal in the project root

2. Run:
   cd .\sweetshop-frontend\
   npm install
   npm run dev

Frontend runs at:
http://localhost:5173


API Documentation

Authentication APIs

POST /api/auth/register
- Register a new user

POST /api/auth/login
- Login and receive JWT token


Sweets APIs

GET /api/sweets
- Get all available sweets

POST /api/sweets/{id}/purchase
- Purchase a sweet

Purchase Request Body (JSON)

{
  "quantity": 2
}


Screenshots

Screenshots of the application are available in the screenshots folder.

- Screenshots/home.png      : Home page showing sweets list
- Screenshots/login.png     : Login page
- Screenshots/purchase.png  : Purchase flow with quantity input


My AI Usage

AI Tools Used
- ChatGPT

How I Used AI
- Debugging Spring Security, JWT, and CORS configuration issues
- Resolving backend and frontend integration problems
- Fixing React controlled input and state management issues
- Understanding request body vs query parameter handling
- Improving implementation clarity and correctness

Reflection
AI significantly improved productivity by providing step-by-step debugging
guidance and explanations. All final design decisions, implementations,
and validations were performed manually to ensure correctness and understanding.


Notes and Future Enhancements
- Role-based access control (Admin features) is planned
- Automated testing and TDD will be added in a future iteration
- API coverage and security rules will be expanded incrementally


Message to the Evaluator----
------------------------->
As per my knowledge i have done it with that i have completely used the AI for the security has its not been learned so in the comming days i will be doing that also with that of the testing.