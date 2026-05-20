# RUS
# CRM-Система для управления продавцами и аналитики продаж (Seller CRM)

Веб-приложение на базе **Spring Boot**, предназначенное для автоматизации учета продавцов, ведения их контактных данных, а также построения глубокой аналитики эффективности продаж за определенные периоды времени.

---

## 1. Функциональность проекта

Проект предоставляет полноценный REST API для управления сущностями продавцов (`Seller`) и расчета ключевых аналитических показателей:

* **Управление продавцами (CRUD):**
    * Регистрация новых продавцов в системе с валидацией входных данных.
    * Получение полного списка зарегистрированных продавцов и просмотр инфо по ID.
    * **Обновление информации о продавце (PUT).**
    * **Мягкое удаление продавца (Soft Delete)** без потери истории операций.
* **Управление транзакциями:**
    * Регистрация совершенных сделок (сумма, тип оплаты, дата, привязка к продавцу).
    * Получение списка всех транзакций с гибкой фильтрацией по продавцу и типу оплаты.
    * Получение детальной информации о конкретной транзакции по ID.
* **Аналитический модуль (Бизнес-метрики):**
    * **Поиск отстающих продавцов (`under-performing`):** Расчет сотрудников с выручкой меньше лимита за период.
    * **Рейтинг эффективности (`seller-rating-by-sales`):** Ранжирование продавцов по объему продаж за период.
    * **Период максимальной продуктивности :** Алгоритм поиска временного отрезка, в котором продавец совершил пиковое количество транзакций.
* **Архитектурные особенности:**
    * **Делегирование логики контроллеров (SRP):** Логика эндпоинтов разнесена по узкоспециализированным компонентам: для продавцов (`SellerCreator`, `SellerGetter` и др.) и для транзакций (`TransactionCreator`, `TransactionGetter`, `PrimePeriodByIdGetter`).
    * **Историчность и аудит данных:** Внедрены автоматические поля `created_at` и `updated_at` (через Hibernate `@CreationTimestamp` / `@UpdateTimestamp`), а транзакции защищены от прямых изменений (неизменяемы).
    * **Глобальная обработка исключений:** Централизованный `GlobalExceptionHandler` для красивых 404/400 ошибок.
---

## 2. Необходимые требования

Для сборки и успешного запуска приложения требуются следующие компоненты среды разработки:

* **Среда выполнения (Runtime):** Java Development Kit (JDK) версии **21**.
* **Система автоматизации сборки:** Gradle (используется встроенный Gradle Wrapper).
* **База данных:** PostgreSQL (рекомендуемая версия 15 и выше).

## 3. Инструкции по сборке и запуску

### Шаг 1: Настройка базы данных
1. Убедитесь, что служба PostgreSQL запущена.
2. Создайте пустую базу данных для проекта (например, через `psql` или pgAdmin):
   ```sql
   CREATE DATABASE crm_db;
   ```
3. Откройте файл конфигурации приложения `src/main/resources/application.properties`  и укажите актуальные данные для подключения к вашей базе данных:
   
### Шаг 2: Сборка проекта
Откройте терминал в корневой папке проекта и выполните команду для очистки кэша и компиляции приложения:

* **Для Windows (Command Prompt / PowerShell):**


    gradlew.bat clean build -x test

* **Для Linux / macOS:**


    ./gradlew clean build -x test


### Шаг 3: Запуск приложения
После успешной сборки запустить приложение можно двумя способами:

1. **Напрямую через Gradle Wrapper:**


    ./gradlew bootRun

2. **Запуск собранного исполняемого JAR-файла:**


    java -jar build/libs/crm-0.0.1-SNAPSHOT.jar


После запуска приложение будет доступно по адресу: `http://localhost:8080`

### Шаг 4: Запуск тестов с проверкой покрытия
Чтобы запустить весь пакет модульных и интеграционных тестов (включая MockMvc-тесты контроллеров), выполните:


./gradlew test

