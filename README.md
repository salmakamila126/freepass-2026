# BCC Canteen API Documentation

## Overview
BCC Canteen is a digital platform to manage campus canteens: ordering food, payments, menu management, and admin control.

**Base URL:**
http://localhost:8080/api


---

## Authentication
- **Type:** JWT (Bearer Token)
- **How to get token:** Use `/users/login` endpoint
- **Header Example:**


---

## Endpoints

### 1. Register User
**POST** `/users/register`

**Request Body**
```json
{
  "username": "salmakamila",
  "password": "123",
  "email": "salma@example.com"
}

**Responses**

{
  "id": 1,
  "username": "salmakamila",
  "email": "salma@example.com",
  "role": "USER"
}

### 1.2 Register User
**POST** `/auth/login`

**Request Body**
```json
{
  "username": "salmakamila",
  "password": "123"
}


**Responses**

{
  "token": "<JWT_TOKEN>"
}

### 1.3 Update Profile
//**PUT** `/auth/profile/{userId}`

**Request Body**
```json
{
  "username": "newname",
  "email": "newemail@example.com"
}


**Responses**

{
  "id": 1,
  "username": "newname",
  "email": "newemail@example.com"
}

### 1.4 Update Role to Owner
//PUT /auth/admin/owner/{ownerId}

**Request Body**
```json
{
  "role": "OWNER"
}


**Responses**
{
  "id": 1,
  "username": "owner",
  "role": "OWNER"
}

//User Controller

###2.1 Get Logged-in User Info
//GET /users/me

**Request Body**
```json
{
  "role": "OWNER"
}


**Responses**
{
  "id": 1,
  "username": "owner",
  "role": "OWNER"
}

//Canteen Controller
###3.1 Get All Canteens
//GET /canteens

**Request Body**
```json


**Responses**
[
  { "id": 1, "name": "Canteen A" },
  { "id": 2, "name": "Canteen B" }
]

###3.2 Create Canteen
POST /canteens

**Request Body**
```json
{
  "name": "Canteen B"
}


**Responses**
{
  "id": 2,
  "name": "Canteen B"
}

###3.3 Get Canteen by ID
//GET /canteens/{id}

**Request Body**
```json


**Responses**
{
  "id": 1,
  "name": "Canteen A"
}

###3.4 Update Canteen
//PUT /canteens/{id}

**Request Body**
```
{
  "name": "Canteen Updated"
}

**Responses**
{
  "id": 1,
  "name": "Canteen Updated"
}

//Menu Controller

###4.2 Create Menu
//POST /menus

**Request Body**
```
{
  "name": "Mie Goreng",
  "price": 15000,
  "canteenId": 1
}


**Responses**
{
  "id": 2,
  "name": "Mie Goreng",
  "price": 15000
}

###4.3 Get Menu by ID
//POST /menus

**Request Body**
```

**Responses**

{
  "id": 1,
  "name": "Nasi Goreng",
  "price": 20000
}

###4.4 Update Menu
//PUT /menus/{id}

**Request Body**
```
{
  "name": "Nasi Goreng Spesial",
  "price": 22000
}

**Responses**
{
  "id": 1,
  "name": "Nasi Goreng Spesial",
  "price": 22000
}

###4.5 Delete Menu
//DELETE /menus/{id}

**Request Body**
```

**Responses**
200 OK

//Order Controller
###5.1 Create Order
//POST /orders
**Request Body**
```
{
  "userId": 1,
  "items": [
    { "menuId": 1, "quantity": 2 }
  ]
}


**Responses**
{
  "orderId": 1,
  "status": "PENDING"
}


###5.2 Update Order Status
PUT /orders/status/{orderId}
**Request Body**
```
{
  "status": "PAID"
}


**Responses**
{
  "orderId": 1,
  "status": "PAID"
}

###5.3 Get Orders by User
GET /orders/user/{userId}
**Request Body**
```

**Responses**
[
  { "orderId": 1, "status": "PAID" }
]

###5.4 Get Orders by Canteen
GET /orders/canteen/{canteenId}
**Request Body**
```

**Responses**
[
  { "orderId": 1, "status": "PENDING" }
]

//Payment Controller
###6.1 Pay Order
POST /payments/pay/{orderId}
**Request Body**
```

**Responses**
{
  "paymentId": 1,
  "status": "PAID"
}


/Feedback Controller
###7.1 Create Feedback
POST /feedbacks/{orderId}
**Request Body**
```
{
  "rating": 5,
  "comment": "Mantap"
}

**Responses**
{
  "id": 1,
  "rating": 5,
  "comment": "Mantap"
}

###7.2 Delete Feedback
DELETE /feedbacks/{id}
**Request Body**
```


**Responses**
200 OK





