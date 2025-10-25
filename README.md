# ShortLink(URL Shortener with Spring Boot)

A production-ready URL shortening service built with Spring Boot and PostgreSQL. Features JWT authentication, link tagging, click analytics, and advanced JPA usage — all in a clean, modular backend architecture.

A simple and secure link shortening service built with **Java + Spring Boot**.  
Perfect for learning Spring Security, JWT, input validation, and REST API design.

🎯 **Goal**: Build a production-ready URL shortener focused on security and clean code.

---

## ✨ Features

- [x] User registration and login with JWT
- [x] Create short links (`/api/links`)
- [x] Redirect via short code (e.g., `/r/A1B2C3D4`)
- [x] URL validation: only `http://` or `https://`
- [x] Protection against XSS and dangerous schemes (`javascript:`, ``)
- [x] Global error handling
- [x] Click statistics
- [X] Rate limiting

---

## 🔐 Security

- Passwords are hashed using `BCryptPasswordEncoder`
- Authentication via JWT (`HS512`, 512+ bits)
- Token passed in header: `Authorization: Bearer <token>`
- URL validation via `@Pattern` — prevents injection attacks
- All endpoints secured except `/api/auth/**`

---

## 🛠 Technologies

| Technology | Purpose |
|----------|--------|
| Java 17 | Programming language |
| Spring Boot 3 | Application framework |
| Spring Security | Authentication & authorization |
| Spring Data JPA | Database interaction |
| PostgreSQL | Persistent storage |
| JWT | Stateless authentication |
| Hibernate | ORM |
| Gradle | Build tool |
| Postman | API testing |

---

## 🚀 How to Run

### 1. Build the project
```bash
./gradlew build
