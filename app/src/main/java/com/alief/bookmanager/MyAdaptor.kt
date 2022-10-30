package com.alief.bookmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdaptor(
    val list: ArrayList<BookModel>,
    val listener: OnItemClickListener,
    val context: Context
) : RecyclerView.Adapter<MyAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = list[position]

        holder.idTextView.text = (position + 1).toString()
        holder.titleTextView.text = book.name
        holder.authorTextView.text = book.author
        holder.pagesTextView.text = book.pages
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val idTextView: TextView = itemView.findViewById(R.id.item_id)
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val authorTextView: TextView = itemView.findViewById(R.id.item_author)
        val pagesTextView: TextView = itemView.findViewById(R.id.item_pages)

        private val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.recycler_view_anim)

        init {
            itemView.setOnClickListener(this)
            itemView.animation = anim
        }

        override fun onClick(view: View?) {
            val position = bindingAdapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClicked(list[position])
            }
        }
    }

}

interface OnItemClickListener {
    fun onItemClicked(bookModel: BookModel)
}