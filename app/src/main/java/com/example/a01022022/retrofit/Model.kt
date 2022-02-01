package com.example.a01022022.retrofit


data class valute(var buy:Int,var sell:Int,var code:String)
data class user(var login:String,var pwd:String)
data class bankomat(var name:String,var time_from:Long,var time_to:Long, var is_working:Boolean)
class Model {
    companion object{
        var valuteList = arrayListOf<valute>()
        var bankomatList = arrayListOf<bankomat>()

    }
}