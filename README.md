# TripWise — Smart and Transparent Travel Planning Platform (Backend)

TripWise is a transparent travel planning backend developed with Spring Boot, Spring Data JPA, PostgreSQL, and Bean Validation. It gives travelers itemized cost clarity across accommodations, transport, and experiences without opaque broker commissions or hidden markups.

---

## 1. Prerequisites

- **Java Development Kit (JDK)**: JDK 17 or higher (tested and verified with JDK 17 / JDK 26)
- **PostgreSQL Server**: PostgreSQL 14+ running on port `5432` for local development
- **Maven**: Bundled Maven Wrapper (`mvnw.cmd` / `./mvnw`) included in the project
- **Node.js**: v18+ (for running the React.js frontend)

---

## 2. PostgreSQL Database Setup

Create a local database and application user with `psql` or another PostgreSQL client:

```sql
CREATE USER tripwise WITH PASSWORD 'tripwise';
CREATE DATABASE tripwise OWNER tripwise;
```

## 3. Configuring the Database Connection

The backend configuration is in `Backend/src/main/resources/application.properties`. Local development defaults to PostgreSQL at `localhost:5432`, database `tripwise`. Configure a connection with `DB_URL` or the individual `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`, and `DB_SSLMODE` environment variables.

## 4. Deploying to Render

The backend repository's top-level `render.yaml` defines a Docker web service and a managed PostgreSQL database:

1. Push the project to a GitHub repository.
2. In Render, choose **New + → Blueprint** and select the repository containing `render.yaml`.
3. Review and apply the Blueprint. Render builds the backend from the repository's `Dockerfile`, provisions PostgreSQL, and connects the service using its internal host and credentials.
4. After deployment, set `FRONTEND_ORIGIN` in the `tripwise-backend` service environment to the exact public origin of your frontend (for example, `https://your-frontend.onrender.com`), then redeploy.
5. If deploying the frontend separately, set its `VITE_API_BASE_URL` to `https://<your-backend>.onrender.com/api` and rebuild it.

The Blueprint uses Render's free plans; free PostgreSQL databases expire after 30 days. Select a paid database plan in Render if the database must persist beyond that limit.

## 5. Running the Spring Boot Backend Locally

1. Start your local PostgreSQL server and create the database/user above.
2. Navigate to the `Backend` directory:
   ```powershell
   cd d:\TripWise\Backend
   ```
3. Set `JAVA_HOME` to a JDK 17+ installation if it is not already configured, then run:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

The backend starts on port **8080** at `http://localhost:8080`. Sample destinations and travel services are seeded automatically.

---
## 6. How to Run the React Frontend

1. Navigate to the `Frontend` directory in another terminal:
   ```cmd
   cd d:\TripWise\Frontend
   ```

2. Install dependencies (if not already installed):
   ```cmd
   npm install
   ```

3. Start the Vite development server:
   ```cmd
   npm run dev
   ```

4. Open `http://localhost:5173` in your browser.
The frontend connects to `http://localhost:8080/api` via the configured `src/services/api.js`.

---

## 7. Architecture & Package Structure

The backend follows a clean, beginner-friendly layered architecture:

- `com.tripwise.entity`: JPA Entities (`Destination`, `TravelService`, `Trip`, `TripItem`)
- `com.tripwise.repository`: Spring Data JPA Repositories
- `com.tripwise.dto`: Request and response transfer models
- `com.tripwise.service`: Core business rules, total calculations, and package comparison logic
- `com.tripwise.controller`: REST API endpoints
- `com.tripwise.config`: CORS configuration (`WebConfig`) and automatic idempotent seeder (`DataSeeder`)
- `com.tripwise.exception`: Custom exceptions and global HTTP error handling (`GlobalExceptionHandler`)

---

## 8. REST API Endpoints & Testing

You can import `Backend/TripWise_Postman_Collection.json` directly into Postman to test all endpoints.

### Destinations
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/destinations` | List all destinations |
| `GET` | `/api/destinations/{id}` | Get single destination |
| `GET` | `/api/destinations/search?name=Goa` | Search destinations by name |
| `POST` | `/api/destinations` | Create new destination |
| `PUT` | `/api/destinations/{id}` | Update existing destination |
| `DELETE` | `/api/destinations/{id}` | Delete destination |

### Travel Services
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/services` | List all services |
| `GET` | `/api/services/{id}` | Get single service |
| `GET` | `/api/services/destination/{destinationId}` | Get services for a destination |
| `GET` | `/api/services/category/{category}` | Filter services by category |
| `POST` | `/api/services` | Add new travel service |
| `PUT` | `/api/services/{id}` | Update service details |
| `DELETE` | `/api/services/{id}` | Delete service |

