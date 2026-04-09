<a href="https://apps.apple.com/app/dialog/id6760887655">
  <img width="250" alt="app_store" src="https://github.com/user-attachments/assets/0c95b6b3-678a-4e0e-8e6f-a503e62ae1ea" />
</a>
&nbsp;
<a href="https://play.google.com/store/apps/details?id=com.on.dialog">
  <img width="250" alt="google_play" src="https://github.com/user-attachments/assets/faffb4d4-bab3-45a1-af55-7a9ec6a52b06" />
</a>

# 👀 Dialog - 생각을 나누는 토론 플랫폼

> Dialog는 누구나 자유롭게 의견을 나누고 다양한 주제에 대해 토론할 수 있는 커뮤니티 플랫폼입니다.<br/>
> 관심 있는 주제를 직접 토론으로 만들거나, 이미 진행 중인 토론에 참여해 새로운 관점을 발견해보세요.

<img width="1512" height="726" src="https://github.com/user-attachments/assets/09ec70d8-9dd5-48b6-a4c6-b43fa13f26d2" />

<br/>

<br>

## 🛠 기술 스택

| 용도       | 기술 스택                               |
|----------|-------------------------------------|
| 언어       | Kotlin, Swift                       |
| UI       | Compose Multiplatform, Material3    |
| 비동기      | Kotlinx Coroutines                  |
| 의존성 주입   | Koin                                |
| 네비게이션    | Jetbrains Nav3 (multiplatform-nav3) |
| 네트워크     | Ktor, Ktorfit                       |
| 직렬화      | kotlinx.serialization               |
| 로컬 저장소   | DataStore                           |
| 이미지 로딩   | Coil                                |
| 크래시 모니터링 | Sentry KMP                          |
| 마크다운 렌더링 | Multiplatform Markdown Renderer     |

<br>

## 🏗 아키텍처

`Clean Architecture`, `MVI`, `Multi-Module` 구조를 채택하며, 공통 코드는 `commonMain`에 위치합니다.

```
Presentation (feature)   ← MVI: Intent → State → UI
      ↓
Domain (core/domain)     ← 비즈니스 로직, Repository 인터페이스
      ↓
Data (core/data)         ← Repository 구현체, Remote/Local DataSource
      ↓
Network / Local (core/network, core/local)
```

각 feature 모듈은 **api** / **impl** 로 분리되어 의존 방향을 단방향으로 유지합니다.

<br>

## 📁 모듈 구조

```
📦 Dialog
├── 📂 composeApp            # 앱 진입점 (Android · iOS 공통)
├── 📂 iosApp                # iOS 네이티브 진입점 (Xcode)
├── 📂 core
│   ├── 📂 common            # 공통 유틸, 확장 함수
│   ├── 📂 data              # Repository 구현체
│   ├── 📂 domain            # UseCase, Repository 인터페이스, 도메인 모델
│   ├── 📂 model             # 공유 데이터 모델
│   ├── 📂 network           # Ktor 클라이언트, API 서비스
│   ├── 📂 local             # Room DB, DataStore
│   ├── 📂 designsystem      # 공통 디자인 컴포넌트, 테마
│   ├── 📂 navigation        # 네비게이션 정의
│   └── 📂 ui                # 공통 UI 유틸
└── 📂 feature
    ├── 📂 login             # 로그인
    ├── 📂 signup            # 회원가입
    ├── 📂 main              # 메인 화면 (바텀 내비게이션)
    ├── 📂 discussionlist    # 토론 목록
    ├── 📂 discussiondetail  # 토론 상세
    ├── 📂 creatediscussion  # 토론 생성
    ├── 📂 scrap             # 스크랩
    ├── 📂 mypage            # 마이페이지
    └── 📂 mycreated         # 내가 만든 토론
```

<br>

## 👥 팀원 소개

<table>
  <tr>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/u/105299421?v=4" width="100" style="border-radius:50%"><br/>
      <b>다이스</b><br/>
      <a href="https://github.com/moondev03">@moondev03</a>
    </td>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/u/183526990?v=4" width="100" style="border-radius:50%"><br/>
      <b>제리</b><br/>
      <a href="https://github.com/jerry8282">@jerry8282</a>
    </td>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/u/139840247?v=4" width="100" style="border-radius:50%"><br/>
      <b>크림</b><br/>
      <a href="https://github.com/ijh1298">@ijh1298</a>
    </td>
  </tr>
</table>
