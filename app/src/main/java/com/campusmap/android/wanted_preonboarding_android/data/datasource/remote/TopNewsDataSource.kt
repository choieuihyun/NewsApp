package com.campusmap.android.wanted_preonboarding_android.data.datasource.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.campusmap.android.wanted_preonboarding_android.BuildConfig
import com.campusmap.android.wanted_preonboarding_android.data.db.remote.api.RetrofitClient.topNewsApi
import com.campusmap.android.wanted_preonboarding_android.data.db.remote.api.TopNewsApi
import com.campusmap.android.wanted_preonboarding_android.data.db.remote.response.Article
import com.campusmap.android.wanted_preonboarding_android.data.db.remote.response.TopNewsResponse
import com.campusmap.android.wanted_preonboarding_android.ui.presenter.MainActivity
import retrofit2.Response

class TopNewsDataSource constructor(private val context: Context) : TopNewsApi {

    var topNewsMutableLiveData: MutableLiveData<List<Article?>> = MutableLiveData()
    var topNewsCategoryMutableLiveData: MutableLiveData<List<Article?>> = MutableLiveData()
    var topNewsData: List<Article?>? = listOf()
    var topNewsCategoryData: List<Article?>? = listOf()

    override suspend fun getTopNewsData(apiKey: String): Response<TopNewsResponse> {

        val response = topNewsApi.getTopNewsData(apiKey)

        if(response.isSuccessful) {
            val result = response.body()?.articles
            topNewsMutableLiveData.postValue(result)
            topNewsData = result
        }

        return response
    }

    override suspend fun getTopNewsCategoryData(
        apiKey: String,
        category: String
    ): Response<TopNewsResponse> {

        val activity: Context = context

        (activity as MainActivity).supportActionBar?.title =
            "Category - ${category.replaceFirstChar { it.uppercase() }}"


            val response = topNewsApi.getTopNewsCategoryData(BuildConfig.NEWS_API_KEY, category)

            if (response.isSuccessful) {
                val result = response.body()?.articles
                topNewsCategoryMutableLiveData.postValue(result)
                topNewsCategoryData = result
            }

        return response
/*        CoroutineScope(Dispatchers.IO).launch {

            launch(Dispatchers.IO) {

                if(response.isSuccessful) {
                    val result = response.body()?.articles
                    result.let {
                        topNewsCategoryMutableLiveData.postValue(it)
                        topNewsCategoryData = it
                    }
                }

            }*/

/*            val value2 = async(Dispatchers.IO) {
                if (response.isSuccessful) {
                    val result = response.body()?.articles
                    result.let {
                        topNewsCategoryMutableLiveData.postValue(it)
                        topNewsCategoryData = it
                    }
                } else {
                    Log.d("TAG", response.code().toString())
                }
            }
            value2.await()*/
/*        }
        value.await()*/
    }
}