# 프로젝트 설명

- 프로젝트 명 : wanted_preonboarding_android

- News API를 사용해 데이터를 받아와서 화면에 띄우고, 저장하는 기능

- 저장한 데이터를 개별 화면에 띄우고 즐겨찾기 버튼에 따라 개별 화면에 추가,삭제 기능


# 프로젝트 전체 구조

- MVVM + LiveData + ViewModel + RoomDB을 사용한 MVVM 구조.

- NewsAPI를 Retrofit으로 받아와 사용하였으며 받아온 데이터를 LiveData를 사용하여 Observing하는 방식.

- BottomNavigation과 SAA를 함께 사용한 구조.

## 프로젝트 세부 구조

- adapter package : recyclerView를 띄워줄 List에 데이터를 넣어줄 Adatper들의 패키지.

- category package 
 1. Categories : BottomNavigation의 Categories Fragment
 2. CategoriesItem : Categories의 버튼 눌렀을 때 띄워줄 리스트가 담긴 Fragment
 3. CategoriyItemDetail : 각 Category의 Item을 눌렀을 때 띄워줄 Fragment

- news package
 1. TopNews : BottomNavigation의 TopNews를 눌렀을 때 List를 띄워주는 Fragment
 2. TopNewsDetail : TopNews의 ListItem을 눌렀을 때 나오는 Fragment
 
 - repository package
 1. TopNewsApplication : Application()을 상속받아 TopNewsRepository가 앱 시작시 생성될 수 있도록 함
 2. TopNewsRepository : Model에 접근하는 Repository로, retrofit과 Room DB를 생성하며 데이터를 받아와 가공하는 메서드가 포함된 Repository

 - retrofit package
 1. TopNewsResponse : NewsAPI를 받아 사용할 수 있는 형태로 변환하는 구조가 있는 data class
 2. TopNewsService : NewsAPI를 받아오는 방식과 Query가 있으며 각각 받아오는 구조마다 반환형태가 있는 interface

 - roomdb package
 1. Saved : RoomDB table의 구조
 2. SavedDao : RoomDB table의 데이터 INSERT,DELETE,SELECT interface
 3. SavedDatabase : 클래스가 앱의 DB를 나타낸다고 Room에게 알려주며 엔터티와 DB버전을 지정

- viewmodel package
 1. CategoryItemsViewModel : Category의 Data를 가공하며 TopNewsRrepository의 해당 LiveData를 Observe
 2. TopNewsViewModel : TopNews Data를 가공하며 TopNewsRepository의 해당 LiveData를 Observe
 3. SavedViewModel : Saved Data를 가공하며 TopNewsRrepository의 해당 LiveData를 Observe

- TopNewsFragment
 1. 앱의 모든 Fragment들이 부착되는 BottomNavigation의 mainFragment


# 개발 도구

- Android Studio
