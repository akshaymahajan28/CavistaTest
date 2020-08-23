package com.mvpgrid.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvpgrid.R
import com.mvpgrid.common.Constants
import com.mvpgrid.common.Utils
import com.mvpgrid.roomDB.Word
import com.mvpgrid.roomDB.WordViewModel
import com.mvpgrid.view.adapter.WordListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_comments.*

class ImageCommentsActivity : AppCompatActivity(), View.OnClickListener {

    private var id: String? = ""
    private var image: String? = ""
    private var name: String? = ""

    private lateinit var wordViewModel: WordViewModel

    companion object {
        fun startActivity(activity: Activity, id: String?, image: String?, name: String?) {
            val intent = Intent(activity, ImageCommentsActivity::class.java)
            intent.putExtra(Constants.KeyConstant.ID, id)
            intent.putExtra(Constants.KeyConstant.IMAGE, image)
            intent.putExtra(Constants.KeyConstant.TITLE, name)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_comments)
        init()
        setViewData()
        setClick()
        loadListData()
    }

    private fun loadListData() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it, id) }
        })
    }

    private fun setClick() {
        btnSend!!.setOnClickListener(this)
    }

    private fun setViewData() {
        if (image != null && !TextUtils.isEmpty(image)) {
            try {
                Picasso.with(this).load(image).into(ivGrid)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            ivGrid!!.visibility = View.VISIBLE
        } else {
            ivGrid!!.visibility = View.GONE
        }
        tvTitle.text = name
    }

    private fun init() {
        id = intent.getStringExtra(Constants.KeyConstant.ID)
        image = intent.getStringExtra(Constants.KeyConstant.IMAGE)
        name = intent.getStringExtra(Constants.KeyConstant.TITLE)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSend -> {
                if (TextUtils.isEmpty(edt_comment!!.text.toString().trim())) {
                    Utils.showToast(this, "Please Enter Comment")
                } else {
                    val word = Word(edt_comment!!.text.toString().trim(), id!!)
                    wordViewModel.insert(word)
                    edt_comment.setText("")
                    Utils.hideKeyBoard(this)
                }
            }
        }
    }
}