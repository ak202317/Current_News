package com.codingbalance.currentnews

data class News (
    val title: String,
    val author: String,
    val url: String,
    val imageUrl: String
) {
    constructor() : this("", "", "", "")
}