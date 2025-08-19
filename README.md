# 🏥 Hospital Management System - Microservices Architecture

This project is a **Hospital Management System** built using **Spring Boot Microservices** with **Eureka Discovery, Config Server, API Gateway, and Docker Compose**.  
It provides functionalities to manage **patients, doctors, and appointments** with separate databases for each service.

---

## 🚀 Features

- **Patient Management**: Add, update, delete, and search for patients.  
- **Doctor Management**: Manage doctors with specialization and years of experience.  
- **Appointment Management**: Book, update, cancel, and check appointment availability.   
- **Eureka Discovery**: Service registration and discovery.  
- **API Gateway**: Centralized routing for all services.  
- **Spring Cloud Config**: Centralized configuration management.  
- **MySQL Databases**: Separate DB for Patients, Doctors, Appointments.  
- **Swagger Documentation** for all services.  
- **Docker Compose Integration** to start all services easily.  

---

## ✅ Tech Stack

- **Java 17**  
- **Spring Boot 3**  
- **Spring Cloud Netflix Eureka**  
- **Spring Cloud Config**  
- **Spring Cloud Gateway**  
- **Spring Data JPA + Hibernate**  
- **MySQL**  
- **Docker & Docker Compose**  
- **Swagger / OpenAPI**  
- **Lombok**  

---

## 🏗️ System Architecture

```mermaid
graph TB
    %% Clients
    Web[Web Browser/Client]
    Mobile[Mobile App]
    Postman[API Client]
    
    %% API Gateway
    Gateway[API Gateway<br/>:8072]
    
    %% Service Registry
    Eureka[Eureka Server<br/>:8761]
    
    %% Config Server
    Config[Config Server<br/>:8071]
    
    %% Microservices
    Patient[Patient Service<br/>:9191]
    Doctor[Doctor Service<br/>:9292]
    Appointment[Appointment Service<br/>:9393]
    
    %% Databases
    DB1[(Patient DB<br/>:3307)]
    DB2[(Doctor DB<br/>:3308)]
    DB3[(Appointment DB<br/>:3309)]
    
    %% Connections
    Web --> Gateway
    Mobile --> Gateway
    Postman --> Gateway
    
    Gateway --> Patient
    Gateway --> Doctor
    Gateway --> Appointment
    
    Patient --> Eureka
    Doctor --> Eureka
    Appointment --> Eureka
    Gateway --> Eureka
    
    Patient --> Config
    Doctor --> Config
    Appointment --> Config
    Gateway --> Config
    
    Patient --> DB1
    Doctor --> DB2
    Appointment --> DB3
```

---

## 🔄 Service Registration Flow

```mermaid
sequenceDiagram
    participant ConfigServer as Config Server<br/>:8071
    participant Eureka as Eureka Server<br/>:8761
    participant Patient as Patient Service<br/>:9191
    participant Doctor as Doctor Service<br/>:9292
    participant Appointment as Appointment Service<br/>:9393
    participant Gateway as API Gateway<br/>:8072
    
    Note over Patient,ConfigServer: Initialization & Registration Phase
    
    Patient->>ConfigServer: GET /patient-service/default
    ConfigServer-->>Patient: Return configuration
    Patient->>Eureka: POST /eureka/apps/PATIENT-SERVICE
    Eureka-->>Patient: 200 OK (Registered)
    
    Doctor->>ConfigServer: GET /doctor-service/default
    ConfigServer-->>Doctor: Return configuration
    Doctor->>Eureka: POST /eureka/apps/DOCTOR-SERVICE
    Eureka-->>Doctor: 200 OK (Registered)
    
    Appointment->>ConfigServer: GET /appointment-service/default
    ConfigServer-->>Appointment: Return configuration
    Appointment->>Eureka: POST /eureka/apps/APPOINTMENT-SERVICE
    Eureka-->>Appointment: 200 OK (Registered)
    
    Gateway->>ConfigServer: GET /gateway-service/default
    ConfigServer-->>Gateway: Return configuration
    Gateway->>Eureka: GET /eureka/apps
    Eureka-->>Gateway: List of available services
```

---

## 🗃️ Database Schema

```mermaid
erDiagram
    PATIENTS {
        bigint patient_id PK
        varchar name
        int age
        varchar phone
        varchar email
        varchar password
        varchar address
        varchar disease
        varchar blood_type
        datetime date_of_registration
        varchar gender
    }
    
    DOCTORS {
        bigint doctor_id PK
        varchar name
        varchar email
        varchar password
        varchar phone
        int age
        int year_of_experience
        varchar specialty
        varchar address
        varchar gender
    }
    
    APPOINTMENTS {
        bigint id PK
        datetime date
        varchar reason
    }
```

---

## 📊 API Request Flow

```mermaid
sequenceDiagram
    participant Client as Client
    participant Gateway as API Gateway<br/>:8072
    participant Eureka as Eureka Server<br/>:8761
    participant Service as Service (Patient/Doctor/Appointment)
    participant DB as MySQL Database
    
    Client->>Gateway: GET /api/patients
    Gateway->>Eureka: GET /eureka/apps/PATIENT-SERVICE
    Eureka-->>Gateway: Patient Service address
    Gateway->>Service: Forward request to Patient Service
    Service->>DB: SELECT query from patients table
    DB-->>Service: Query result
    Service-->>Gateway: JSON response
    Gateway-->>Client: 200 OK with data
```

---

## 🐳 Docker Deployment Architecture

