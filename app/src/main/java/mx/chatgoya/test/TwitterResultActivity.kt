package mx.chatgoya.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_twitter_result.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class TwitterResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_result)

        val hashtag:String = intent.extras.getString("hashtag")
        Log.d("hashtag", hashtag)

        val url = "https://serene-woodland-62357.herokuapp.com/twitter/$hashtag"


        val request = object : JsonArrayRequest(Request.Method.GET, url, null,
                Response.Listener<JSONArray>  { response ->
                    val twittersList : MutableList<TwitterObject> = mutableListOf()
                    for (i in 0 until response.length()){
                        val twitter = TwitterObject()
                        twitter.twitterDate = response.getJSONObject(i).getString("date")
                        twitter.twitterId = response.getJSONObject(i).getInt("id")
                        twitter.twitterText = response.getJSONObject(i).getString("text")
                        twitter.userId = response.getJSONObject(i).getJSONObject("user").getInt("id")
                        twitter.userPhotoUrl = response.getJSONObject(i).getJSONObject("user").getString("profile_image_url")
                        twitter.userName = response.getJSONObject(i).getJSONObject("user").getString("name")
                        twittersList.add(twitter)
                    }

                    val twitterAdapter = TwitterAdapter(twittersList, this@TwitterResultActivity)
                    recyclerView.addItemDecoration(DividerItemDecoration(this@TwitterResultActivity, DividerItemDecoration.VERTICAL))
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = twitterAdapter

                    val swipeLeftHandler = object : SwipeToDelete(this) {
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val adapter = recyclerView.adapter as TwitterAdapter
                            adapter.removeAt(viewHolder.adapterPosition)
                        }
                    }

                    val swipeRightHandler = object : SwipeToSave(this) {
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val adapter = recyclerView.adapter as TwitterAdapter
                            //adapter.removeAt(viewHolder.adapterPosition)
                            //toast("rigth")
                            adapter.saveAt(viewHolder.adapterPosition)
                        }
                    }

                    val itemTouchHelper = ItemTouchHelper(swipeLeftHandler)
                    itemTouchHelper.attachToRecyclerView(recyclerView)

                    val itemTouchHelper2 = ItemTouchHelper(swipeRightHandler)
                    itemTouchHelper2.attachToRecyclerView(recyclerView)





                }, Response.ErrorListener {

        }){
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("Accept", "application/json")
                params.put("Content-Type", "application/json")
                return params
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }


}
