# 🔐 Secure App

Secure App is a robust, production-grade **Spring Boot REST API** built to demonstrate strong backend security practices using modern techniques and libraries. This project showcases your proficiency in securing APIs with features like **JWT Authentication**, **Role-Based Access Control (RBAC)**, **Account Activation via Email**, **Argon2d password encryption**, and **protection against brute force attacks and DoS attacks**.

<p align="center">
  <img src="https://img.shields.io/badge/SpringBoot-REST_API-green?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Security-HS256%20%7C%20JWT%20%7C%20Argon2d%20%7C%20Rate%20Limiting-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" />
</p>

---

## 🌟 Features

| Feature                                      | Description                                                                                             |
|---------------------------------------------|---------------------------------------------------------------------------------------------------------|
| **Spring Security**                         | Industry-standard authentication & authorization configuration                                          |
| **JWT Authentication (HS256)**              | Stateless and secure authentication via JSON Web Tokens                                                 |
| **RBAC (Role-Based Access Control)**        | Admin/user role segregation and endpoint protection                                                     |
| **Account Activation**                      | Email verification workflow before login is enabled                                                     |
| **Argon2d Password Hashing**                | Advanced password encryption algorithm for strong password security                                     |
| **Brute Force Attack Prevention**           | Protects login endpoints by tracking failed attempts and locking accounts after repeated failures        |
| **DoS Protection via Rate Limiting**        | Uses in-memory bucket (Bucket4j) to limit request rates per IP, mitigating denial-of-service attacks    |

---

## 🛡 Security Highlights

✅ **Argon2d encryption** ensures user passwords are securely hashed with resistance to GPU cracking.

✅ **JWT tokens** allow for scalable, stateless authentication across your backend.

✅ **Role-based access control (RBAC)** lets you easily control which endpoints are accessible to which user roles.

✅ **Account lockout mechanism** tracks failed login attempts and temporarily locks accounts after too many failures, defending against brute force login attempts.

✅ **In-memory rate limiting** using Bucket4j applies DoS protection by throttling the number of requests allowed per client IP.

---

## 🚀 Technologies Used

- Spring Boot 3.x
- Spring Security 6.x
- JWT (HS256) via `jjwt`
- Argon2d password encoding (`Argon2PasswordEncoder`)
- Bucket4j (for in-memory rate limiting)
- JavaMailSender (for email activation)
- Lombok

---

## 📖 License

This project is licensed under the MIT License.
