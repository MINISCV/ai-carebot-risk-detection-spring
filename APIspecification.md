# **고독사 예방을 위한 시니어케어 돌봄로봇 데이터 분석 API 명세서**

**버전:** 1.1.0

**최종 수정일:** 2025-09-30

---

## **1. 개요**

이 문서는 **고독사 예방을 위한 시니어케어 돌봄로봇 데이터 분석**에서 제공하는 REST API의 명세에 대해 기술합니다. 본 API는 **회원, 인형, 시니어 관리, 대화 데이터 분석, 대시보드 및 공통 코드** 기능을 제공하며, 개발자들이 애플리케이션과 서비스를 쉽게 연동할 수 있도록 돕습니다.

---

## **2. 기본 정보**

### **2.1. 기본 URL**

모든 API 요청의 기본 URL은 다음과 같습니다.

*   `http://127.0.0.1:8080/api`

### **2.2. 요청 형식**

*   대부분의 요청 본문은 `JSON` 형식이어야 합니다. 모든 JSON 필드는 snake_case를 따릅니다.
*   `Content-Type` 헤더는 `application/json`으로 설정해야 합니다.
*   파일 업로드의 경우 `Content-Type`은 `multipart/form-data`를 사용합니다.

### **2.3. 응답 형식**

*   모든 응답의 본문은 `JSON` 형식으로 제공됩니다. 모든 JSON 필드는 snake_case를 따릅니다.
*   성공적인 응답은 일반적으로 HTTP 상태 코드 `200 OK`, `201 Created`, `204 No Content`를 반환합니다.
*   실패한 응답은 `4xx` 또는 `5xx` 범위의 HTTP 상태 코드를 반환하며, 응답 본문에 에러에 대한 상세 정보를 포함합니다.

---

## **3. 인증**

본 API는 로그인을 제외한 일부 공개 API를 제외하고 모든 요청에 대해 인증이 필요합니다. 인증 방식으로는 `JWT Bearer Token`을 사용하며, `ADMIN` 권한이 필요합니다.

### **3.1. JWT Bearer Token 인증**

로그인 API를 통해 발급받은 액세스 토큰(Access Token)을 모든 요청 헤더에 포함하여 인증을 수행합니다.

*   **Header:** `Authorization: Bearer {your_access_token}`

토큰이 만료되었을 경우, Refresh Token을 사용하여 새로운 Access Token을 발급받을 수 있습니다.

---

## **4. 에러 코드**

API 요청 실패 시 반환되는 공통 에러 코드입니다.

| HTTP 상태 코드 | 설명 |
| :--- | :--- |
| `400 Bad Request` | 요청 파라미터가 누락되었거나 형식이 올바르지 않습니다. |
| `401 Unauthorized` | 인증되지 않은 요청입니다. 유효한 인증 정보가 필요합니다. |
| `403 Forbidden` | 해당 리소스에 접근할 권한이 없습니다. |
| `404 Not Found` | 요청한 리소스를 찾을 수 없습니다. |
| `405 Method Not Allowed` | 허용되지 않은 HTTP 메서드로 요청했습니다. |
| `409 Conflict` | 요청이 서버의 현재 상태와 충돌했습니다. (예: 데이터 중복) |
| `500 Internal Server Error` | 서버 내부에서 오류가 발생했습니다. |
| `503 Service Unavailable` | 외부 서비스(분석 서버)와의 통신에 실패했습니다. |

---

## **5. API 엔드포인트**

### **1. 인증 (Authentication)**

#### **1.1. `POST /login` - 로그인**

사용자 아이디와 비밀번호로 로그인하여 시스템 접근을 위한 JWT 토큰을 발급받습니다.

*   **Method:** `POST`
*   **URL:** `/login`
*   **Description:** 로그인 성공 시, 응답 헤더에 Access Token을, 응답 쿠키에 Refresh Token을 포함하여 반환합니다.
*   **인증:** 불필요

*   **Request Body:**

| 필드 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `username` | `string` | Y | 사용자 아이디 |
| `password` | `string` | Y | 비밀번호 |

*   **Example Request Body:**
```json
{
  "username": "admin",
  "password": "password123"
}
```

*   **Success Response (`200 OK`):**
    *   **Header:**
        *   `Authorization`: `Bearer {access_token}`
    *   **Cookie:**
        *   `refresh_token`: `{refresh_token}` (HttpOnly)
    *   응답 본문은 없습니다.

*   **Error Responses:**
    *   `401 Unauthorized`: 아이디 또는 비밀번호가 일치하지 않을 경우 발생합니다.

---
#### **1.2. `POST /refresh` - Access Token 갱신**

유효한 Refresh Token을 사용하여 만료된 Access Token을 갱신합니다.

*   **Method:** `POST`
*   **URL:** `/refresh`
*   **Description:** 요청 시 쿠키에 담긴 Refresh Token을 검증하여 새로운 Access Token을 발급하고 응답 헤더에 담아 반환합니다.
*   **인증:** 불필요 (Refresh Token 쿠키 필요)

*   **Request Cookies:**

| 쿠키 이름 | 필수 | 설명 |
| :--- | :--- | :--- |
| `refresh_token` | Y | Access Token 갱신에 사용되는 Refresh Token |

*   **Success Response (`200 OK`):**
    *   **Header:** `Authorization` 헤더에 새로운 Access Token이 포함됩니다. (`Bearer {new_access_token}`)
    *   응답 본문은 없습니다.

