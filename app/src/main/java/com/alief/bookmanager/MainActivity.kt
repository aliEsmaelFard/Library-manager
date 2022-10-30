package com.alief.bookmanager

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import androidx.appcompat.widget.Toolbar
class MainActivity : AppCompatActivity(), OnItemClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var bookadaptor: MyAdaptor
    lateinit var db: MyDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MyDataBaseHelper(this)

        //set up toolbar
        val deleteAllButton: ImageView = findViewById(R.id.delete_all)
        deleteAllButton.setOnClickListener { showAlertDialog() }

        recyclerView = findViewById(R.id.recyclerview)
        val noDataTextView: TextView = findViewById(R.id.no_data_text)

        db.getAllBooks().observe(this, Observer { arrayList ->

            if (arrayList.isEmpty()) {
                noDataTextView.visibility = View.VISIBLE
            }
            recyclerView.adapter = MyAdaptor(arrayList, this, this)
        })
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        //set up fab
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }


    override fun onItemClicked(bookModel: BookModel) {
        val gson = Gson()
        val jsonString = gson.toJson(bookModel)

        val intent = Intent(this, ShowActivity::class.java)
        intent.putExtra("book", jsonString)
        startActivity(intent)
    }

    fun showAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setMessage("آیا مایل به حدف همه اطلاعات هستید؟")
            .setPositiveButton("بله") { _, _ ->
                db.deleteAll()
                startActivity(intent);
                finish();
            }
            .setNegativeButton("خیر") { dialog, _ ->
                dialog.cancel()
            }
            .setIcon(R.drawable.ic_launcher_foreground)
            .show()
    }

}