---

## 4. Примеры использования API
### Блок API: Продавцы (`/api/sellers`)
### 1. Создание нового продавца
* **HTTP Метод:** `POST`
* **Эндпоинт:** `/api/sellers`
* **Заголовки:** `Content-Type: application/json`
* **Тело запроса (JSON):**
    ```json
    {
      "name": "Иван Иванов",
      "contactInfo": "ivan.ivanov@example.com, +7 (999) 123-45-67"
    }
    ```
* **Пример успешного ответа (200 OK):**
    ```json
    {
      "id": 1,
      "name": "Иван Иванов",
      "contactInfo": "ivan.ivanov@example.com, +7 (999) 123-45-67"
    }
    ```

### 2. Получение списка всех продавцов
* **HTTP Метод:** `GET`
* **Эндпоинт:** `/api/sellers`
* **Пример успешного ответа (200 OK):**
    ```json
    [
      {
        "id": 1,
        "name": "Иван Иванов",
        "contactInfo": "ivan.ivanov@example.com, +7 (999) 123-45-67"
      },
      {
        "id": 2,
        "name": "Анна Петрова",
        "contactInfo": "anna.p@example.com"
      }
    ]
    ```

### 3. Получение продавца по уникальному ID
* **HTTP Метод:** `GET`
* **Эндпоинт:** `/api/sellers/{sellerId}`
* **Пример запроса:** `/api/sellers/1`
* **Пример успешного ответа (200 OK):**
    ```json
    {
      "id": 1,
      "name": "Иван Иванов",
      "contactInfo": "ivan.ivanov@example.com, +7 (999) 123-45-67"
    }
    ```
### 4. Обновление информации о продавце
* **HTTP Метод:** `PUT`
* **Эндпоинт:** `/api/sellers/{sellerId}`
* **Тело запроса (JSON):**
    ```json
    {
      "name": "Иван Иванов (Обновлено)",
      "contactInfo": "new.email@example.com"
    }
    ```
* **Пример успешного ответа (200 OK):**
    ```json
    {
      "id": 1,
      "name": "Иван Иванов (Обновлено)",
      "contactInfo": "new.email@example.com"
    }
    ```

### 5. Поиск отстающих продавцов (Сумма продаж ниже лимита)
* **HTTP Метод:** `GET`
* **Эндпоинт:** `/api/sellers/under-performing`
* **Пример запроса:** `GET /api/sellers/under-performing?dateFrom=2026-01-01T00:00:00&dateTo=2026-12-31T23:59:59&maxAmount=5000.0`
* **Пример успешного ответа (200 OK):**
    ```json
    [
      {
        "id": 2,
        "name": "Анна Петрова",
        "contactInfo": "anna.p@example.com"
      }
    ]
    ```

### 6. Получение рейтинга продавцов по объему продаж
* **HTTP Метод:** `GET`
* **Эндпоинт:** `/api/sellers/seller-rating-by-sales`
* **Пример запроса:** `GET /api/sellers/seller-rating-by-sales?dateFrom=2026-01-01T00:00:00&dateTo=2026-12-31T23:59:59`
* **Пример успешного ответа (200 OK):**
    ```json
    [
      {
        "sellerId": 1,
        "name": "Иван Иванов",
        "totalSalesAmount": 154500.0,
        "salesCount": 42
      }
    ]
    ```
### 7. Удаление продавца
* **HTTP Метод:** `DELETE`
* **Эндпоинт:** `/api/sellers/{sellerId}`
* **Пример запроса:** `DELETE /api/sellers/1`
* **Пример успешного ответа:** `200 OK` (Без тела ответа)
---
---

### Блок API: Транзакции (`/api/transactions`)

### 8. Создание новой транзакции
* **HTTP Метод:** `POST`
* **Эндпоинт:** `/api/transactions`
* **Тело запроса (JSON):**
    ```json
    {
      "sellerId": 1,
      "amount": 4500.0,
      "paymentType": "CASH",
      "transactionDate": "2026-05-20T12:00:00"
    }
    ```
