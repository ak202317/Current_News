package com.codingbalance.currentnews

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var newsRv : RecyclerView
    private lateinit var madapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRv = findViewById(R.id.newsRv)

        newsRv.layoutManager = LinearLayoutManager(this)
        fetchData()
        madapter = NewsListAdapter(this)
        newsRv.adapter = madapter

    }

    private fun fetchData() {
      //  val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=975631efc5244d3eb0d484ae898f2982"
        val url = " https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                madapter.updateNews(newsArray)
            },
            {
                Toast.makeText(this, "something went wrong $it", Toast.LENGTH_LONG).show()
                Log.d("Auth Error::", "fetchData: $it")

            }
        )
        {
            /*override fun getHeaders(): Map<String, String> {

                val params: MutableMap<String, String> = HashMap()

                params["User-Agent"] = "Mozilla/5.0"

                return params

            }*/
        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun itemClicked(item: News) {
     //   Toast.makeText(this, "clicked item is $item", Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}