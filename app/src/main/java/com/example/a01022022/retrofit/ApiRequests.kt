package com.example.a01022022.retrofit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiRequests:ViewModel() {



    fun getValute(onGet:(list:ArrayList<valute>)->Unit){
        if(Model.valuteList.isNotEmpty()){
            onGet(Model.valuteList)
        }
        else{
            viewModelScope.launch {
                try {
                    val connect = Connect.connect.getValute()
                    if (connect.isSuccessful){
                        connect.body()?.let{
                            Model.valuteList = it
                            onGet(it)
                        }
                    }
                }catch (e:Exception){
                    Log.d("adadasdasd",e.toString())
                }
            }
        }
    }

    fun getBankomats(onGet:(list:ArrayList<bankomat>)->Unit){
        if(Model.bankomatList.isNotEmpty()){
            onGet(Model.bankomatList)
        }
        else{
            viewModelScope.launch {
                try {
                    val connect = Connect.connect.getBankomats()
                    if (connect.isSuccessful){
                        connect.body()?.let{
                            Model.bankomatList = it
                            onGet(it)
                        }
                    }
                }catch (e:Exception){

                }
            }
        }
    }


    fun Auth(user: user,onGet: (Response<Void>) -> Unit){
        viewModelScope.launch {
            try {
                val connect = Connect.connect.Auth(user)
                if (connect.code()==201){
                    onGet(connect)
                }
            }catch (e:Exception){

            }

        }
    }
}