*   **Error Responses:**
    *   `401 Unauthorized`: Refresh Token이 없거나 유효하지 않은 경우 발생합니다.

***

### **2. 회원 (Members)**

#### **2.1. `POST /members` - 회원 가입**

시스템에 새로운 관리자 회원을 등록합니다.

*   **Method:** `POST`
*   **URL:** `/members`
*   **Description:** 새로운 회원을 생성합니다. 사용자 이름(username)은 시스템에서 유일해야 합니다.
*   **인증:** 불필요

*   **Request Body:**

| 필드 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `username` | `string` | Y | 사용자의 아이디 (중복 불가) |
| `password` | `string` | Y | 비밀번호 |

*   **Example Request Body:**
```json
{
  "username": "new_admin",
  "password": "password123"
}
```

*   **Success Response (`201 Created`):**
    *   생성된 회원의 정보를 반환합니다. (비밀번호 제외)
```json
{
    "username": "new_admin",
    "role": "ROLE_ADMIN",
    "enabled": true
}
```

*   **Error Responses:**
    *   `400 Bad Request`: `username` 또는 `password`가 비어있을 경우 발생합니다.
    *   `409 Conflict`: 이미 존재하는 사용자 이름일 경우 발생합니다.

---
#### **2.2. `GET /members` - 전체 회원 목록 조회**

시스템에 등록된 모든 회원의 목록을 조회합니다.

*   **Method:** `GET`
*   **URL:** `/members`
*   **Description:** 전체 회원 목록을 조회합니다.
*   **인증:** 필수

*   **Success Response (`200 OK`):**
    *   회원 정보 객체 배열을 반환합니다.
```json
[
    {
        "username": "admin",
        "role": "ROLE_ADMIN",
        "enabled": true
    },
    {
        "username": "new_admin",
        "role": "ROLE_ADMIN",
        "enabled": true
    }
]
```
---
#### **2.3. `GET /members/{username}` - 특정 회원 정보 조회**

특정 회원의 상세 정보를 조회합니다.

*   **Method:** `GET`
*   **URL:** `/members/{username}`
*   **Description:** Username으로 특정 회원의 정보를 조회합니다.
*   **인증:** 필수

*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `username` | `string`| Y | 조회할 회원의 username |

*   **Success Response (`200 OK`):**
    *   조회된 회원의 정보를 반환합니다.
```json
{
    "username": "admin",
    "role": "ROLE_ADMIN",
    "enabled": true
}
```

*   **Error Responses:**
    *   `404 Not Found`: 해당 username의 회원이 존재하지 않을 경우 발생합니다.
---
#### **2.4. `PUT /members/{username}` - 회원 정보 수정**

특정 회원의 역할(role) 및 활성화(enabled) 상태를 수정합니다.

*   **Method:** `PUT`
*   **URL:** `/members/{username}`
*   **Description:** Username으로 특정 회원의 정보를 수정합니다.
*   **인증:** 필수

*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `username` | `string`| Y | 수정할 회원의 username |

*   **Request Body:**

| 필드 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `role` | `string` | Y | 변경할 회원의 역할. 허용 값: `"ROLE_ADMIN"`, `"ROLE_MEMBER"` |
| `enabled` | `boolean`| Y | 계정 활성화 여부 |

*   **Example Request Body:**
```json
{
    "role": "ROLE_MEMBER",
    "enabled": false
}
```

*   **Success Response (`200 OK`):**
    *   수정된 회원의 정보를 반환합니다.
```json
{
    "username": "admin",
    "role": "ROLE_MEMBER",
    "enabled": false
}
```

*   **Error Responses:**
    *   `404 Not Found`: 해당 username의 회원이 존재하지 않을 경우 발생합니다.
---
#### **2.5. `DELETE /members/{username}` - 회원 삭제**

특정 회원을 시스템에서 삭제합니다.

*   **Method:** `DELETE`
*   **URL:** `/members/{username}`
*   **Description:** Username으로 특정 회원을 삭제합니다.
*   **인증:** 필수

*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `username` | `string`| Y | 삭제할 회원의 username |

*   **Success Response (`204 No Content`):**
    *   응답 본문이 없습니다.

*   **Error Responses:**
    *   `404 Not Found`: 해당 username의 회원이 존재하지 않을 경우 발생합니다.

***

### **3. 인형 (Dolls)**

#### **3.1. `POST /dolls` - 인형 생성**

새로운 인형을 시스템에 등록합니다.

*   **Method:** `POST`
*   **URL:** `/dolls`
*   **Description:** 새로운 인형을 생성합니다. 인형 ID는 시스템에서 유일해야 합니다.
*   **인증:** 필수

*   **Request Body:**

| 필드 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | `string` | Y | 생성할 인형의 고유 ID |

*   **Example Request Body:**
```json
{
  "id": "doll-serial-123"
}
```

*   **Success Response (`201 Created`):**
    *   생성된 인형의 정보를 반환합니다.
```json
{
    "id": "doll-serial-123",
    "senior_id": null
}
```

*   **Error Responses:**
    *   `400 Bad Request`: 요청 본문 형식이 잘못되었거나 `id` 필드가 비어있을 경우 발생합니다.
    *   `409 Conflict`: 이미 존재하는 인형 ID일 경우 발생합니다.


---
#### **3.2. `GET /dolls` - 전체 인형 목록 조회**

시스템에 등록된 모든 인형의 목록을 조회합니다.