* **Пример успешного ответа (200 OK):**
    ```json
    {
      "id": 101,
      "amount": 4500.0,
      "paymentType": "CASH",
      "transactionDate": "2026-05-20T12:00:00",
      "createdAt": "2026-05-20T12:05:21"
    }
    ```

### 9. Получение списка всех транзакций (с фильтрами)
* **HTTP Метод:** `GET`
* **Эндпоинт:** `/api/transactions`
* **Параметры фильтрации (опционально):** `sellerId` (ID продавца), `paymentType` (CASH/CARD).
* **Пример запроса с фильтром по продавцу:** `GET /api/transactions?sellerId=1`
* **Пример успешного ответа (200 OK):**
    ```json
    [
      {
        "id": 101,
        "amount": 4500.0,
        "paymentType": "CASH",
        "transactionDate": "2026-05-20T12:00:00",
        "createdAt": "2026-05-20T12:05:21"
      }
    ]
    ```

### 10. Получение информации о транзакции по ID
* **HTTP Метод:** `GET`
* **Эндпоинт:** `/api/transactions/{transactionId}`
* **Пример запроса:** `GET /api/transactions/101`

### 11. Получение самого продуктивного периода продавца (Задача со звёздной)
* **HTTP Метод:** `GET`
* **Эндпоинт:** `/api/transactions/prime-period/{sellerId}`
* **Пример запроса:** `GET /api/transactions/prime-period/1`
* **Пример успешного ответа (200 OK):**
    ```json
    {
      "sellerId": 1,
      "dateFrom": "2026-03-01T00:00:00",
      "dateTo": "2026-03-15T23:59:59",
      "transactionsCount": 89
    }
    ```

## 5. Обработка исключений (Ошибки API)

Если в базе данных нет продавца/трнзакции с запрашиваемым ID продавца, приложение возвращает понятную ошибку вместо падения:

### Пример: Запрос несуществующего продавца (`GET /api/sellers/999`)
* **HTTP Статус:** `404 Not Found`
* **Тело ответа (JSON):**
    ```json
    {
      "timestamp": "2026-05-19T19:52:00",
      "status": 404,
      "error": "Not Found",
      "message": "Продавец с ID 999 не найден",
      "path": "/api/sellers/999"
    }
    ```
# ENG
# Seller CRM: Management & Sales Analytics System

A **Spring Boot**-based web application designed to automate seller management, maintain contact records, and generate deep sales performance analytics over specified time periods.

---

## 1. Features

The project provides a fully functional REST API for managing `Seller` entities and calculating core business metrics:

* **Seller Management (CRUD):**
    * Registration of new sellers with incoming data validation.
    * Retrieving a full list of registered sellers and viewing detailed info by ID.
    * **Updating seller profiles (PUT).**
    * **Soft Delete of sellers** to safely deactivate profiles without breaking transaction history.
* **Transaction Management:**
    * Registration of completed transactions (amount, payment type, date, and seller assignment).
    * Retrieving all transactions with flexible filtering by seller ID and payment type.
    * Retrieving detailed information for a specific transaction by ID.
* **Analytics Module (Business Metrics):**
    * **Under-performing Sellers (`under-performing`):** Identifies employees whose total revenue within a given period is strictly below a specified threshold.
    * **Sales Performance Rating (`seller-rating-by-sales`):** Ranks sellers based on total sales volume over a specified period to highlight top performers.
    * **Peak Performance Period (Star Task):** An advanced algorithm designed to find the specific time window during which a seller achieved their highest volume of transactions.
