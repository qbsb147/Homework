# Homework 🚀

이 저장소는 체스 애플리케이션과 관련된 Java 코드(데이터베이스 연동, 컨트롤러, 모델 포함)를 포함하고 있습니다.

## 🛡️ Badges

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)

## ✨ Features

*   **체스 게임 로직:** 체스 게임의 핵심 기능 구현
*   **플레이어 관리:** 플레이어 데이터 및 연산 처리
*   **데이터베이스 연동:** JDBC를 사용한 데이터 영속화 (파일 ojdbc17.xml로 미루어 Oracle 사용 추정)
*   **MVC 아키텍처:** 구조화된 개발을 위한 Model-View-Controller 패턴 적용
*   **설정:** 데이터베이스 연결 및 애플리케이션 설정 포함
*   **이미지 자산:** 체스판 및 기타 게임 요소에 대한 이미지 리소스를 포함합니다.

## 🛠️ 설치

프로젝트를 시작하려면 아래 단계를 따르세요:

1.  **저장소 클론:**
    ```bash
    git clone https://github.com/qbsb147/Homework.git
    cd Homework
    ```

2.  **Java 개발 키트(JDK) 설정:**
    JDK 17 이상이 설치되어 있어야 합니다. Oracle Java SE 다운로드 페이지에서 다운로드할 수 있습니다.

3.  **IDE 설정 (해당되는 경우):**
    프로젝트는 IntelliJ IDEA 프로젝트 구조를 가지고 있습니다. IntelliJ IDEA에서 프로젝트를 열면 바로 작업할 수 있습니다. Oracle JDBC 드라이버(ojdbc17.xml)를 프로젝트에 추가하여 라이브러리에 등록하면 데이터베이스 연결이 원활합니다.

4.  **데이터베이스 설정:**
    JDBC를 통해 Oracle 데이터베이스와 연결됩니다. 데이터베이스 접속 정보는 driver.properties 및 관련 설정 파일에서 확인하고 수정할 수 있습니다. SQL 쿼리는 resources/query.xml에서 관리됩니다.

## 💡 사용법

애플리케이션은 서버를 먼저 실행하고, 그 다음 클라이언트를 실행하는 구조입니다.

### 서버 실행

서버를 먼저 실행하여 데이터베이스 및 게임 로직과 연결합니다:

### 클라이언트 실행

서버가 실행된 상태에서 클라이언트를 실행하면 사용자 인터페이스가 시작됩니다::

### 서버 실행

서버를 먼저 실행해야 클라이언트가 데이터베이스 및 게임 로직과 연결됩니다:

```bash
# Java 17 기준, 클래스 경로를 지정하고 Run 클래스의 main 실행
java -cp path/to/classes Chess.run.Run
```
path/to/classes는 컴파일된 .class 파일이 있는 디렉터리로 바꿔주세요.

### 클라이언트 실행

서버가 실행된 상태에서 클라이언트를 실행하면 사용자 인터페이스가 시작됩니다:

```bash
# Java 17 기준, 클래스 경로를 지정하고 ChessClient 클래스의 main 실행
java -cp path/to/classes Chess.config.connection.ChessClient
```
path/to/classes는 컴파일된 .class 파일이 있는 디렉터리로 바꿔주세요.


이렇게 하면 사용자 입장에서 **무엇을 먼저 실행해야 하는지**, **어떤 클래스의 main을 실행해야 하는지** 명확히 알 수 있습니다.

원하면 제가 README 전체 사용법 섹션을 이 기준으로 다시 작성해서 보여드릴 수도
### 예시: 플레이어 등록

```java
// OfflineChessMenu를 통해 회원가입
OfflineChessMenu offlineMenu = new OfflineChessMenu(out, in, sc);

// 새 플레이어 등록
offlineMenu.playerJoin();

// 입력 예시:
// 아이디: examplePlayer
// 비밀번호: 1234
// 이름: 예찬
// 나이: 29
// 성별: M
// 이메일: example@example.com
// 휴대전화: 010-1234-5678
```

### 예시: 오프라인 체스 게임 플레이

```java
// OfflineChessMenu를 통해 게임 시작
OfflineChessMenu offlineMenu = new OfflineChessMenu(out, in, sc);

// 게임 플레이
offlineMenu.soloPlay();

// ChessBoard를 통해 실제 체스 보드가 표시되고
// 플레이어가 직접 체스를 진행할 수 있음
```

## 🤝 기여

프로젝트 기여를 환영합니다. 참여 방법은 다음과 같습니다:

1.  **저장소 포크**
2.  **새 브랜치 생성** (기능 추가 또는 버그 수정용)
3.  **코드 변경 및 문서화**
4.  **테스트 작성** (새 기능/버그 수정용)
5.  **Pull Request 제출** 및 변경 사항 설명

코드는 프로젝트 스타일과 규칙을 준수해야 합니다.

## 📜 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다. 개별 파일에 포함된 라이선스 정보도 확인하세요.