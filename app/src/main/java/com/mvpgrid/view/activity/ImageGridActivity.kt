package com.mvpgrid.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvpgrid.R
import com.mvpgrid.common.BaseActivity
import com.mvpgrid.common.Utils
import com.mvpgrid.model.DataResponse
import com.mvpgrid.model.GridData
import com.mvpgrid.presenter.GridPresenter
import com.mvpgrid.view.GridView
import com.mvpgrid.view.adapter.ImageGridAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class ImageGridActivity : BaseActivity<GridView?, GridPresenter?>(),
    GridView, View.OnClickListener {
    private val dataList: MutableList<GridData> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var edtSearch: EditText? = null
    private var mAdapter: ImageGridAdapter? = null
    private var sKeyWords = "vanilla"
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setupToolbarBack(resources.getString(R.string.app_name))
        setAdapter()
        searchKeyword()
        setFontsToViews()
        presenter!!.getGridImage(sKeyWords)
    }

    private fun setAdapter() {
        mAdapter = ImageGridAdapter(this, dataList, this)
        val gridLayoutManager = GridLayoutManager(applicationContext, 4)
        recyclerView!!.layoutManager = gridLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = mAdapter
    }

    private fun searchKeyword() {
        edtSearch!!.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Utils.hideKeyBoard(this)
                sKeyWords = if (edtSearch!!.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                    edtSearch!!.text.toString().trim { it <= ' ' }
                } else {
                    "vanilla"
                }
                dataList.clear()
                mAdapter!!.notifyDataSetChanged()
                presenter!!.getGridImage(sKeyWords)
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun createPresenter(): GridPresenter {
        return GridPresenter(this)
    }

    private fun init() {
        recyclerView = findViewById<View>(R.id.recy_list) as RecyclerView
        edtSearch = findViewById(R.id.edt_search)
        toolbar = findViewById(R.id.toolbar)
    }

    /**
     * Set Toolbar
     */
    protected fun setupToolbarBack(title: String?) {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            if (supportActionBar != null) {
                supportActionBar!!.setDisplayShowTitleEnabled(true)
                toolbar!!.title = title
            }
        }
    }

    override fun showLoader() {
        showProcessor()
    }

    override fun hideLoader() {
        hideProcessor()
    }

    override fun setGridResponse(jsonResponse: JSONObject) {
        dataList.clear()
        mAdapter!!.notifyDataSetChanged()
        val dataResponse = Utils.getStringToModelData(
            jsonResponse.toString(),
            DataResponse::class.java
        ) as DataResponse
        if (dataResponse.data != null && dataResponse.data.size > 0) {
            dataList.addAll(dataResponse.data)
            mAdapter!!.notifyDataSetChanged()
        }

        if (dataList.size == 0) {
            tvEmptyResult!!.visibility = View.VISIBLE
        } else {
            tvEmptyResult!!.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFontsToViews() {}

    companion object {
        private var BASE_URL = "https://api.imgur.com"

        @JvmField
        val GET_IMAGES_GRID =
            "$BASE_URL/3/gallery/search/1?q="
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivGrid -> {
                val model = v.tag as GridData
                if (model.id != null && !TextUtils.isEmpty(model.id)) {
                    var title = ""
                    if (!TextUtils.isEmpty(model.title)) {
                        title = model.title
                    }
                    var image = ""
                    try {
                        image = model.images[0].link
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    ImageCommentsActivity.startActivity(mActivity, model.id, image, title)
                }
            }
        }
    }
}