* **Architectural Highlights:**
    * **Controller Logic Delegation (SRP):** Endpoint logic is split into highly specialized single-responsibility components: for sellers (`SellerCreator`, `SellerGetter`, etc.) and for transactions (`TransactionCreator`, `TransactionGetter`, `PrimePeriodByIdGetter`).
    * **Data Immutability & Audit Logging:** Automated audit fields (`created_at` / `updated_at`) managed via Hibernate `@CreationTimestamp` and `@UpdateTimestamp`. Transactions are designed to be immutable to preserve historical audit trails.
    * **Global Exception Handling:** A centralized `GlobalExceptionHandler` intercepting errors and mapping them to standardized 400/404 JSON responses.

---

## 2. Requirements

To build and successfully run this application, ensure your environment meets the following specifications:

* **Runtime:** Java Development Kit (JDK) version **21**.
* **Build System:** Gradle (the project includes an embedded Gradle Wrapper).
* **Database:** PostgreSQL (recommended version 15 or higher).

## 3. Build & Run Instructions

### Step 1: Database Setup
1. Ensure your PostgreSQL service is active and running.
2. Create an empty database for the project (e.g., via `psql` or pgAdmin):
   ```sql
   CREATE DATABASE crm_db;
   ```
3. Open the main configuration file `src/main/resources/application.properties` and update the datasource credentials to match your database settings.

### Step 2: Build the Project
Navigate to the project's root folder in your terminal and execute the command to clean the cache and compile the application:

* **For Windows (Command Prompt / PowerShell):**
  ```cmd
  gradlew.bat clean build -x test
  ```
* **For Linux / macOS:**
  ```bash
  ./gradlew clean build -x test
  ```

### Step 3: Run the Application
Once the build is complete, you can start the application using one of the following methods:

1. **Directly via the Gradle Wrapper:**
   ```bash
   ./gradlew bootRun
   ```
2. **Running the compiled executable JAR file:**
   ```bash
   java -jar build/libs/crm-0.0.1-SNAPSHOT.jar
   ```

The server will initialize and become accessible at: `http://localhost:8080`

### Step 4: Run Tests with Test Coverage Check
To execute the entire unit and integration test suite (including MockMvc controller tests), run:
```bash
./gradlew test
```

---

## 4. API Usage Examples

### Sellers API Block (`/api/sellers`)

### 1. Register a New Seller
* **HTTP Method:** `POST`
* **Endpoint:** `/api/sellers`
* **Headers:** `Content-Type: application/json`
* **Request Body (JSON):**
    ```json
    {
      "name": "John Doe",
      "contactInfo": "john.doe@example.com, +1 (555) 123-4567"
    }
    ```
* **Sample Success Response (200 OK):**
    ```json
    {
      "id": 1,
      "name": "John Doe",
      "contactInfo": "john.doe@example.com, +1 (555) 123-4567"
    }
    ```

### 2. Get All Sellers
* **HTTP Method:** `GET`
* **Endpoint:** `/api/sellers`
* **Sample Success Response (200 OK):**
    ```json
    [
      {
        "id": 1,
        "name": "John Doe",
        "contactInfo": "john.doe@example.com, +1 (555) 123-4567"
      },
      {
        "id": 2,
        "name": "Jane Smith",
        "contactInfo": "jane.smith@example.com"
      }
    ]
    ```

### 3. Get Seller by Unique ID
* **HTTP Method:** `GET`
* **Endpoint:** `/api/sellers/{sellerId}`
* **Sample Request:** `GET /api/sellers/1`
* **Sample Success Response (200 OK):**
    ```json
    {
      "id": 1,
      "name": "John Doe",
      "contactInfo": "john.doe@example.com, +1 (555) 123-4567"
    }
    ```

### 4. Update Seller Information
* **HTTP Method:** `PUT`
* **Endpoint:** `/api/sellers/{sellerId}`
* **Request Body (JSON):**
    ```json
    {
      "name": "John Doe (Updated)",
      "contactInfo": "new.email@example.com"
    }
    ```
* **Sample Success Response (200 OK):**
    ```json
    {
      "id": 1,
      "name": "John Doe (Updated)",
      "contactInfo": "new.email@example.com"
    }
    ```

