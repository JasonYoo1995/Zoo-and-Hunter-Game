- **사용 스택** : Java, Java GUI Library(Swing, AWT)
- **개발 기간** : 2019년 4월 27일 ~ 6월 16일
- **시연 영상** : [https://youtu.be/_39glvhmSWs](https://youtu.be/_39glvhmSWs)
- **프로젝트 내용 요약**
    - 동물원에서 탈출한 동물을 사냥꾼이 숲 속에서 포획하여 다시 동물원에 가두는 게임입니다.
    - 키보드로 캐릭터를 움직이고, 캐릭터들 간에 Spatial Interaction(공격, 포획, 대화 등) 가능합니다.
    - 사냥꾼과 각 동물들은 HP(체력)을 가지고 있어, 사냥꾼의 HP가 0이 되면 사망하고, 동물의 HP가 0이 되면 포획 가능한 상태가 됩니다.
    - 마을에서 간호사에게 골드를 주고 HP를 회복 받거나, 상인에게 골드를 주고 HP 회복을 위한 아이템을 구매할 수 있습니다. 또한 포획한 동물을 판매하여 골드를 벌 수도 있습니다.
    - 멀티 스레딩 : 메인 스레드, 사냥꾼이 동물을 공격하는 스레드, 사냥꾼의 개가 동물을 공격하는 스레드, 동물이 사냥꾼을 공격하는 스레드까지 총 4개의 스레드가 병렬로 실행됩니다.
    - MVC 모델 기반으로 개발하였습니다.
- **제작 문서**
    
    [동물원 사냥 게임 문서](https://www.notion.so/9e246be2ed9248cea50a936fa70c7523)
    
- **어려웠던 점 / 느낀 점**
    - 가장 시행착오가 많았던 프로젝트들 중 하나였습니다. 이 프로젝트를 통해 객체 간 복잡한 의존 관계와 객체 간 통신을 구현할 때, 가독성이 명확하고 확장성 있는 설계가 필요함을 절실히 느꼈고, 추후 디자인 패턴을 반드시 공부하겠다는 마음을 먹게 된 계기가 되었습니다.

![image](https://user-images.githubusercontent.com/66045861/146749305-7fe4ed1d-a08d-4f33-828b-2307d8c61680.png)

![image](https://user-images.githubusercontent.com/66045861/146749339-e87c9481-07fc-46aa-9277-ef6aab4740c9.png)

![image](https://user-images.githubusercontent.com/66045861/146749390-daba3e10-d83c-4b08-bb3d-68c042f9e829.png)

![image](https://user-images.githubusercontent.com/66045861/146749477-05ade652-e4cf-41cf-b818-146fa5123b58.png)

![image](https://user-images.githubusercontent.com/66045861/146749511-e75cb39e-8fc8-4485-bcf3-8b1878ed8a65.png)
