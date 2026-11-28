# TrustDesk – AI Support Operations Agent

An AI-powered customer support operations platform built using Java 21 and Spring Boot.

## Overview

TrustDesk helps support agents analyze customer tickets, retrieve company policies, generate AI-powered responses with citations, and recommend actions with human approval.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Angular 21 (Frontend – planned)
- Spring AI (planned)
- Docker (planned)

## Architecture

TrustDesk follows a modular monolithic architecture with separate modules for:

- Customer Management
- Order Management
- Ticket Management
- AI Processing
- Knowledge Base and RAG
- Human Approval Workflow
- AI Guardrails
- Evaluation and Tracing

## Project Structure

```text
trustdesk-backend/
├── src/
│   ├── main/
│   │   ├── java/com/trustdesk/
│   │   └── resources/
│   └── test/
├── build.gradle
├── settings.gradle
└── README.md
```

## Local Setup

### Prerequisites

- Java 21
- PostgreSQL
- Gradle Wrapper

### Database Configuration

Create a PostgreSQL database:

```sql
CREATE DATABASE trustdesk_db;
```

Configure `src/main/resources/application.properties`:

```properties
spring.application.name=trustdesk
spring.datasource.url=jdbc:postgresql://localhost:5432/trustdesk_db
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

Set the database password in your environment before starting the application.

### Run Application

Windows PowerShell:

```powershell
$env:DB_PASSWORD="YOUR_PASSWORD"
.\gradlew.bat bootRun
```

## Health Check

```http
GET http://localhost:8080/api/health
```

## Development Status

- [x] Spring Boot project initialization
- [x] PostgreSQL database setup
- [ ] Customer management
- [ ] Order management
- [ ] Ticket management
- [ ] AI ticket triage
- [ ] RAG-based knowledge retrieval
- [ ] AI response drafting
- [ ] Human approval workflow
- [ ] AI guardrails and evaluation
- [ ] Angular dashboard

## Security

Never commit database passwords, API keys, JWT secrets, or other credentials to GitHub.

## License

To be decided.


## Imp Words
1. Retrieval (रिट्रीवल) – Knowledge Base में से सवाल से संबंधित जानकारी या documents खोजना।

2. Grounding (ग्राउंडिंग) – AI का जवाब retrieved documents में मौजूद वास्तविक जानकारी पर आधारित रखना, ताकि AI अपनी तरफ से गलत जानकारी न बनाए।

3. Guardrails (गार्डरेल्स) – AI को unsafe काम करने, गलत instructions follow करने या company policies bypass करने से रोकने के लिए सुरक्षा नियम लगाना।