```mermaid
graph TB
    subgraph Docker Host
        subgraph Microservices Network
            Gateway[gateway:8072]
            Eureka[eureka:8761]
            Config[config:8071]
            Patient[patient:9191]
            Doctor[doctor:9292]
            Appointment[appointment:9393]
        end
        
        subgraph Database Containers
            DB1[patient_db:3307]
            DB2[doctor_db:3308]
            DB3[appointment_db:3309]
        end
    end
    
    Internet -.-> Gateway
    
    Patient --> DB1
    Doctor --> DB2
    Appointment --> DB3
    
    Patient --> Eureka
    Doctor --> Eureka
    Appointment --> Eureka
    Gateway --> Eureka
    
    Patient --> Config
    Doctor --> Config
    Appointment --> Config
    Gateway --> Config
```

---

## 🐳 Run with Docker

Clone the repository:

```bash
git clone https://github.com/Seif-Elsokary/Hospital-Management-System.git
cd Hospital-Management-System/docker-compose/default
```

Start all services using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- 3 MySQL databases (patients:3307, doctors:3308, appointments:3309)
- Eureka Discovery Server (:8761)
- Config Server (:8071)
- API Gateway (:8072)
- Patient Microservice (:9191)
- Doctor Microservice (:9292)
- Appointment Microservice (:9393)

---

## 🌍 Services Endpoints

| Service        | Port | Endpoint                                                                         |
|----------------|------|----------------------------------------------------------------------------------|
| Patient MS     | 9191 | [http://localhost:9191/api/patients](http://localhost:9191/api/patients)         |
| Doctor MS      | 9292 | [http://localhost:9292/api/doctors](http://localhost:9292/api/doctors)           |
| Appointment MS | 9393 | [http://localhost:9393/api/appointments](http://localhost:9393/api/appointments) |
| Config Server  | 8071 | [http://localhost:8071](http://localhost:8071)                                   |
| Eureka Server  | 8761 | [http://localhost:8761](http://localhost:8761)                                   |
| API Gateway    | 8072 | [http://localhost:8072](http://localhost:8072)                                   |

---

## 📋 API Overview

### Patient Service
| Method | Endpoint              | Description          |
|--------|-----------------------|----------------------|
| GET    | `/api/patients`       | List all patients    |
| GET    | `/api/patients/{id}`  | Get patient by ID    |
| POST   | `/api/patients`       | Create a new patient |
| PUT    | `/api/patients/{id}`  | Update patient       |
| DELETE | `/api/patients/{id}`  | Delete patient       |

### Doctor Service
| Method | Endpoint             | Description         |
|--------|----------------------|---------------------|
| GET    | `/api/doctors`       | List all doctors    |
| GET    | `/api/doctors/{id}`  | Get doctor by ID    |
| POST   | `/api/doctors`       | Create a new doctor |
| PUT    | `/api/doctors/{id}`  | Update doctor       |
| DELETE | `/api/doctors/{id}`  | Delete doctor       |

### Appointment Service
| Method | Endpoint                 | Description             |
|--------|--------------------------|-------------------------|
| GET    | `/api/appointments`      | List all appointments   |
| GET    | `/api/appointments/{id}` | Get appointment by ID   |
| POST   | `/api/appointments`      | Create a new appointment|
| PUT    | `/api/appointments/{id}` | Update appointment      |
| DELETE | `/api/appointments/{id}` | Cancel appointment      |

---

## 📂 Project Structure

```
Hospital-Management-System/
│
├── configserver/          # Centralized configuration service
├── eurekaserver/          # Service registry
├── gatewayserver/         # API Gateway
├── patients/              # Patient microservice
├── doctors/               # Doctor microservice
├── appointments/          # Appointment microservice
├── docker-compose/        # Docker Compose setup
└── README.md              # Project documentation
```

---

## 🔧 Configuration

Each microservice has its own configuration file in the Config Server repository. The services are configured to connect to their respective databases:

- Patient Service: `patient_db` (port 3307)
- Doctor Service: `doctor_db` (port 3308)
- Appointment Service: `appointment_db` (port 3309)

---

## 🎯 Usage Examples

### Create a Patient
```bash
curl -X POST http://localhost:9191/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "123-456-7890",
    "address": "123 Main St, City, State",
    "dateOfBirth": "1990-01-01",
    "medicalHistory": "No significant medical history"
  }'
```

### Create a Doctor
```bash
curl -X POST http://localhost:9292/api/doctors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dr. Jane Smith",
    "specialization": "Cardiology",
    "yearsOfExperience": 10,
    "email": "jane.smith@hospital.com",
    "phone": "555-1234"
  }'
```

### Book an Appointment
```bash
curl -X POST http://localhost:9393/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 1,
    "doctorId": 1,
    "appointmentDate": "2023-12-15T10:00:00",
    "notes": "Regular checkup"
  }'
```

---

## 🛠️ Development

To run services individually during development:

1. Start Eureka Server (port 8761)
2. Start Config Server (port 8071)
3. Start API Gateway (port 8072)
4. Start microservices in any order:
   - Patient Service (port 9191)
   - Doctor Service (port 9292)
   - Appointment Service (port 9393)

Each service will register itself with Eureka and fetch its configuration from the Config Server.

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check [issues page](https://github.com/Seif-Elsokary/Hospital-Management-System/issues).

---

## 📧 Contact

Seif Elsokary - [seifsoliman809@gmail.com](mailto:seifsoliman809@gmail.com)

Project Link: [https://github.com/Seif-Elsokary/Hospital-Management-System](https://github.com/Seif-Elsokary/Hospital-Management-System)