*   **Method:** `GET`
*   **URL:** `/dolls`
*   **Description:** 전체 인형 목록과 각 인형에 할당된 시니어 ID를 함께 조회합니다.
*   **인증:** 필수

*   **Success Response (`200 OK`):**
    *   인형 객체 배열을 반환합니다.
```json
[
    {
        "id": "doll-serial-123",
        "senior_id": 1
    },
    {
        "id": "doll-serial-456",
        "senior_id": null
    }
]
```
---
#### **3.3. `GET /dolls/{id}` - 특정 인형 정보 조회**

특정 ID를 가진 인형의 상세 정보를 조회합니다.

*   **Method:** `GET`
*   **URL:** `/dolls/{id}`
*   **Description:** ID로 특정 인형의 정보를 조회합니다.
*   **인증:** 필수

*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | `string`| Y | 조회할 인형의 ID |

*   **Success Response (`200 OK`):**
    *   조회된 인형의 정보를 반환합니다.
```json
{
    "id": "doll-serial-123",
    "senior_id": 1
}
```

*   **Error Responses:**
    *   `404 Not Found`: 해당 ID의 인형이 존재하지 않을 경우 발생합니다.

---
#### **3.4. `DELETE /dolls/{id}` - 인형 삭제**

특정 ID를 가진 인형을 시스템에서 삭제합니다.

*   **Method:** `DELETE`
*   **URL:** `/dolls/{id}`
*   **Description:** ID로 특정 인형을 삭제합니다. 해당 인형이 시니어에게 할당되어 있는 경우 삭제할 수 없습니다.
*   **인증:** 필수

*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | `string`| Y | 삭제할 인형의 ID |

*   **Success Response (`204 No Content`):**
    *   응답 본문이 없습니다.

*   **Error Responses:**
    *   `404 Not Found`: 해당 ID의 인형이 존재하지 않을 경우 발생합니다.
    *   `409 Conflict`: 인형이 특정 시니어에게 할당되어 있어 삭제할 수 없는 경우 발생합니다.

***

### **4. 시니어 (Seniors)**

#### **4.1. `POST /seniors` - 시니어 등록**

새로운 시니어 정보를 등록하고, 등록되지 않은 인형을 할당합니다.

*   **Method:** `POST`
*   **URL:** `/seniors`
*   **Description:** 새로운 시니어 정보를 생성하고, 사용 가능한 인형을 할당합니다. 요청은 `senior` (JSON) 파트와 `photo` (file) 파트로 구성됩니다.
*   **인증:** 필수

*   **Request Body (Form Data):**

| 파트 이름 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `senior`| `json` | Y | 시니어 정보 객체. 아래의 필드들을 포함합니다. |
| `photo` | `file` | N | 시니어 프로필 사진 파일 |

*   **`senior` JSON 파트 상세:**

| 필드 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `doll_id` | `string` | Y | 할당할 인형의 고유 ID |
| `name` | `string` | Y | 시니어 이름 |
| `birth_date` | `string` | Y | 생년월일 (YYYY-MM-DD) |
| `sex` | `string` | Y | 성별. 허용 값: `"MALE"`, `"FEMALE"` |
| `residence` | `string` | Y | 거주 형태. 허용 값: `"SINGLE_FAMILY_HOME"`, `"MULTIPLEX_HOUSING"`, `"MULTI_FAMILY_HOUSING"`, `"APARTMENT"` |
| `phone` | `string` | Y | 연락처 (010-1234-5678 형식) |
| `address` | `string` | Y | 주소 전체 |
| `gu` | `string` | Y | 주소(구). `GET /api/administrative-districts`를 통해 얻은 `gu_code` 값을 사용합니다. |
| `dong` | `string` | Y | 주소(동). `GET /api/administrative-districts`를 통해 얻은 `dong_code` 값을 사용합니다. |
| `note` | `string` | N | 시니어 관련 특이사항 |
| `guardian_name`|`string` | Y | 보호자 이름 |
| `guardian_phone`|`string` | Y | 보호자 연락처 (010-1234-5678 형식) |
| `relationship`|`string` | Y | 보호자와의 관계 |
| `guardian_note`|`string` | N | 보호자 관련 특이사항 |
| `diseases`| `string`| N | 앓고 있는 질병 |
| `medications`| `string`| N | 복용 중인 약물 |
| `disease_note`| `string`| N | 질병 관련 특이사항 |

*   **Success Response (`201 Created`):**
    *   생성된 시니어의 전체 정보를 반환합니다.
```json
{
    "id": 1,
    "doll_id": "doll-123",
    "name": "김어르신",
    "photo_url": "/api/seniors/photos/uuid-generated-filename.jpg",
    "birth_date": "1945-05-10",
    "sex": "FEMALE",
    "state": "POSITIVE",
    "residence": "APARTMENT",
    "phone": "010-1234-5678",
    "address": "대전광역시 동구 중앙동 123",
    "gu": "동구",
    "dong": "중앙동",
    "note": "거동이 불편하심",
    "guardian_name": "김보호",
    "guardian_phone": "010-8765-4321",
    "relationship": "자녀",
    "guardian_note": "오후 3-5시 연락 선호",
    "diseases": "고혈압",
    "medications": "혈압약",
    "disease_note": "매일 아침 복용"
}
```

*   **Error Responses:**
    *   `404 Not Found`: 요청된 `doll_id`의 인형을 찾을 수 없는 경우 발생합니다.
    *   `409 Conflict`: 해당 인형이 이미 다른 시니어에게 할당된 경우 발생합니다.

