package mx.chatgoya.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_twitter_result.*

class MainActivity : AppCompatActivity() {

    @BindView(R.id.editText) lateinit var hashtagEditText: EditText

    @OnClick(R.id.button) fun searchForHashTag(){
        var hashtag = when(hashtagEditText.text.toString()){
            "" -> "Ingenia"
            else -> hashtagEditText.text.toString()
        }

        val i = Intent(this@MainActivity, TwitterResultActivity::class.java)
        i.putExtra("hashtag", hashtag)
        startActivityForResult(i, 10101)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        Realm.init(this@MainActivity)
        val realm = Realm.getDefaultInstance()
        //realm.beginTransaction()
        val favouritesSaved = realm.where(FavouriteTweetModel::class.java).findAll()
        val twittersList : MutableList<TwitterObject> = mutableListOf()
        for (favourite in favouritesSaved){
            val twitter = TwitterObject()
            twitter.twitterDate = favourite.twitterDate
            twitter.twitterId = favourite.twitterId
            twitter.twitterText = favourite.twitterText
            twitter.userId = favourite.userId
            twitter.userPhotoUrl = favourite.userPhotoUrl
            twitter.userName = favourite.userName
            twittersList.add(twitter)
        }

        val twitterAdapter = TwitterAdapter(twittersList, this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = twitterAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 10101){

        }
    }
}
