package com.example.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.json.JSONException

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        //Volley의 RequestQueue 선언
        var requestQueue: RequestQueue=Volley.newRequestQueue(this)

        //GSON 객체 선언
        var gson:Gson= Gson()

        //API 주소 선언
        val url="https://api.themoviedb.org/3/movie/now_playing?"+"api_key=91620cb8a14f325d3fdd33aea39738d2"+"&language=ko-KR"+"&region=KR"

        //API를 호출함
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {//데이터가 정상적으로 호출됐을 때 처리하는 부분
            response -> try {
                //response(영화 JSON 데이터) -> MovieList Data Class로 변환 (by GSON)
                val data = gson.fromJson<MovieList>(response.toString(), MovieList::class.java)

                val adapter=MovieAdapter(this, data.results)
                movieRecycler.adapter=adapter

                val lm=LinearLayoutManager(this)
                movieRecycler.layoutManager=lm
            } catch (e:JSONException){
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener {//데이터가 정상적으로 호출되지 않았을 때 처리하는 부분
            error -> Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
        })

        requestQueue.add(request)

    }
}