---
#### **4.2. `GET /seniors` - 전체 시니어 목록 조회 (검색 및 페이징)**
*   **Method:** `GET`
*   **URL:** `/seniors`
*   **Description:** 시스템에 등록된 시니어 목록을 검색 조건에 따라 페이징하여 조회합니다.
*   **인증:** 필수

*   **Query Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `page` | `integer` | N | 페이지 번호 (0부터 시작) |
| `size` | `integer` | N | 페이지당 항목 수 |
| `senior_id` | `long` | N | 시니어 ID |
| `name` | `string` | N | 시니어 이름 (부분 일치) |
| `phone` | `string` | N | 연락처 (부분 일치) |
| `sex` | `string` | N | 성별. 허용 값: `"MALE"`, `"FEMALE"` |
| `gu` | `string` | N | 주소(구). `GET /api/administrative-districts`를 통해 얻은 `gu_code` 값을 사용합니다. |
| `dong` | `string` | N | 주소(동). `GET /api/administrative-districts`를 통해 얻은 `dong_code` 값을 사용합니다. |
| `state` | `string` | N | 현재 상태. 허용 값: `"POSITIVE"`, `"DANGER"`, `"CRITICAL"`, `"EMERGENCY"` |
| `doll_id` | `string` | N | 할당된 인형 ID |
| `age_group`| `integer` | N | 연령대 (예: 60, 70, 80, 100(100세 이상)) |

*   **Success Response (`200 OK`):**
    *   페이징된 시니어 목록 정보를 반환합니다.
```json
{
    "content": [
        {
            "senior_id": 1,
            "name": "김어르신",
            "age": 80,
            "sex": "FEMALE",
            "gu": "동구",
            "dong": "중앙동",
            "state": "POSITIVE",
            "doll_id": "doll-123",
            "phone": "010-1234-5678",
            "created_at": "2025-09-30T10:00:00"
        }
    ],
    "page_number": 0,
    "page_size": 10,
    "total_elements": 1,
    "total_pages": 1,
    "is_last": true,
    "is_first": true
}
```
---
#### **4.3. `GET /seniors/{id}` - 특정 시니어 상세 정보 조회**
*   **Method:** `GET`
*   **URL:** `/seniors/{id}`
*   **Description:** ID로 특정 시니어의 상세 정보와 최근 분석 결과 5개를 조회합니다.
*   **인증:** 필수
*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | `long`| Y | 조회할 시니어의 ID |

*   **Success Response (`200 OK`):** 
    *   조회된 시니어의 상세 정보를 반환합니다.
```json
{
    "id": 1,
    "doll_id": "doll-123",
    "name": "김어르신",
    "photo_url": "/api/seniors/photos/uuid-generated-filename.jpg",
    "birth_date": "1945-05-10",
    "sex": "FEMALE",
    "state": "POSITIVE",
    "residence": "APARTMENT",
    "phone": "010-1234-5678",
    "address": "대전광역시 동구 중앙동 123",
    "gu": "동구",
    "dong": "중앙동",
    "note": "거동이 불편하심",
    "guardian_name": "김보호",
    "guardian_phone": "010-8765-4321",
    "relationship": "자녀",
    "guardian_note": "오후 3-5시 연락 선호",
    "diseases": "고혈압",
    "medications": "혈압약",
    "disease_note": "매일 아침 복용",
    "recent_overall_results": [
        {
            "id": 10,
            "label": "POSITIVE",
            "summary": "김어르신님의 최근 대화 분석 결과, POSITIVE 수준의 주의가 필요합니다.",
            "timestamp": "2025-09-28T14:00:00"
        }
    ]
}
```

*   **Error Responses:** `404 Not Found`: 해당 ID의 시니어가 존재하지 않을 경우 발생합니다.

---
#### **4.4. `PUT /seniors/{id}` - 시니어 정보 수정**
*   **Method:** `PUT`
*   **URL:** `/seniors/{id}`
*   **Content-Type:** `multipart/form-data`
*   **Description:** ID로 특정 시니어의 정보를 수정합니다. 요청 형식은 `POST /seniors`와 동일합니다.
*   **인증:** 필수
*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | `long`| Y | 수정할 시니어의 ID |

*   **Request Body:** `4.1. POST /seniors`의 Request Body 형식과 동일합니다.
*   **Success Response (`200 OK`):**
    *   수정된 시니어의 정보를 반환합니다. (`4.1`의 응답 형식과 동일)
*   **Error Responses:**
    *   `404 Not Found`: 해당 시니어 또는 변경하려는 인형이 존재하지 않을 경우 발생합니다.
    *   `409 Conflict`: 변경하려는 인형이 이미 다른 시니어에게 할당된 경우 발생합니다.

---
#### **4.5. `DELETE /seniors/{id}` - 시니어 삭제**
*   **Method:** `DELETE`
*   **URL:** `/seniors/{id}`
*   **Description:** ID로 특정 시니어를 삭제합니다. 삭제 시 할당되었던 인형은 '할당되지 않은' 상태가 됩니다.
*   **인증:** 필수
*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | `long`| Y | 삭제할 시니어의 ID |

*   **Success Response (`204 No Content`):**
    *   응답 본문이 없습니다.

*   **Error Responses:** `404 Not Found`: 해당 ID의 시니어가 존재하지 않을 경우 발생합니다.

