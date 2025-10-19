## Rate Limit Checker

A Spring Boot application that implements **rate limiting** using the **Token Bucket Algorithm**.
This service allows controlling request rates per client (e.g., per IP address) to prevent abuse and ensure fair resource usage.

### Overview

The **Token Bucket Algorithm** provides a flexible and efficient rate-limiting mechanism.
Each client (identified by IP or key) has a "bucket" that fills with tokens at a fixed rate.
Every request consumes one token. When the bucket is empty, further requests are rejected until new tokens are added.

### Features

* Rate limiting per IP (or configurable key)
* Adjustable token refill rate and bucket size
* In-memory implementation (with easy extension to Redis or DB)
* Logging of remaining request limits
* Built with **Spring Boot** and **Java Streams**

### How It Works

1. Each incoming request is identified by the client IP.
2. The system checks the client’s token bucket:

    * If tokens are available → request is allowed, one token is removed.
    * If no tokens remain → request is rejected (HTTP 429).
3. Tokens are replenished over time at the configured rate.

### Technologies Used

* **Java 21+**
* **Spring Boot 3+**
* **Maven**
* **Slf4j / Logback** for logging

### Configuration

You can adjust rate-limiting parameters in `application.yml`:

```yaml
app:
  rateLimit: 100 # max tokens per bucket
  refillInterval: 60000 # time between token refills
```

### Running the Application

```bash
mvn clean install
mvn spring-boot:run
```

or with Docker:

```bash
docker build -t rate-limiter .
docker run -p 8080:8080 rate-limiter
```

Access the application at:
[http://localhost:8080](http://localhost:8080)
