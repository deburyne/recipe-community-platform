# 🍳 Recipe Community Platform

재료 기반 레시피 추천 플랫폼 백엔드 API 서버입니다.

## 🛠 기술 스택
- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Security + JWT**
- **Spring Data JPA**
- **MySQL**
- **Swagger (SpringDoc OpenAPI)**

## 📌 주요 기능
- 회원가입 / 로그인 (JWT 인증)
- 레시피 등록 / 조회 / 수정 / 삭제 (CRUD)
- 재료 기반 레시피 추천

## 📂 프로젝트 구조
src
└── main
└── java
└── com.recipe.platform
├── domain
│   ├── user        # 회원 도메인
│   └── recipe      # 레시피 도메인
└── global
├── config      # Security 설정
├── exception   # 예외 처리
├── filter      # JWT 필터
└── util        # JWT 유틸
## 🔑 API 목록

### 회원 API
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| POST | /api/users/signup | 회원가입 | 불필요 |
| POST | /api/users/login | 로그인 | 불필요 |

### 레시피 API
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| POST | /api/recipes | 레시피 등록 | 필요 |
| GET | /api/recipes | 전체 레시피 조회 | 필요 |
| GET | /api/recipes/{id} | 특정 레시피 조회 | 필요 |
| PUT | /api/recipes/{id} | 레시피 수정 | 필요 |
| DELETE | /api/recipes/{id} | 레시피 삭제 | 필요 |
| GET | /api/recipes/recommend | 재료 기반 추천 | 필요 |

## 🚀 실행 방법
1. MySQL에 `recipe_db` 데이터베이스 생성
2. `application.yml` 에 DB 비밀번호 설정
3. 서버 실행 후 Swagger 확인: `http://localhost:8080/swagger-ui/index.html`