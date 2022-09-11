package com.campusmap.android.wanted_preonboarding_android.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.campusmap.android.wanted_preonboarding_android.news.Article
import com.campusmap.android.wanted_preonboarding_android.repository.TopNewsRepository
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import kotlinx.coroutines.*

// 만약 ViewModel 의 Context 를 사용하면
// 액티비티가 destroy 되는 경우 메모리 에러가 발생할 수 있어서
// Application Context 를 사용하기 위해 application 을 인자로 받습니다. 인데 난 SAA라 안해도 되나?
class TopNewsViewModel : ViewModel() {

    // ViewModel은 DB에 직접적으로 접근하면 안됨.
    private var topNewsRepository: TopNewsRepository = TopNewsRepository.get()
    private var topNewsResponseAllLiveData: LiveData<List<Article?>> = topNewsRepository.getTopNewsAllResponseLiveData()
    private var topNewsCategoryResponseAllLiveData: LiveData<List<Article?>> = topNewsRepository.getTopNewsCategoryAllResponseLiveData()

    // private var topNewsResponseAllData: List<Article?>? = topNewsRepository.getTopNewsAllResponseData()

/*    private var savedResponseAllData: List<Saved> = topNewsRepository.getAllSavedNewsData() // 그냥 Saved 데이터 모두 받아옴.
    private var savedResponseData: Saved = topNewsRepository.getSavedNewsData() // Saved 데이터 하나씩 받아옴.*/


    var topNewsPosition: Int = 0

    // Retrofit Data

    fun getTopNewsItemData() {
        topNewsRepository.getTopNewsItemData // topNews retrofit 연결하면서 topNewsData 반환.
    }

    fun getTopNewsAllResponseLiveData(): LiveData<List<Article?>>{ // TopNews 전체 Data
        return topNewsResponseAllLiveData
    }


    suspend fun getTopNewsAllResponseData(): List<Article?>? {
        return topNewsRepository.getTopNewsAllResponseData()
    }

    fun setTopNewsItemPosition(position : Int) {
        topNewsPosition = position
    }

    // Saved Data

/*    fun getAllSaved() {
        topNewsRepository.getAllSaved()
    }*/

    // 아 위에처럼 할게 아니라 Observe를 해야하는데 Observe하려면
    // LiveData 속성으로 넘어가야지 멍청아 메서드는 관찰이 안돼
    val savedNewsLiveData = topNewsRepository.getAllSaved()

    fun addSaved(saved: Saved) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsRepository.addSaved(saved)
        }
    }

//    fun getSavedId(id: Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            topNewsRepository.getSavedIdData(id)
//        }
//    }

    fun getTitle(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsRepository.getTitle(title)
        }
    }

    fun deleteSaved(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsRepository.deleteSaved(title)
        }
    }

    fun deleteAllSaved() {
        CoroutineScope(Dispatchers.IO).launch {
            topNewsRepository.deleteAllSaved()
        }
    }

/*    fun getTopNewsDetailResponseLiveData(position: Int): LiveData<List<Article?>> {
        topNewsResponseAllLiveData.value = position
    }*/

/*    fun getTopNewsDetailButtonLiveData(value: Boolean) : Boolean {
        return topNewsDetailButtonLiveData = value
    }*/


}