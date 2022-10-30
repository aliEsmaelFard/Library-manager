package com.alief.bookmanager

data class BookModel(
    var name: String,
    var author: String,
    var pages: String,
    var id: Int = 0,
) {
}