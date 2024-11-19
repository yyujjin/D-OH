# <img width="200" alt="image" src="https://github.com/user-attachments/assets/cb614de8-b69b-4e70-b68f-a9be8f2709c6">

로고 디자인 의뢰자가 컨테스트를 개최하거나 디자이너에게 직접 문의할 수 있으며, 누구나 디자이너로 참여해 상금을 받을 수 있는 로고 네이밍 서비스입니다.
이 프로젝트는 6명이 협업하여 진행했으며, 저는 **사용자 간 실시간 소통을 위한 채팅 기능을 담당하여 구현**하였습니다.

## 🛠️ 기술 스택

  - Spring Boot
  - MyBatis
  - MySQL
  - JavaScript
  - Thymeleaf
 

## 🚀 프로젝트 전체 기능 소개
<img width="1000" alt="스크린샷 2024-11-19 오후 9 42 55" src="https://github.com/user-attachments/assets/105d23f8-a194-4d39-b3d4-d00adbd31cf1">
<img width="1000" alt="스크린샷 2024-11-19 오후 9 43 02" src="https://github.com/user-attachments/assets/1d504dd1-2d30-4079-910b-e2309c6e8e5a">
<img width="1000" alt="스크린샷 2024-11-19 오후 9 43 11" src="https://github.com/user-attachments/assets/540de52b-d364-4158-aeae-f1804732c9e3">
<img width="1000" alt="스크린샷 2024-11-19 오후 9 43 41" src="https://github.com/user-attachments/assets/8f786f52-d8f5-448a-9503-8b5a41e0344f">
<img width="1000" alt="스크린샷 2024-11-19 오후 9 43 56" src="https://github.com/user-attachments/assets/eb471dd2-844f-42ae-a472-7692204aba8d">
<img width="1000" alt="스크린샷 2024-11-19 오후 9 44 03" src="https://github.com/user-attachments/assets/77480d2b-4815-42b2-8f21-12a26fe16d08">
<img width="1000" alt="스크린샷 2024-11-19 오후 9 44 26" src="https://github.com/user-attachments/assets/54cb12e9-e057-4a22-b33a-db53d465b0ec">



## 💬 채팅 기능 소개
### 🙋‍♀️디자이너에게 문의하기

![KakaoTalk_Photo_2024-11-07-16-35-00 007](https://github.com/user-attachments/assets/d4a8af3a-3f2a-4fbd-98f7-b95d5c2b3cfe)


- 사용자는 **디자이너에게 문의하기** 버튼을 클릭하여 실시간 채팅방에 참여할 수 있습니다.
- 이 기능을 통해 사용자는 디자이너와 직접 소통하고, 원하는 디자인이나 수정 사항에 대해 실시간으로 문의할 수 있습니다.
- 버튼을 클릭하면 자동으로 채팅방에 입장하게 되어 간편하게 소통할 수 있습니다.


### 🔔 메시지 알림 기능

![KakaoTalk_Photo_2024-11-07-16-34-58 001](https://github.com/user-attachments/assets/f21cd4ba-8e5a-41b7-af92-442166edfa0b)

- 새로운 메시지가 도착하면, 사이드바의 **메시지 아이콘**에 알림이 표시됩니다.
- 사용자는 알림을 통해 즉시 새로운 메시지가 도착했음을 확인할 수 있어 중요한 소통을 놓치지 않도록 돕습니다.

### 💬 실시간 채팅 기능

![KakaoTalk_Photo_2024-11-07-16-34-58 002](https://github.com/user-attachments/assets/3db9c5c5-b85d-4af4-b513-823dd0a7b527)

- 채팅방에서는 양방향 대화가 가능하여, 사용자는 디자이너와 동시에 메시지를 주고받을 수 있습니다.


### 👥 채팅방 참여자 정보 표시 기능

![KakaoTalk_Photo_2024-11-07-16-34-59 003](https://github.com/user-attachments/assets/f21337e5-1ef9-49dd-9c49-3e2bc3641b5c)

- 기존에 생성된 채팅방이 있는 경우, 사이드바의 **메시지 아이콘**에 현재 채팅방에 참여하고 있는 사람들의 정보가 표시됩니다.
- 사용자는 사이드바에서 채팅방 참여자 목록을 바로 확인할 수 있어, 현재 대화에 누가 참여 중인지 쉽게 파악할 수 있습니다.



### 🔚 채팅 종료 기능

![KakaoTalk_Photo_2024-11-07-16-34-59 005](https://github.com/user-attachments/assets/e5b66706-10c1-4794-91f1-e8d1cddcf261)


- 사용자가 **채팅 종료** 버튼을 누르면, 현재 채팅방에서 나가게 되며, 화면에서는 모든 메시지가 삭제된 것처럼 보입니다.
- 하지만, 메시지 데이터는 **완전히 삭제되지 않고** 데이터베이스에 보관되어, 필요시 복원할 수 있습니다.
- 데이터가 백그라운드에서 안전하게 보존되므로, 추후 복구가 필요한 경우 데이터 무결성을 유지할 수 있습니다.