***
### **5. 분석 (Analyze)**

#### **5.1. `POST /analyze` - 대화 파일 분석 요청**

인형과의 대화 내용이 담긴 CSV 파일을 업로드하여 감정 및 위험도 분석을 요청합니다.

*   **Method:** `POST`
*   **URL:** `/analyze`
*   **Description:** 대화 내용이 담긴 CSV 파일을 분석하고 그 결과를 데이터베이스에 저장합니다.
*   **인증:** 필수
*   **Content-Type:** `multipart/form-data`

*   **Request Body (Form Data):**

| 필드 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `file` | `file` | Y | 분석할 대화 내용이 담긴 CSV 파일 |

*   **CSV 파일 형식:**
    *   첫 번째 줄은 헤더(`doll_id,text,uttered_at`)이며, 분석 시에는 이 헤더를 기준으로 데이터를 읽습니다.
    *   각 줄은 `인형ID,대화내용,발화시각` 순서여야 합니다.
    *   `uttered_at`의 날짜/시간 형식은 `yyyy-MM-dd H:mm:ss` 입니다.
    *   **예시:**
        ```csv
        doll_id,text,uttered_at
        doll-123,"오늘 날씨가 좋네",2025-09-23 10:30:00
        doll-123,"점심은 뭘 먹을까?",2025-09-23 12:00:15
        ```

*   **Success Response (`201 Created`):**
    *   분석 결과를 담은 JSON 객체와 생성된 리소스의 ID를 반환합니다.
```json
{
    "id": 1,
    "overall_result": {
        "doll_id": "doll-123",
        "dialogue_count": 2,
        "char_length": 22,
        "label": "DANGER",
        "confidence_scores": {
            "positive": 0.1,
            "danger": 0.7,
            "critical": 0.15,
            "emergency": 0.05
        },
        "full_text": "오늘 날씨가 좋네 점심은 뭘 먹을까?",
        "reason": {
            "evidence": [
                {
                    "seq": 2,
                    "text": "점심은 뭘 먹을까?",
                    "score": 0.6
                }
            ],
            "summary": "부정적인 단어 사용 빈도가 높고, 외로움을 표현하는 문장이 발견되었습니다."
        }
    },
    "dialogue_result": [
        {
            "seq": 1,
            "doll_id": "doll-123",
            "text": "오늘 날씨가 좋네",
            "uttered_at": "2025-09-23T10:30:00",
            "label": "POSITIVE",
            "confidence_scores": {
                "positive": 0.9,
                "danger": 0.05,
                "critical": 0.03,
                "emergency": 0.02
            }
        },
        {
            "seq": 2,
            "doll_id": "doll-123",
            "text": "점심은 뭘 먹을까?",
            "uttered_at": "2025-09-23T12:00:15",
            "label": "DANGER",
            "confidence_scores": {
                "positive": 0.2,
                "danger": 0.6,
                "critical": 0.1,
                "emergency": 0.1
            }
        }
    ]
}
```

*   **Error Responses:**
    *   `400 Bad Request`: 파일이 비어있거나, CSV 형식이 잘못된 경우 발생합니다.
    *   `404 Not Found`: CSV에 포함된 인형 ID가 시스템에 등록되어 있지 않거나, 해당 인형에 시니어가 할당되지 않은 경우 발생합니다.
    *   `503 Service Unavailable`: 외부 분석 서버와의 통신 오류 등 분석 과정에서 서버 오류가 발생한 경우 반환됩니다.

---
#### **5.2. `GET /analyze` - 전체 분석 결과 목록 조회 (검색 및 페이징)**

지금까지 분석된 모든 대화의 종합 결과 목록을 검색 조건에 따라 조회합니다.

*   **Method:** `GET`
*   **URL:** `/analyze`
*   **Description:** 전체 분석 종합 결과 목록을 검색 조건에 따라 페이징하여 조회합니다.
*   **인증:** 필수

*   **Query Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `page` | `integer` | N | 페이지 번호 (0부터 시작) |
| `size` | `integer` | N | 페이지당 항목 수 |
| `senior_id`| `long` | N | 시니어 ID |
| `name` | `string` | N | 시니어 이름 (부분 일치) |
| `sex` | `string` | N | 성별. 허용 값: `"MALE"`, `"FEMALE"` |
| `gu` | `string` | N | 주소(구). `GET /api/administrative-districts`를 통해 얻은 `gu_code` 값을 사용합니다. |
| `dong` | `string` | N | 주소(동). `GET /api/administrative-districts`를 통해 얻은 `dong_code` 값을 사용합니다. |
| `age_group`| `integer` | N | 연령대 (예: 60, 70, 80, 100(100세 이상)) |
| `doll_id` | `string` | N | 인형 ID |
| `label` | `string` | N | 분석 결과 레이블. 허용 값: `"POSITIVE"`, `"DANGER"`, `"CRITICAL"`, `"EMERGENCY"` |
| `start_date`| `string` | N | 검색 시작일 (YYYY-MM-DD) |
| `end_date` | `string` | N | 검색 종료일 (YYYY-MM-DD) |

*   **Success Response (`200 OK`):**
    *   페이징된 분석 종합 결과 목록을 반환합니다.
