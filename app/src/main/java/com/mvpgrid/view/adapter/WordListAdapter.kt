package com.mvpgrid.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mvpgrid.R
import com.mvpgrid.roomDB.Word
import java.util.*


class WordListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val dataList: MutableList<Word> = ArrayList()

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = dataList[position]
        holder.wordItemView.text = current.word
    }

    internal fun setWords(words: List<Word>, id: String?) {
        this.dataList.clear()
        for (item in words) {
            if (item.idData == id) {
                this.dataList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size
}


