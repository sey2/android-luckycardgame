# android-luckycardgame

## Step 1-1 (Layout 틀 구성) [07.03 15:30]

- 단순 선형 구조를 요구하는 레이아웃이기 때문에 Root Layout을 Linear Layout으로 배치
- 아이템 요소 레이아웃을 LinearLayout으로 구성 후 shape 태그로 라운딩 처리

### 실행 화면
<img width="499" src="https://github.com/sey2/S_Diary/assets/54762273/07343cfd-f117-4459-9569-6a8ecdd46df8">  

## Step 1-2 게임 클래스 구현
-   코드의 가독성과 유지 보수성을 더해주기 위해 sealed class 사용
-   코드의 결합도를 낮추고 단위 테스트 가능한 코드 작성을 위해 Repository 패턴 이용
-   Card 속성 출력 함수를 Card 클래스의 확장 함수로 작성하여 캡슐화와 확장성을 더함
-  신뢰성 확보 및 유지보수 용이성을 위해 테스트 코드 작성

## (Step 1-3) 카드 나눠주기 기능 구현

- 사용자 카드 화면은 Recycler View로 구현
- 남은 카드는 GridLayout을 이용해 구현
- 랜덤으로 카드 나눠주는 기능은 list의 suffled() 메서드를 이용
- Repository 패턴을 이용하여 데이터 액세스 로직 중앙화


### 결과화면
<img width="436" alt="스크린샷 2023-07-06 오후 5 51 16" src="https://github.com/softeerbootcamp-2nd/android-luckycardgame/assets/54762273/72d0d5c1-ea76-469d-b2c0-5fa99c431b2c">


## (Step 1-4) 카드게임 로직 구현

- 참가자 클래스 구현
- Unit Test 용이성을 위해 Service Class에 비지니스 로직 중앙화
- 게임 참가자가 3명, 4명, 5명일때 카드를 올바르게 나누어 지는지 테스트 코드 작성
- 카드를 생성하고 1번부터 12번까지 3장씩 올바르게 생성되었는지 검증하는 테스트 코드 작성
- 게임 참가자 수가 3명일 때(테스트 클래스의 Default 참가자수) 참가자 카드 리스트가 오름차순 정렬이 잘 되는지 확인하는 테스트 코드 작성
- 참가자 카드 리스트가 오름차순 정렬이 잘 되는지 확인하는 테스트 코드 작성
- 게임 참가자 수가 3명일 때(테스트 클래스의 Default 참가자수) 참가자 카드 리스트가 내림차순 정렬이 잘 되는지 확인 테스트 코드 작성
- 게임 참가자 수가 3명일 때(테스트 클래스의 Default 참가자수) 바닥 카드 리스트가 오름차순 정렬이 잘 되는지 확인하는 테스트 코드 작성
- 게임 참가자 카드 중에서 세 장이 동일한 숫자인 카드가 존재하는지 확인하는 테스트 코드 작성
- 특정 참가자와 해당 참가자 카드 중에 가장 낮은 숫자 또는 가장 높은 숫자, 바닥 카드 중 아무거나를 선택해서 3개가 같은지 판단할 수 있는지 확인하는 테스트 코드 작성

**결과-**

<table>
  <tr>
    <td><img width="200" src="https://github.com/sey2/CodingTest/assets/54762273/4dd481ab-6ca2-40c4-a43a-94b8249d6891"></td>
    <td><img width="200" src="https://github.com/sey2/CodingTest/assets/54762273/9ce40896-a374-420a-8b81-b1f3fa704b1a"></td>
  </tr>
  <tr>
    <td align="center"><b>결과 화면 1</b></td>
    <td align="center"><b>결과 화면 2</b></td>
  </tr>
</table>