```json
{
    "content": [
        {
            "overall_result_id": 1,
            "label": "DANGER",
            "summary": "부정적인 단어 사용 빈도가 높고, 외로움을 표현하는 문장이 발견되었습니다.",
            "timestamp": "2025-09-30T11:00:00",
            "doll_id": "doll-123",
            "senior_id": 1,
            "name": "김어르신",
            "age": 80,
            "sex": "FEMALE",
            "gu": "동구",
            "dong": "중앙동"
        }
    ],
    "page_number": 0,
    "page_size": 10,
    "total_elements": 1,
    "total_pages": 1,
    "is_last": true,
    "is_first": true
}
```
---
#### **5.3. `GET /analyze/{id}` - 특정 분석 상세 결과 조회**

특정 ID를 가진 분석의 상세 결과를 조회합니다. (종합 결과 + 개별 대화 목록)

*   **Method:** `GET`
*   **URL:** `/analyze/{id}`
*   **Description:** ID로 특정 분석의 상세 결과를 조회합니다.
*   **인증:** 필수

*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | `long`| Y | 조회할 분석 결과의 ID |

*   **Success Response (`200 OK`):**
    *   조회된 분석 상세 결과 정보를 반환합니다.
```json
{
    "senior_name": "김어르신",
    "diseases": "고혈압",
    "age": 80,
    "doll_id": "doll-123",
    "label": "DANGER",
    "confidence_scores": {
        "positive": 0.1,
        "danger": 0.7,
        "critical": 0.15,
        "emergency": 0.05
    },
    "reasons": [
        "점심은 뭘 먹을까?"
    ],
    "summary": "부정적인 단어 사용 빈도가 높고, 외로움을 표현하는 문장이 발견되었습니다.",
    "dialogues": [
        {
            "id": 1,
            "text": "오늘 날씨가 좋네",
            "uttered_at": "2025-09-23T10:30:00",
            "label": "POSITIVE",
            "confidence_scores": {
                "positive": 0.9,
                "danger": 0.05,
                "critical": 0.03,
                "emergency": 0.02
            }
        },
        {
            "id": 2,
            "text": "점심은 뭘 먹을까?",
            "uttered_at": "2025-09-23T12:00:15",
            "label": "DANGER",
            "confidence_scores": {
                "positive": 0.2,
                "danger": 0.6,
                "critical": 0.1,
                "emergency": 0.1
            }
        }
    ]
}
```

*   **Error Responses:**
    *   `404 Not Found`: 해당 ID의 분석 결과가 존재하지 않을 경우 발생합니다.

---
#### **5.4. `DELETE /analyze/{id}` - 분석 결과 삭제**

특정 ID를 가진 분석 결과를 삭제합니다.

*   **Method:** `DELETE`
*   **URL:** `/analyze/{id}`
*   **Description:** ID로 특정 분석 결과를 삭제합니다.
*   **인증:** 필수

*   **Path Parameters:**

| 파라미터 | 타입 | 필수 | 설명 |
| :--- | :--- | :--- | :--- |
| `id` | `long`| Y | 삭제할 분석 결과의 ID |

*   **Success Response (`204 No Content`):**
    *   응답 본문이 없습니다.

*   **Error Responses:**
    *   `404 Not Found`: 해당 ID의 분석 결과가 존재하지 않을 경우 발생합니다.

---
### **6. 대시보드 (Dashboard)**

#### **6.1. `GET /dashboard` - 대시보드 데이터 조회**

대시보드에 필요한 데이터를 조회합니다. (시니어 현황, 긴급 분석 결과)

*   **Method:** `GET`
*   **URL:** `/dashboard`
*   **Description:** 시니어 상태별 인원수와 최근 긴급/위험 분석 결과 상위 10개를 조회합니다.
*   **인증:** 필수

*   **Success Response (`200 OK`):**
    *   대시보드 데이터를 반환합니다.
```json
{
    "state_count": {
        "total": 15,
        "positive": 10,
        "danger": 3,
        "critical": 1,
        "emergency": 1
    },
    "recent_urgent_results": [
        {
            "overall_result_id": 5,
            "label": "EMERGENCY",
            "senior_name": "박긴급",
            "age": 85,
            "sex": "MALE",
            "gu": "서구",
            "dong": "둔산1동",
            "summary": "도움을 요청하는 다급한 목소리가 감지되었습니다.",
            "timestamp": "2025-09-30T10:30:00"
        },
        {
            "overall_result_id": 8,
            "label": "CRITICAL",
            "senior_name": "이위험",
            "age": 78,
            "sex": "FEMALE",
            "gu": "유성구",
            "dong": "온천1동",
            "summary": "가슴 통증을 호소하는 대화가 발견되었습니다.",
            "timestamp": "2025-09-30T09:00:00"
        }
    ]
}
```

---
### **7. 공통 (Common)**

#### **7.1. `GET /administrative-districts` - 전체 행정구역 목록 조회**

시니어 등록 및 검색에 사용 가능한 전체 '구'와 '행정동' 목록을 조회합니다.

*   **Method:** `GET`
*   **URL:** `/administrative-districts`
*   **Description:** 프론트엔드에서 지역 선택 드롭다운 메뉴를 동적으로 생성하는 데 사용될 수 있습니다.
*   **인증:** 불필요

*   **Success Response (`200 OK`):**
    *   '구'와 그에 속한 '행정동' 목록 배열을 반환합니다.