//swagger: http://localhost:8080/swagger-ui/index.html#/order-controller
///v3/api-docs
{
  "openapi": "3.0.1",
  "info": {
    "title": "OpenAPI definition",
    "version": "v0"
  },
  "servers": [
    {
      "url": "http://localhost:8080",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/orders/status/{orderId}": {
      "put": {
        "tags": [
          "order-controller"
        ],
        "operationId": "updateOrderStatus",
        "parameters": [
          {
            "name": "orderId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          },
          {
            "name": "status",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string",
              "enum": [
                "PENDING",
                "COOKING",
                "READY",
                "COMPLETED",
                "CANCELLED"
              ]
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Order"
                }
              }
            }
          }
        }
      }
    },
    "/menus/{id}": {
      "get": {
        "tags": [
          "menu-controller"
        ],
        "operationId": "getMenuById",
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Menu"
                }
              }
            }
          }
        }
      },
      "put": {
        "tags": [
          "menu-controller"
        ],
        "operationId": "updateMenu",
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/Menu"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Menu"
                }
              }
            }
          }
        }
      },
      "delete": {
        "tags": [
          "menu-controller"
        ],
        "operationId": "deleteMenu",
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK"
          }
        }
      }
    },
    "/canteens/{id}": {
      "get": {
        "tags": [
          "canteen-controller"
        ],
        "operationId": "getCanteenById",
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Canteen"
                }
              }
            }
          }
        }
      },
      "put": {
        "tags": [
          "canteen-controller"
        ],
        "operationId": "updateCanteen",
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/Canteen"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Canteen"
                }
              }
            }
          }
        }
      },
      "delete": {
        "tags": [
          "canteen-controller"
        ],
        "operationId": "deleteCanteen",
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK"
          }
        }
      }
    },
    "/auth/profile/{userId}": {
      "put": {
        "tags": [
          "auth-controller"
        ],
        "operationId": "updateProfile",
        "parameters": [
          {
            "name": "userId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/User"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserResponse"
                }
              }
            }
          }
        }
      }
    },
    "/auth/admin/owner/{ownerId}": {
      "put": {
        "tags": [
          "auth-controller"
        ],
        "operationId": "editOwner",
        "parameters": [
          {
            "name": "ownerId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/User"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserResponse"
                }
              }
            }
          }
        }
      }
    },
    "/payments/pay/{orderId}": {
      "post": {
        "tags": [
          "payment-controller"
        ],
        "operationId": "payOrder",
        "parameters": [
          {
            "name": "orderId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Payment"
                }
              }
            }
          }
        }
      }
    },
    "/orders": {
      "post": {
        "tags": [
          "order-controller"
        ],
        "operationId": "createOrder",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/CreateOrderRequest"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Order"
                }
              }
            }
          }
        }
      }
    },
    "/menus": {
      "get": {
        "tags": [
          "menu-controller"
        ],
        "operationId": "getAllMenus",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/Menu"
                  }
                }
              }
            }
          }
        }
      },
      "post": {
        "tags": [
          "menu-controller"
        ],
        "operationId": "createMenu",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/Menu"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Menu"
                }
              }
            }
          }
        }
      }
    },
    "/feedbacks/{orderId}": {
      "post": {
        "tags": [
          "feedback-controller"
        ],
        "operationId": "giveFeedback",
        "parameters": [
          {
            "name": "orderId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          },
          {
            "name": "userId",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          },
          {
            "name": "rating",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int32"
            }
          },
          {
            "name": "comment",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Feedback"
                }
              }
            }
          }
        }
      }
    },
    "/canteens": {
      "get": {
        "tags": [
          "canteen-controller"
        ],
        "operationId": "getAllCanteens",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/Canteen"
                  }
                }
              }
            }
          }
        }
      },
      "post": {
        "tags": [
          "canteen-controller"
        ],
        "operationId": "createCanteen",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/Canteen"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Canteen"
                }
              }
            }
          }
        }
      }
    },
    "/auth/register": {
      "post": {
        "tags": [
          "auth-controller"
        ],
        "operationId": "register",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/User"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserResponse"
                }
              }
            }
          }
        }
      }
    },
    "/auth/login": {
      "post": {
        "tags": [
          "auth-controller"
        ],
        "operationId": "login",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/LoginRequest"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/AuthResponse"
                }
              }
            }
          }
        }
      }
    },
    "/auth/admin/owner": {
      "get": {
        "tags": [
          "auth-controller"
        ],
        "operationId": "adminOwnerEndpoint",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "string"
                }
              }
            }
          }
        }
      },
      "post": {
        "tags": [
          "auth-controller"
        ],
        "operationId": "addOwner",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/User"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserResponse"
                }
              }
            }
          }
        }
      }
    },
    "/users/me": {
      "get": {
        "tags": [
          "user-controller"
        ],
        "operationId": "me",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "string"
                }
              }
            }
          }
        }
      }
    },
    "/orders/user/{userId}": {
      "get": {
        "tags": [
          "order-controller"
        ],
        "operationId": "getOrdersByUser",
        "parameters": [
          {
            "name": "userId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/Order"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/orders/canteen/{canteenId}": {
      "get": {
        "tags": [
          "order-controller"
        ],
        "operationId": "getOrdersByCanteen",
        "parameters": [
          {
            "name": "canteenId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/Order"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/auth/owner": {
      "get": {
        "tags": [
          "auth-controller"
        ],
        "operationId": "ownerOnly",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "string"
                }
              }
            }
          }
        }
      }
    },
    "/auth/admin": {
      "get": {
        "tags": [
          "auth-controller"
        ],
        "operationId": "adminOnly",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "string"
                }
              }
            }
          }
        }
      }
    },
    "/feedbacks/{id}": {
      "delete": {
        "tags": [
          "feedback-controller"
        ],
        "operationId": "deleteFeedback",
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK"
          }
        }
      }
    }
  },
  "components": {
    "schemas": {
      "Canteen": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "name": {
            "type": "string"
          },
          "owner": {
            "$ref": "#/components/schemas/User"
          },
          "menus": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/Menu"
            }
          }
        }
      },
      "Feedback": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "rating": {
            "type": "integer",
            "format": "int32"
          },
          "comment": {
            "type": "string"
          },
          "createdAt": {
            "type": "string",
            "format": "date-time"
          },
          "order": {
            "$ref": "#/components/schemas/Order"
          }
        }
      },
      "GrantedAuthority": {
        "type": "object",
        "properties": {
          "authority": {
            "type": "string"
          }
        }
      },
      "Menu": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "name": {
            "type": "string"
          },
          "price": {
            "type": "number",
            "format": "double"
          },
          "stock": {
            "type": "integer",
            "format": "int32"
          },
          "canteen": {
            "$ref": "#/components/schemas/Canteen"
          }
        }
      },
      "Order": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "user": {
            "$ref": "#/components/schemas/User"
          },
          "totalPrice": {
            "type": "number",
            "format": "double"
          },
          "status": {
            "type": "string",
            "enum": [
              "PENDING",
              "COOKING",
              "READY",
              "COMPLETED",
              "CANCELLED"
            ]
          },
          "items": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/OrderItem"
            }
          },
          "canteen": {
            "$ref": "#/components/schemas/Canteen"
          },
          "payment": {
            "$ref": "#/components/schemas/Payment"
          },
          "feedback": {
            "$ref": "#/components/schemas/Feedback"
          }
        }
      },
      "OrderItem": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "order": {
            "$ref": "#/components/schemas/Order"
          },
          "menu": {
            "$ref": "#/components/schemas/Menu"
          },
          "quantity": {
            "type": "integer",
            "format": "int32"
          },
          "price": {
            "type": "number",
            "format": "double"
          }
        }
      },
      "Payment": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "amount": {
            "type": "number",
            "format": "double"
          },
          "paymentTime": {
            "type": "string",
            "format": "date-time"
          },
          "status": {
            "type": "string",
            "enum": [
              "UNPAID",
              "PAID"
            ]
          },
          "order": {
            "$ref": "#/components/schemas/Order"
          }
        }
      },
      "User": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "name": {
            "type": "string"
          },
          "email": {
            "type": "string"
          },
          "username": {
            "type": "string"
          },
          "role": {
            "type": "string",
            "enum": [
              "USER",
              "OWNER",
              "ADMIN"
            ]
          },
          "enabled": {
            "type": "boolean"
          },
          "accountNonLocked": {
            "type": "boolean"
          },
          "authorities": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/GrantedAuthority"
            }
          },
          "credentialsNonExpired": {
            "type": "boolean"
          },
          "accountNonExpired": {
            "type": "boolean"
          }
        }
      },
      "UserResponse": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "name": {
            "type": "string"
          },
          "email": {
            "type": "string"
          },
          "role": {
            "type": "string"
          }
        }
      },
      "CreateOrderRequest": {
        "type": "object",
        "properties": {
          "userId": {
            "type": "integer",
            "format": "int64"
          },
          "canteenId": {
            "type": "integer",
            "format": "int64"
          },
          "items": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/OrderItemRequest"
            }
          }
        }
      },
      "OrderItemRequest": {
        "type": "object",
        "properties": {
          "menuId": {
            "type": "integer",
            "format": "int64"
          },
          "quantity": {
            "type": "integer",
            "format": "int32"
          }
        }
      },
      "LoginRequest": {
        "type": "object",
        "properties": {
          "username": {
            "type": "string"
          },
          "password": {
            "type": "string"
          }
        }
      },
      "AuthResponse": {
        "type": "object",
        "properties": {
          "token": {
            "type": "string"
          }
        }
      }
    }
  }
}











