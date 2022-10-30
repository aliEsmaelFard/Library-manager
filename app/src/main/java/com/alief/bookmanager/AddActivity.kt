package com.alief.bookmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class AddActivity : AppCompatActivity() {

    lateinit var titleTextView: TextView
    lateinit var authorTextView: TextView
    lateinit var pagesTextView: TextView
    lateinit var saveButton: Button
    lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_acticty)
        viewFinder()

        saveButton.setOnClickListener{
            val title = titleTextView.text.toString()
            val author = authorTextView.text.toString()
            val pages = pagesTextView.text.toString()

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(pages) )
            {
                Snackbar.make(linearLayout, "همه مقادیر را پر کنید", Snackbar.LENGTH_SHORT).show()
            } else {
                val db = MyDataBaseHelper(this)
                val insert = db.addBook(BookModel(title, author, pages))

                if (insert < 0) {
                    Snackbar.make(linearLayout, "خطای رخ داد!", Snackbar.LENGTH_SHORT).show()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }

    private fun viewFinder()
    {
        titleTextView = findViewById(R.id.add_title_et)
        authorTextView = findViewById(R.id.add_author_et)
        pagesTextView = findViewById(R.id.add_pages_et)
        saveButton = findViewById(R.id.save_button)
        linearLayout = findViewById(R.id.add_layout)
    }
}