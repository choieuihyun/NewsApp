package com.campusmap.android.wanted_preonboarding_android.repository

import android.content.Context

/*
     앱이 단일 프로세스에서 실행되면 AppDatabase 객체를 인스턴스화할 때 싱글톤 디자인 패턴을 따라야 합니다.
     각 RoomDatabase 인스턴스는 리소스를 상당히 많이 소비하며 단일 프로세스 내에서 여러 인스턴스에 액세스해야 하는 경우는 거의 없습니다.
     출처 -공식문서-
*/

class SavedRepository private constructor(context : Context) {


    companion object {

        private var INSTANCE: SavedRepository? = null

    }

}