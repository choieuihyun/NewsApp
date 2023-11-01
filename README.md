# 프로젝트 설명

- 프로젝트 명 : news_apps

- News API를 사용해 데이터를 받아와서 화면에 띄우고, 저장하는 기능

- 저장한 데이터를 개별 화면에 띄우고 즐겨찾기 버튼에 따라 개별 화면에 추가,삭제 기능


# 프로젝트 전체 구조

- 기존의 어정쩡한 MVVM 구조 -> MVP 아키텍처로 리팩토링.
- 부연 설명 : 잘 이해하지 못한 상태로 만들었던 앱이기에 다시 만들어보고자 리팩토링 하였습니다.

<img src="https://github.com/choieuihyun/NewsApp/assets/59135621/38a9c8e0-945d-485b-b393-2c60a9ecca81.png" width="300" height="300"/>


## 프로젝트 세부 구조

#### data layer

```bash
app

├── data
│   │ 
│   ├── dataSource
│       │ 
│       ├── remote
│       │ 
│       └── local
│
│   │ 
│   ├── db
│   │ 
│   ├── mapper
│   │
│   └── repositoryImpl
│
├── domain
│   ├── train.py
│   ├── classify.py
│   ├── model.py
│   └── dataset.py
└── domain
    ├── train.py
    ├── classify.py
    ├── model.py
    └── dataset.py

* Data layer는 dataSource, db, mapper, repositoryImpl(repository 구현부)로 구성하였으며
  세부적으로는 Local, Remote로 나누었습니다. 

  * Local : RoomDB
  * Remote : https://newsapi.org의 API + Retrofit

* Domain layer는 model, repository, usecase로 구성하였습니다.

  * model : data layer의 entity를 뷰에 띄워줄 데이터로 변환해주는 model 데이터 클래스
```

# 개발 도구

- Android Studio
