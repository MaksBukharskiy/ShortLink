# ShortLink (URL Shortener with Spring Boot)

A production-ready URL shortening service built with Spring Boot and PostgreSQL. Features JWT authentication, link tagging, click analytics, and advanced JPA usage — all in a clean, modular backend architecture.

A simple and secure link shortening service built with **Java + Spring Boot**.  
Perfect for learning Spring Security, JWT, input validation, and REST API design.

🎯 **Goal**: Build a production-ready URL shortener focused on security and clean code.

---

## ✨ Features

- [x] User registration and login with JWT
- [x] Create short links (`/api/links`)
- [x] Test public endpoint (`/message/test/test-permit-all`)
- [x] Redirect via short code (e.g., `/s/A1B2C3D4`)
- [x] URL validation: only `http://` or `https://`
- [x] Protection against XSS and dangerous schemes (`javascript:`, ``)
- [x] Global error handling
- [x] Click statistics
- [x] Service, Security, API testing (JUnit, Mockito, MockMvc)
- [x] Rate limiting
- [x] QR code generation for short links (`/api/qr/{code}`)
- [x] Theme Setting and auto Theme on (`api/user/theme`, `api/user/me`)
- [x] Tag links for categorization (`work`, `meeting`, etc.)(`/api/links`)

---    

## 🔐 Security

- Passwords are hashed using `BCryptPasswordEncoder`
- Authentication via JWT (`HS512`, 512+ bits)
- Token passed in header: `Authorization: Bearer <token>`
- URL validation via `@Pattern` — prevents injection attacks
- All endpoints secured except:
    - `/api/auth/**`
    - `/s/{code}`
    - `/api/qr/{code}`
- Comprehensive testing of services, security, and API logic using JUnit, Mockito, and MockMvc

---

## 🛠️ Tech Stack

| Category               | Technologies & Tools                                                                 |
|------------------------|---------------------------------------------------------------------------------------|
| **Language**           | Java 17                                                                               |
| **Framework**          | Spring Boot 3.5.6, Spring Web, Spring Validation, Spring Security                     |
| **ORM / Data Access**  | Spring Data JPA, Hibernate                                                            |
| **Database**           | PostgreSQL 15                                                                         |
| **Application Server** | Embedded Tomcat                                                                       |
| **Build Tool**         | Gradle                                                                                |
| **IDE**                | IntelliJ IDEA 2025                                                                    |
| **Version Control**    | Git, Warp (SSH client)                                                                |
| **Testing**            | JUnit 5, Mockito, MockMvc, Spring Test                                                |
| **Additional Tools**   | REST API, Postman, Lombok, Bean Validation API, HikariCP, Java Collections, ZXing     |

---

## 🚀 How to Run

### 1. Build the project
```bash
./gradlew build