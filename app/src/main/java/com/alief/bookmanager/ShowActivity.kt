package com.alief.bookmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class ShowActivity : AppCompatActivity() {


    lateinit var titleTextView: TextView
    lateinit var authorTextView: TextView
    lateinit var pagesTextView: TextView
    lateinit var updateButton: Button
    lateinit var deleteButton: Button
    lateinit var linearLayout: LinearLayout
    lateinit var title: String
    lateinit var author: String
    lateinit var pages: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_activty)
        viewFinder()

        val gson = Gson()
        val bookObj = gson.fromJson(intent.getStringExtra("book"), BookModel::class.java)

        titleTextView.text = bookObj.name
        authorTextView.text = bookObj.author
        pagesTextView.text = bookObj.pages

        val db = MyDataBaseHelper(this)

        updateButton.setOnClickListener {

            title = titleTextView.text.toString()
            author = authorTextView.text.toString()
            pages = pagesTextView.text.toString()

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(pages)) {
                Snackbar.make(linearLayout, "همه مقادیر را پر کنید", Snackbar.LENGTH_SHORT).show()
            } else {

                val book = BookModel(title, author, pages, bookObj.id)
                db.updateBook(book)
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        deleteButton.setOnClickListener {

            db.deleteBook(bookObj)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

private fun viewFinder() {
    titleTextView = findViewById(R.id.add_title_et)
    authorTextView = findViewById(R.id.add_author_et)
    pagesTextView = findViewById(R.id.add_pages_et)
    updateButton = findViewById(R.id.update_button)
    deleteButton = findViewById(R.id.delete_button)
    linearLayout = findViewById(R.id.add_layout)

}


}