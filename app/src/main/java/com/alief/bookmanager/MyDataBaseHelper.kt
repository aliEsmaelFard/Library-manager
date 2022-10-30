package com.alief.bookmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyDataBaseHelper(
    context: Context,
) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "LibraryDataBase"
        private const val VERSION = 1
        private const val TABLE_NAME = "library"
        private const val COLUMN_ID = "id"
        private const val COLUMN_BOOK_NAME = "book_name"
        private const val COLUMN_AUTHOR = "author"
        private const val COLUMN_PAGES = "pages"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "CREATE TABLE $TABLE_NAME " +
                "( $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_BOOK_NAME TEXT," +
                " $COLUMN_AUTHOR TEXT," +
                " $COLUMN_PAGES INTEGER );"
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addBook(book: BookModel): Long {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_BOOK_NAME, book.name)
        cv.put(COLUMN_AUTHOR, book.author)
        cv.put(COLUMN_PAGES, book.pages)

        val insertQuery = db.insert(TABLE_NAME, null, cv)
        db.close()
        return insertQuery
    }

    fun getAllBooks(): MutableLiveData<ArrayList<BookModel>> {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val liveData: MutableLiveData<ArrayList<BookModel>> = MutableLiveData()
        val arrayList = arrayListOf<BookModel>()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            Log.d("SQLiteException", e.message.toString())
        }

        if (cursor!!.moveToFirst()){

            do {
                val book =  BookModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(0)
                )
                arrayList.add(book)

            } while (cursor.moveToNext())

        }
        cursor.close()
        liveData.postValue(arrayList)
        return liveData
    }

    fun updateBook(book: BookModel): Long {
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(COLUMN_BOOK_NAME, book.name)
        cv.put(COLUMN_AUTHOR, book.author)
        cv.put(COLUMN_PAGES, book.pages)

        println("book is  name: ${book.name}  author: ${book.author}  id: ${book.id}")

        val updateQuery = db.update(TABLE_NAME, cv, "$COLUMN_ID=?", arrayOf(book.id.toString()))
        db.close()
        return updateQuery.toLong()
    }

    fun deleteBook(book: BookModel): Long {
        val db = this.writableDatabase

        val deleteQuery =  db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(book.id.toString()))
        db.close()
        return deleteQuery.toLong()
    }

    fun deleteAll() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
}

