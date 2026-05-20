# 🎮 GameLog API

A simple RESTful API built with **Java 21** and **Spring Boot** for logging video games you've finished playing. Track your gaming history, rate your experiences, and filter your log by year or game name.

---

## 🚀 Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 3.2.5 |
| Spring Data JPA | - |
| Spring Validation | - |
| H2 (in-memory) | - |
| Maven | 3.x |

---

## 📦 Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+

### Running the project

```bash
git clone https://github.com/your-user/gamelog.git
cd gamelog
./mvnw spring-boot:run
```

The application will start at `http://localhost:8080`.

You can also access the H2 console at `http://localhost:8080/h2-console` with the following credentials:

- **JDBC URL:** `jdbc:h2:mem:gamelogdb`
- **Username:** `sa`
- **Password:** `password`

---

## 📡 API Endpoints

### 1. Log a finished game

**POST** `/games`

Registers a game you've just finished. The year it was played is automatically set based on the current date.

**Request Body:**

```json
{
  "gameName": "Hollow Knight",
  "rating": 5
}
```

| Field | Type | Required | Rules |
|---|---|---|---|
| `gameName` | String | ✅ | Cannot be null |
| `rating` | Integer | ✅ | Between 0 and 5 |

**Response (200 OK):**

```json
{
  "id": 1,
  "gameName": "Hollow Knight",
  "rating": 5,
  "yearPlayed": "2025-05-20T14:30:00"
}
```

---

### 2. List logged games

**GET** `/game/list`

Returns all logged games. Supports optional filtering by year and/or name.

**Query Parameters:**

| Parameter | Type | Required | Description |
|---|---|---|---|
| `ano` | Integer | ❌ | Filter by the year the game was played |
| `name` | String | ❌ | Filter by game name (partial match supported) |

**Examples:**

```
GET /game/list
GET /game/list?ano=2025
GET /game/list?name=hollow
GET /game/list?ano=2025&name=hollow
```

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "gameName": "Hollow Knight",
    "rating": 5,
    "yearPlayed": "2025-05-20T14:30:00"
  }
]
```

---

## 🗂️ Project Structure

```
gamelog/
├── src/
│   ├── main/
│   │   ├── java/com/study/gamelog/
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── service/          # Business logic
│   │   │   ├── repository/       # Data access layer
│   │   │   ├── model/            # JPA entities
│   │   │   └── dto/              # Request and response DTOs
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/study/gamelog/
├── pom.xml
└── README.md
```

---

## 📝 Example cURL Requests

**Save a game:**
```bash
curl -X POST http://localhost:8080/games \
  -H "Content-Type: application/json" \
  -d '{"gameName": "Celeste", "rating": 5}'
```

**List all games:**
```bash
curl http://localhost:8080/game/list
```

**Filter by year:**
```bash
curl "http://localhost:8080/game/list?ano=2025"
```

**Filter by name:**
```bash
curl "http://localhost:8080/game/list?name=celeste"
```

---

## 📄 License

This project is intended for study and educational purposes.