```json
[
    {
        "gu_code": "DAEDEOK_GU",
        "gu_name": "대덕구",
        "dong_list": [
            { "dong_code": "DAEHWA_DONG", "dong_name": "대화동" },
            { "dong_code": "DEOKAM_DONG", "dong_name": "덕암동" },
            { "dong_code": "MOKSANG_DONG", "dong_name": "목상동" },
            { "dong_code": "BEOB_1_DONG", "dong_name": "법1동" },
            { "dong_code": "BEOB_2_DONG", "dong_name": "법2동" },
            { "dong_code": "BIRAE_DONG", "dong_name": "비래동" },
            { "dong_code": "SEOKBONG_DONG", "dong_name": "석봉동" },
            { "dong_code": "SONGCHON_DONG", "dong_name": "송촌동" },
            { "dong_code": "SINTANJIN_DONG", "dong_name": "신탄진동" },
            { "dong_code": "OJEONG_DONG", "dong_name": "오정동" },
            { "dong_code": "JUNGNI_DONG", "dong_name": "중리동" },
            { "dong_code": "HOEDEOK_DONG", "dong_name": "회덕동" }
        ]
    },
    {
        "gu_code": "DONG_GU",
        "gu_name": "동구",
        "dong_list": [
            { "dong_code": "GAYANG_1_DONG", "dong_name": "가양1동" },
            { "dong_code": "GAYANG_2_DONG", "dong_name": "가양2동" },
            { "dong_code": "DAE_DONG", "dong_name": "대동" },
            { "dong_code": "DAECHEONG_DONG", "dong_name": "대청동" },
            { "dong_code": "PANAM_1_DONG", "dong_name": "판암1동" },
            { "dong_code": "PANAM_2_DONG", "dong_name": "판암2동" },
            { "dong_code": "SAMSUNG_DONG", "dong_name": "삼성동" },
            { "dong_code": "SANNAE_DONG", "dong_name": "산내동" },
            { "dong_code": "SEONGNAM_DONG", "dong_name": "성남동" },
            { "dong_code": "SININ_DONG", "dong_name": "신인동" },
            { "dong_code": "YONGUN_DONG", "dong_name": "용운동" },
            { "dong_code": "YONGJEON_DONG", "dong_name": "용전동" },
            { "dong_code": "JAYANG_DONG", "dong_name": "자양동" },
            { "dong_code": "JUNGANG_DONG", "dong_name": "중앙동" },
            { "dong_code": "HYO_DONG", "dong_name": "효 동" },
            { "dong_code": "HONGDO_DONG", "dong_name": "홍도동" }
        ]
    },
    {
        "gu_code": "SEO_GU",
        "gu_name": "서구",
        "dong_list": [
            { "dong_code": "GASUWON_DONG", "dong_name": "가수원동" },
            { "dong_code": "GAJANG_DONG", "dong_name": "가장동" },
            { "dong_code": "GALMA_1_DONG", "dong_name": "갈마1동" },
            { "dong_code": "GALMA_2_DONG", "dong_name": "갈마2동" },
            { "dong_code": "GWANJEO_1_DONG", "dong_name": "관저1동" },
            { "dong_code": "GWANJEO_2_DONG", "dong_name": "관저2동" },
            { "dong_code": "GOEJEONG_DONG", "dong_name": "괴정동" },
            { "dong_code": "GISEONG_DONG", "dong_name": "기성동" },
            { "dong_code": "NAE_DONG", "dong_name": "내동" },
            { "dong_code": "DOAN_DONG", "dong_name": "도안동" },
            { "dong_code": "DOMA_1_DONG", "dong_name": "도마1동" },
            { "dong_code": "DOMA_2_DONG", "dong_name": "도마2동" },
            { "dong_code": "DUNSAN_1_DONG", "dong_name": "둔산1동" },
            { "dong_code": "DUNSAN_2_DONG", "dong_name": "둔산2동" },
            { "dong_code": "DUNSAN_3_DONG", "dong_name": "둔산3동" },
            { "dong_code": "MANNYEON_DONG", "dong_name": "만년동" },
            { "dong_code": "BYEON_DONG", "dong_name": "변동" },
            { "dong_code": "BOKSU_DONG", "dong_name": "복수동" },
            { "dong_code": "YONGMUN_DONG", "dong_name": "용문동" },
            { "dong_code": "WOLPYEONG_1_DONG", "dong_name": "월평1동" },
            { "dong_code": "WOLPYEONG_2_DONG", "dong_name": "월평2동" },
            { "dong_code": "WOLPYEONG_3_DONG", "dong_name": "월평3동" },
            { "dong_code": "JEONGLIM_DONG", "dong_name": "정림동" },
            { "dong_code": "TANBANG_DONG", "dong_name": "탄방동" }
        ]
    },
    {
        "gu_code": "YUSEONG_GU",
        "gu_name": "유성구",
        "dong_list": [
            { "dong_code": "GWANPYEONG_DONG", "dong_name": "관평동" },
            { "dong_code": "GUJEUK_DONG", "dong_name": "구즉동" },
            { "dong_code": "NOEUN_1_DONG", "dong_name": "노은1동" },
            { "dong_code": "NOEUN_2_DONG", "dong_name": "노은2동" },
            { "dong_code": "NOEUN_3_DONG", "dong_name": "노은3동" },
            { "dong_code": "SANGDAE_DONG", "dong_name": "상대동" },
            { "dong_code": "SINSEONG_DONG", "dong_name": "신성동" },
            { "dong_code": "ONCHEON_1_DONG", "dong_name": "온천1동" },
            { "dong_code": "ONCHEON_2_DONG", "dong_name": "온천2동" },
            { "dong_code": "WONSINHEUNG_DONG", "dong_name": "원신흥동" },
            { "dong_code": "JEONMIN_DONG", "dong_name": "전민동" },
            { "dong_code": "JINJAM_DONG", "dong_name": "진잠동" },
            { "dong_code": "HAKHA_DONG", "dong_name": "학하동" }
        ]
    },
    {
        "gu_code": "JUNG_GU",
        "gu_name": "중구",
        "dong_list": [
            { "dong_code": "DAESA_DONG", "dong_name": "대사동" },
            { "dong_code": "DAEHEUNG_DONG", "dong_name": "대흥동" },
            { "dong_code": "MOK_DONG", "dong_name": "목동" },
            { "dong_code": "MUNCHANG_DONG", "dong_name": "문창동" },
            { "dong_code": "MUNHWA_1_DONG", "dong_name": "문화1동" },
            { "dong_code": "MUNHWA_2_DONG", "dong_name": "문화2동" },
            { "dong_code": "BUSA_DONG", "dong_name": "부사동" },
            { "dong_code": "SANSEONG_DONG", "dong_name": "산성동" },
            { "dong_code": "SEOKGYO_DONG", "dong_name": "석교동" },
            { "dong_code": "ORYU_DONG", "dong_name": "오류동" },
            { "dong_code": "YONGDU_DONG", "dong_name": "용두동" },
            { "dong_code": "YUCHEON_1_DONG", "dong_name": "유천1동" },
            { "dong_code": "YUCHEON_2_DONG", "dong_name": "유천2동" },
            { "dong_code": "EUNHAENGSEONHWA_DONG", "dong_name": "은행선화동" },
            { "dong_code": "JUNGCHON_DONG", "dong_name": "중촌동" },
            { "dong_code": "TAEPYEONG_1_DONG", "dong_name": "태평1동" },
            { "dong_code": "TAEPYEONG_2_DONG", "dong_name": "태평2동" }
        ]
    }
]
```
---
### **8. API 엔드포인트 요약**

