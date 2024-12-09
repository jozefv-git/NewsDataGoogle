package com.jozefv.newsdata.auth.domain

fun isUserValid(email:String,password: String): Boolean{
    return email == "sygic@sygic.com" && password == "SygicTestPass"
}