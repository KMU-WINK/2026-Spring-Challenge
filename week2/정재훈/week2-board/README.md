# 2주차 게시글 API

Java 17 / Spring Boot 3.5.16 / Gradle Groovy

## 실행

이 폴더에서 PowerShell로 실행합니다.

```powershell
.\gradlew.bat bootRun
```

기본 주소: `http://localhost:8080`

## 필수 API

| 기능 | 메서드 | 경로 | 성공 상태 |
| --- | --- | --- | --- |
| 작성 | POST | /api/posts | 201 |
| 전체 조회 | GET | /api/posts | 200 |
| 상세 조회 | GET | /api/posts/{id} | 200 |
| 수정 | PATCH | /api/posts/{id} | 200 |
| 삭제 | DELETE | /api/posts/{id} | 204 (응답 본문 없음) |
| 좋아요 | POST | /api/posts/{id}/likes | 200 |

작성 요청 (Postman: Body → raw → JSON):

```json
{"title":"첫 게시글","content":"내용","writer":"정재훈"}
```

수정 요청:

```json
{"title":"수정된 게시글","content":"수정된 내용"}
```

생성 응답의 id를 상세 조회·수정·삭제·좋아요 경로에 사용합니다.
요청은 PostCreateRequest/PostUpdateRequest, 응답은 PostResponse를 사용합니다.
메모리 저장소이므로 서버 재시작 시 데이터가 사라집니다.
심화 과제의 전역 예외 처리와 @Valid는 포함하지 않았습니다.
PATCH는 실습 자료와 같이 제목과 내용을 함께 받습니다.

## 검증

```powershell
.\gradlew.bat test
```

PostApiTests에서 작성 → 목록/상세 조회 → 수정 → 좋아요 → 삭제의 상태 코드와 데이터를 검증합니다.