| 기능 | 메서드 | 엔드포인트 | 인증 | 주요 요청 | 주요 응답 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **인증** | | | | | |
| 로그인 | `POST` | `/api/login` | 불필요 | Body: `username`, `password` | Header: `Authorization`, Cookie: `refresh_token` |
| 토큰 갱신 | `POST` | `/api/refresh` | 불필요 | Cookie: `refresh_token` | Header: `Authorization` |
| **회원** | | | | | |
| 회원 가입 | `POST` | `/api/members` | 불필요 | Body: `username`, `password` | 생성된 `Member` 객체 |
| 전체 회원 조회 | `GET` | `/api/members` | ADMIN | - | `Member` 객체 배열 |
| 특정 회원 조회 | `GET` | `/api/members/{username}` | ADMIN | Path: `username` | `Member` 객체 |
| 회원 정보 수정 | `PUT` | `/api/members/{username}` | ADMIN | Path: `username`, Body: `role`, `enabled` | 수정된 `Member` 객체 |
| 회원 삭제 | `DELETE` | `/api/members/{username}` | ADMIN | Path: `username` | `204 No Content` |
| **인형** | | | | | |
| 인형 생성 | `POST` | `/api/dolls` | ADMIN | Body: `id` | 생성된 `Doll` 객체 |
| 전체 인형 조회 | `GET` | `/api/dolls` | ADMIN | - | `Doll` 객체 배열 |
| 특정 인형 조회 | `GET` | `/api/dolls/{id}` | ADMIN | Path: `id` | `Doll` 객체 |
| 인형 삭제 | `DELETE` | `/api/dolls/{id}` | ADMIN | Path: `id` | `204 No Content` |
| **시니어** | | | | | |
| 시니어 등록 | `POST` | `/api/seniors` | ADMIN | Form-data: `senior`, `photo` | 생성된 `Senior` 객체 |
| 시니어 목록 조회 | `GET` | `/api/seniors` | ADMIN | Query: 검색 조건 | 페이징된 `Senior` 목록 |
| 특정 시니어 조회 | `GET` | `/api/seniors/{id}` | ADMIN | Path: `id` | `Senior` 상세 객체 |
| 시니어 정보 수정 | `PUT` | `/api/seniors/{id}` | ADMIN | Path: `id`, Form-data: `senior`, `photo` | 수정된 `Senior` 객체 |
| 시니어 삭제 | `DELETE` | `/api/seniors/{id}` | ADMIN | Path: `id` | `204 No Content` |
| **분석** | | | | | |
| 대화 파일 분석 | `POST` | `/api/analyze` | ADMIN | Form-data: `file` | `AnalysisResult` 객체 |
| 분석 결과 목록 조회 | `GET` | `/api/analyze` | ADMIN | Query: 검색 조건 | 페이징된 `OverallResult` 목록 |
| 특정 분석 결과 조회 | `GET` | `/api/analyze/{id}` | ADMIN | Path: `id` | `AnalysisDetail` 객체 |
| 분석 결과 삭제 | `DELETE` | `/api/analyze/{id}` | ADMIN | Path: `id` | `204 No Content` |
| **대시보드** | | | | | |
| 대시보드 데이터 조회 | `GET` | `/api/dashboard` | ADMIN | - | `Dashboard` 데이터 객체 |
| **공통** | | | | | |
| 행정구역 목록 조회 | `GET` | `/api/administrative-districts`| 불필요 | - | `Gu` 및 `Dong` 목록 |

---