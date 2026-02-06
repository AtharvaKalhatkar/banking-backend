KFpxD4AYi6pNUJMR





mongodb+srv://atharva8552\_db\_user:KFpxD4AYi6pNUJMR@smartcore-bank.4o6o4q0.mongodb.net/?appName=smartcore-bank



\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

69629409b66daef4e305493f





{

    "accountNumber": "b1025592-c",

    "balance": 0.0,

    "id": "696296ae085a5fa3394f4fc4",

    "status": "ACTIVE",

    "userId": "69629409b66daef4e305493f"

}

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_



New user registration



http://localhost:8081/api/users/register



{

  "name": "Rahul Sharma",

  "email": "rahul.sharma@test.com",

  "password": "rahul123",

  "userType": "STUDENT"

}







{

    "email": "atharva01@test.com",

    "id": "69629ae6085a5fa3394f4fc7",

    "name": "Atharva bharat kalhatkar",

    "password": "atharva123",

    "userType": "STUDENT"

}



{

    "accountNumber": "756268495836",

    "balance": 0.0,

    "id": "69629b2fb1b59a21488aed2e",

    "status": "ACTIVE",

    "userId": "69629ae6085a5fa3394f4fc7"

}

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_





POST http://localhost:8081/api/accounts/create/{newUserId}



{

    "email": "rahul.sharma@test.com",

    "id": "6962991b085a5fa3394f4fc5",

    "name": "Rahul Sharma",

    "password": "rahul123",

    "userType": "STUDENT"

}



\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_



API for Deposite

POST http://localhost:8081/api/transactions/deposit/756268495836/5000



{

    "accountNumber": "756268495836",

    "amount": 5000.0,

    "balanceAfter": 5000.0,

    "id": "696349db9165d12d33ba8c78",

    "timestamp": "2026-01-11T12:27:31.23642",

    "type": "CREDIT"

}

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_





API for Withdrawl

POST http://localhost:8081/api/transactions/withdraw/582937401928/2000



{

    "accountNumber": "756268495836",

    "amount": 2000.0,

    "balanceAfter": 3000.0,

    "id": "69634a179165d12d33ba8c79",

    "timestamp": "2026-01-11T12:28:31.1606024",

    "type": "DEBIT"

}



\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_



API to check account health

GET http://localhost:8081/api/health/756268495836

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_



API for AUTH

post http://localhost:8081/api/auth/login



{

  "email": "rahul.sharma@test.com",

  "password": "rahul123"

}

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_



API for test login

http://localhost:8081/api/auth/login



{

  "email": "amit.verma@test.com",

  "password": "amit123"

}



JWT token

eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbWl0LnZlcm1hQHRlc3QuY29tIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3NjgxMTgzNTIsImV4cCI6MTc2ODEyMTk1Mn0.AeAQQMzNXo21eBHxAkl1zeAgPzKDCsWQfX97YSfFTk0

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_





Api for user Registration



http://localhost:8081/api/users/register



{

  "name": "Admin User",

  "email": "admin@test.com",

  "password": "admin123",

  "userType": "BANK",

  "role": "ADMIN"

}





{

    "email": "admin@test.com",

    "id": "6963e9ba930e7976bf184ae5",

    "name": "Admin User",

    "password": "$2a$10$6lSmZI196I2CmrPx0nl3d.rn7iBZqRymRm5RB3upkOIaIPn/EhfCy",

    "role": "USER"

}

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_





Login as admin

http://localhost:8081/api/auth/login



{

  "email": "admin@test.com",

  "password": "admin123"

}





eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsInJvbGUiOiJVU0VSIiwidXNlcklkIjoiNjk2M2U5YmE5MzBlNzk3NmJmMTg0YWU1IiwiaWF0IjoxNzY4MTU3OTA1LCJleHAiOjE3NjgyNDQzMDV9.bGXyElbK5ZW95-9C2SnIUFig-NQBQKCOXagnmPrwRzc







eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsInJvbGUiOiJVU0VSIiwidXNlcklkIjoiNjk2M2U5YmE5MzBlNzk3NmJmMTg0YWU1IiwiaWF0IjoxNzY4MjM4MDgwLCJleHAiOjE3NjgzMjQ0ODB9.6z0KrcBgBsDSRmxTQnv6O-0MuMWkfO4sW4ev-xAW3I0





eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsInJvbGUiOiJVU0VSIiwidXNlcklkIjoiNjk2M2U5YmE5MzBlNzk3NmJmMTg0YWU1IiwiaWF0IjoxNzY4MjQxMTcxLCJleHAiOjE3NjgzMjc1NzF9.BaqDxLO8NLiK2jIJOAzmQmjtVnaX1LzjmwZEtt2a\_bg

