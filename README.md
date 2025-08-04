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
🗄️ Database Schema (historydb)
The application uses a PostgreSQL database to store historical weather information fetched from AccuWeather and Visual Crossing APIs. The corresponding table for the entity PastDataDB is named historydb.

✅ Table Structure
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

  - Update your PostgreSQL credentials:
     - `username`, `password`, and `URL`  
  - File to modify: `src/main/resources/application-dev.properties`
  - spring.datasource.url=jdbc:postgresql://localhost:5432/weatherDB
  - spring.datasource.username=?
  - spring.datasource.password=?