### Trips
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/trips` | Create new trip itinerary |
| `GET` | `/api/trips` | List all planned trips |
| `GET` | `/api/trips/{id}` | Get trip details and item list |
| `PUT` | `/api/trips/{id}` | Update trip parameters |
| `DELETE` | `/api/trips/{id}` | Delete trip and its items |

### Trip Items (Direct Bookings)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/trips/{tripId}/items` | Add travel service to trip (calculates subtotal and trip total) |
| `GET` | `/api/trips/{tripId}/items` | List all items attached to trip |
| `DELETE` | `/api/trips/{tripId}/items/{itemId}` | Remove item and automatically adjust trip total |

### Budget Summary
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/trips/{tripId}/budget` | Itemized category ledger, remaining budget, cost per traveler |

### Transparent Package Comparison
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/packages/compare` | Compare commercial agency package quote against self-built trip |

---

## 9. Sample Request and Response Examples

### A. Create a Trip
**Request:** `POST /api/trips`
```json
{
  "tripName": "College Friends Goa Vacation",
  "destinationId": 1,
  "startDate": "2026-11-10",
  "endDate": "2026-11-15",
  "numberOfTravelers": 3,
  "budget": 45000.00,
  "interests": "Beaches, Adventure, Seafood",
  "status": "PLANNED"
}
```

**Response (`201 Created`):**
```json
{
  "id": 1,
  "tripName": "College Friends Goa Vacation",
  "destinationId": 1,
  "destinationName": "Goa (North & South)",
  "startDate": "2026-11-10",
  "endDate": "2026-11-15",
  "numberOfTravelers": 3,
  "budget": 45000.00,
  "interests": "Beaches, Adventure, Seafood",
  "status": "PLANNED",
  "totalEstimatedCost": 0.00,
  "items": []
}
```

---

### B. Add a Service Item to a Trip
**Request:** `POST /api/trips/1/items`
```json
{
  "serviceId": 7,
  "quantity": 3
}
```

**Response (`201 Created`):**
```json
{
  "id": 1,
  "serviceId": 7,
  "serviceName": "Scuba Diving & Grand Island Boat Cruise",
  "category": "activities",
  "providerName": "Goa Aqua Adventures",
  "quantity": 3,
  "unitPrice": 2600.00,
  "subtotal": 7800.00
}
```

---

### C. Check Trip Budget Summary
**Request:** `GET /api/trips/1/budget`

**Response (`200 OK`):**
```json
{
  "tripId": 1,
  "tripName": "College Friends Goa Vacation",
  "destinationName": "Goa (North & South)",
  "numberOfTravelers": 3,
  "budget": 45000.00,
  "totalEstimatedCost": 20950.00,
  "remainingBudget": 24050.00,
  "costPerTraveler": 6983.33,
  "categoryBreakdown": {
    "transportation": 5550.00,
    "accommodation": 7600.00,
    "activities": 7800.00
  },
  "notes": "Note: Food & personal expenses are not booked as items and are estimated separately.",
  "withinBudget": true
}
```

---

### D. Package Comparison
**Request:** `POST /api/packages/compare`
```json
{
  "packageName": "Standard Commercial Goa Explorer 4N/5D",
  "destination": "Goa",
  "packagePrice": 32000.00,
  "tripId": 1,
  "includedServices": [
    "Direct Flight to Goa",
    "Santana Beach Resort Candolim",
    "Scuba Diving & Grand Island Boat Cruise",
    "All meals & private AC coach"
  ]
}
```

**Response (`200 OK`):**
```json
{
  "packageName": "Standard Commercial Goa Explorer 4N/5D",
  "destination": "Goa",
  "packagePrice": 32000.00,
  "selfBuiltPrice": 20950.00,
  "priceDifference": 11050.00,
  "percentageDifference": 52.74,
  "matchedInclusions": [
    "Santana Beach Resort Candolim",
    "Scuba Diving & Grand Island Boat Cruise"
  ],
  "unmatchedInclusions": [
    "Direct Flight to Goa",
    "All meals & private AC coach"
  ],
  "selfBuiltItemsConsidered": [
    "Goa Express AC 3-Tier Train (Qty: 3)",
    "Santana Beach Resort Candolim (Qty: 2)",
    "Scuba Diving & Grand Island Boat Cruise (Qty: 3)"
  ],
  "transparencyDisclaimer": "Transparency Note: The calculated price difference reflects direct provider rates versus bundled package pricing. Differences may stem from service tier disparities, agency support overhead, premium conveniences, taxes, or unmatched inclusions, and should not be interpreted as pure brokerage markup or guaranteed savings."
}
```
