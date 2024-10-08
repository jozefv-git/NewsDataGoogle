package com.jozefv.newsdata.auth.domain

fun isUserValid(email:String,password: String): Boolean{
    return email == "elonga@elonga.com" && password == "ElongaTheBest"
}