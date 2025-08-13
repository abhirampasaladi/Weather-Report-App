## 🌦️ Weather Data Aggregator App
This is a **Spring Boot-based Weather App** that aggregates weather information from multiple external APIs like **AccuWeather** and **Visual Crossing Weather**. It provides a RESTful API to fetch current weather, temperature data, and historical reports based on U.S. postal codes.

### 🛠 Tech Stack
- **Backend:** Java 21, Spring Boot 3.5.3, Spring Web, Spring Data JPA
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA
- **Async Processing:** Spring Scheduling with `TaskExecutor`
- **Logging:**  
  - Outbound: `RestTemplate` with `ClientHttpInterceptor`  
  - Inbound: Servlet `Filter` for request logging
- **Utilities:** Lombok for boilerplate reduction

### ✅ Development Best Practices
- Followed Java coding standards using **SonarQube**
- Used design patterns where appropriate:
  - **Singleton Pattern**
  - **Builder Pattern**
  - **Factory Pattern**

### 🔍 REST API Endpoints
Base URL: `/weather/us/cities`

| Endpoint | HTTP Method | Query Parameters | Description |
|----------|-------------|-----------------|-------------|
| `/current-conditions` | GET | `postalcode` (String, required, 5-digit or 5+4 ZIP format) | Retrieves current weather information for a given postal code. |
| `/current-conditions/temperatures` | GET | `postalcode` (String, required, 5-digit or 5+4 ZIP format) | Retrieves current temperature information for a given postal code. |
| `/history-reports` | GET | `postalcode` (String, required), `range` (int, required, past hours) | Retrieves weather data for the past `range` hours. |
| `/history-reports/temperature/in-past-hr` | GET | `postalcode` (String, required), `range` (int, required, past hours) | Retrieves temperature data for the past `range` hours. |
| `/history-reports/temperature/between` | GET | `postalcode` (String, required), `from` (LocalDateTime, required, `YYYY-MM-DDTHH:MM:SS±HH:MM`), `to` (LocalDateTime, required, `YYYY-MM-DDTHH:MM:SS±HH:MM`) | Retrieves temperature data between two specific date-time values. |

Example REST API calls:
- http://localhost:8080/weather/us/cities/current-conditions?postalcode=19087-5317
- http://localhost:8080/weather/us/cities/current-conditions?postalcode=19087
- http://localhost:8080/weather/us/cities/history-reports/temperature/in-past-hr?range=500&postalcode=45220
- http://localhost:8080/weather/us/cities/history-reports/temperature/between?from=2025-08-01T11:00:00&to=2025-08-04T20:00:00&postalcode=19425

### ⏳ Scheduled Weather Data Storage
- Implemented an asynchronous scheduled task to periodically fetch current weather data for a configurable postal code and store it in the database.
- Cron expression and postal code are externalized via application.properties (weather.scheduler.cron and weather.scheduler.postal-code), allowing schedule and location changes without modifying code.
- Uses Spring’s @Scheduled for task scheduling and @Async for non-blocking execution.
- application-dev.properties changes:
  - weather.scheduler.cron=0 0 */1 * * *
  - weather.scheduler.postal-code=19425

### ⚙️ Configuration Files
The application uses Spring Boot’s profile-based configuration to separate environment settings.
There are two main configuration files:
1. application.properties (Base Configuration)
- This is the default configuration file, containing common settings shared across all environments.
- Key Configurations:
  - spring.application.name=weatherinfo → Defines the application name.
  - Weather API Keys:
    - acc_api_key → AccuWeather API key
    - vc_api_key → Visual Crossing API key.
  - Scheduler Changes: 
    - weather.scheduler.postal-code=45220 → Target location postal code.
    - weather.scheduler.cron=0 0 */1 * * * → Runs every hour.
  - Weather API Endpoints:
    - AccuWeather: acc_apikey_url, acc_url_path, acc_url_query_parameters.
    - Visual Crossing: vc_url, vc_url_path, vc_url_query_parameters.
  - Database:
    - PostgreSQL connection settings: URL, username, password.
  - JPA:
    - spring.jpa.hibernate.ddl-auto=update → Auto schema update.
    - spring.jpa.show-sql=true → SQL logs enabled.
  - Logging:
    - logging.level.org.springframework.web=DEBUG → Debug logs for web layer.
  - Profile Handling
    - spring.config.activate.on-profile=prod → Base file defaults to PROD profile.
    - spring.profiles.active=dev → Forces dev profile as active for development.

2. application-dev.properties (Development Configuration)
Overrides settings from the base file for development environment.
- Differences from Base:
  - Profile
    - spring.config.activate.on-profile=dev → Marks this file for DEV environment.
    - profile.status=DEV-ENV → Custom indicator for DEV mode.
  - Other Settings
    - API keys, API endpoints, scheduler, database, and logging remain the same as in base config.

📌 How Profiles Work
- Spring Boot loads application.properties first. Then it overrides with environment-specific files (e.g., application-dev.properties) based on:
  - spring.profiles.active in the base config or --spring.profiles.active passed via CLI/ENV variable.
- In this project, I have used profiling for practice purpose and i have set it to "dev". So, make changes in application-dev.properties to reflect changes.

---

## ⚙️ Setup Instructions
To use this RESTful API:
1. **API Keys Required**  
   - Obtain developer keys from:
     - [AccuWeather](https://developer.accuweather.com/)
     - [Visual Crossing](https://www.visualcrossing.com/weather-data-editions)
   - Add them in:  
     `src/main/resources/application-dev.properties`
2. **Active Profile**  
   - The app is currently configured to use the `dev` profile (both are same, if you want to add configurations then add them to application-dev.properties to reflect changes).  
     Ensure this is set correctly in application.properties:
     ```
     spring.profiles.active=dev
     ```
3. **Database Configuration**
   - Creat a Database with name "weatherDB" and table with name "historydb".
## 🗄️ Database Schema — `historydb`
Stores historical weather data from **AccuWeather** and **Visual Crossing** APIs into a PostgreSQL table named `historydb`. Mapped to the `PastDataDB` JPA entity.

---
### ✅ Table Structure
```sql
CREATE TABLE historydb (
  datetime TIMESTAMP PRIMARY KEY,
  postalcode VARCHAR(255),
  weathercondition VARCHAR(1000),
  accudatetime VARCHAR(255),
  accucondition VARCHAR(255),
  accutemp VARCHAR(255),
  vcdatetime VARCHAR(255),
  vctemp VARCHAR(255),
  vcfeelslike VARCHAR(255),
  vccondition VARCHAR(255)
);

🔍 Column Details
Column Name	Type	Description
datetime	TIMESTAMP	Primary Key. Local date and time of data entry
postalcode	VARCHAR(255)	Zip code used to query the weather data
weathercondition	VARCHAR(1000)	Combined weather information from both APIs
accudatetime	VARCHAR(255)	Timestamp from AccuWeather API
accucondition	VARCHAR(255)	Weather condition from AccuWeather
accutemp	VARCHAR(255)	Temperature from AccuWeather
vcdatetime	VARCHAR(255)	Timestamp from Visual Crossing API
vctemp	VARCHAR(255)	Temperature from Visual Crossing
vcfeelslike	VARCHAR(255)	"Feels Like" temperature from Visual Crossing
vccondition	VARCHAR(255)	Weather condition from Visual Crossing

⚙️ Database Configuration
Update your PostgreSQL credentials and database URL in the properties file:
File: src/main/resources/application-dev.properties
  - Update your PostgreSQL credentials:
     - `username`, `password`, and `URL`
spring.datasource.url=jdbc:postgresql://localhost:5432/weatherDB
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

