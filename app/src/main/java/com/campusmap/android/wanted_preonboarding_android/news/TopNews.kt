package com.campusmap.android.wanted_preonboarding_android.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.RetrofitClient
import com.campusmap.android.wanted_preonboarding_android.databinding.TopnewsBinding
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TopNews : Fragment() {

    private lateinit var binding: TopnewsBinding
    private lateinit var topNewsRecyclerView: RecyclerView
    private lateinit var topNewsArrayList: MutableList<Article>


    private val topNewsAdapter by lazy {
        TopNewsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d("topNewsArray1", "topNewsArray1")

        topNewsArrayList = ArrayList()
        Log.d("TAG", "onCreate")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.topnews, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topNewsRecyclerView = view.findViewById(R.id.topNews_recycler_view)
        topNewsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        topNewsRecyclerView.adapter = topNewsAdapter

/*        binding.topNewsRecyclerView.apply {
            findViewById<View>(R.id.topNews_recycler_view)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = topNewsAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }*/



        retrofitWork()

        CoroutineScope(Dispatchers.IO).launch {

            // 요게 코루틴보다 늦게하면 되는데
            launch {
                delay(2000) // 이렇게하는거 딱 봐도 안좋아보이는데 방법을 모르겠네.
                Log.d("topNewsArray2", topNewsArrayList[0].toString())
            }


        }


    }

    override fun onResume() {
        super.onResume()

        topNewsAdapter.setOnItemClickListener(object : TopNewsAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    createFragment(TopNewsDetail.newInstance(topNewsArrayList[pos]))
                    Log.d("TopNewsInCoroutine", topNewsArrayList[pos].toString())
                }
            }

        })

    }

    companion object { // 근데 왜 이렇게 하는거지?
        fun newInstance() : TopNews {
            return TopNews()
        }
    }

    private fun createFragment(view: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.topNews_container, view)
            .commit()
    }

    private fun retrofitWork(){
        val service = RetrofitClient.topNewsService

        service.getTopNewsData(getString(R.string.api_key)).enqueue(object : Callback<TopNewsResponse> {

            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {


                if(response.isSuccessful) {
                    Log.d("TAG", response.body().toString())
                    val result = response.body()?.articles
                    topNewsAdapter.submitList(result)
                    for (element in result!!) {
                        topNewsArrayList.add(element!!)
                    }
                    Log.d("topNewsArray3", topNewsArrayList?.get(0).toString())
                }

            }
            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }

        })


    }


}