### 5. Find Under-performing Sellers (Sales Below Threshold)
* **HTTP Method:** `GET`
* **Endpoint:** `/api/sellers/under-performing`
* **Sample Request:** `GET /api/sellers/under-performing?dateFrom=2026-01-01T00:00:00&dateTo=2026-12-31T23:59:59&maxAmount=5000.0`
* **Sample Success Response (200 OK):**
    ```json
    [
      {
        "id": 2,
        "name": "Jane Smith",
        "contactInfo": "jane.smith@example.com"
      }
    ]
    ```

### 6. Get Seller Rating by Sales Volume
* **HTTP Method:** `GET`
* **Endpoint:** `/api/sellers/seller-rating-by-sales`
* **Sample Request:** `GET /api/sellers/seller-rating-by-sales?dateFrom=2026-01-01T00:00:00&dateTo=2026-12-31T23:59:59`
* **Sample Success Response (200 OK):**
    ```json
    [
      {
        "sellerId": 1,
        "name": "John Doe",
        "totalSalesAmount": 154500.0,
        "salesCount": 42
      }
    ]
    ```

### 7. Delete a Seller (Soft Delete)
* **HTTP Method:** `DELETE`
* **Endpoint:** `/api/sellers/{sellerId}`
* **Sample Request:** `DELETE /api/sellers/1`
* **Sample Success Response:** `200 OK` (Empty response body)

---

### Transactions API Block (`/api/transactions`)

### 8. Create a New Transaction
* **HTTP Method:** `POST`
* **Endpoint:** `/api/transactions`
* **Request Body (JSON):**
    ```json
    {
      "sellerId": 1,
      "amount": 4500.0,
      "paymentType": "CASH",
      "transactionDate": "2026-05-20T12:00:00"
    }
    ```
* **Sample Success Response (200 OK):**
    ```json
    {
      "id": 101,
      "amount": 4500.0,
      "paymentType": "CASH",
      "transactionDate": "2026-05-20T12:00:00",
      "createdAt": "2026-05-20T12:05:21"
    }
    ```

### 9. Get All Transactions (With Filters)
* **HTTP Method:** `GET`
* **Endpoint:** `/api/transactions`
* **Query Parameters (Optional):** `sellerId` (Filter by Seller ID), `paymentType` (Filter by CASH/CARD/TRANSFER).
* **Sample Request with Filter:** `GET /api/transactions?sellerId=1`
* **Sample Success Response (200 OK):**
    ```json
    [
      {
        "id": 101,
        "amount": 4500.0,
        "paymentType": "CASH",
        "transactionDate": "2026-05-20T12:00:00",
        "createdAt": "2026-05-20T12:05:21"
      }
    ]
    ```

### 10. Get Transaction by ID
* **HTTP Method:** `GET`
* **Endpoint:** `/api/transactions/{transactionId}`
* **Sample Request:** `GET /api/transactions/101`

### 11. Get Seller's Peak Performance Period (Star Task)
* **HTTP Method:** `GET`
* **Endpoint:** `/api/transactions/prime-period/{sellerId}`
* **Sample Request:** `GET /api/transactions/prime-period/1`
* **Sample Success Response (200 OK):**
    ```json
    {
      "sellerId": 1,
      "dateFrom": "2026-03-01T00:00:00",
      "dateTo": "2026-03-15T23:59:59",
      "transactionsCount": 89
    }
    ```

---

## 5. Exception Handling (API Errors)

If a requested resource (Seller or Transaction) is not found within the system, the application interceptor halts normal execution and returns a standardized error payload instead of a raw stack trace:

### Example: Requesting a Non-Existent Seller (`GET /api/sellers/999`)
* **HTTP Status:** `404 Not Found`
* **Response Body (JSON):**
    ```json
    {
      "timestamp": "2026-05-19T19:52:00",
      "status": 404,
      "error": "Not Found",
      "message": "Seller with ID 999 not found",
      "path": "/api/sellers/999"
    }
    ```
