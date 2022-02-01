package com.example.a01022022

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a01022022.retrofit.ApiRequests
import com.example.a01022022.retrofit.bankomat
import com.example.a01022022.retrofit.valute
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun ValuteScreen(MainNav:NavHostController) {
    val formatter = SimpleDateFormat("dd:MM:yyyy")
    val time = Calendar.getInstance().time
    val currenttime = formatter.format(time)

    var valuteList:ArrayList<valute> by remember {
        mutableStateOf(arrayListOf())
    }
    ApiRequests().getValute {
        valuteList = it
    }
    Column() {
        IconButton(onClick = {MainNav.navigate("LoginScreen")}, modifier = Modifier
            .size(40.dp)
            .padding(start = 10.dp)) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription =  null)
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = currenttime, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.width(80.dp))
            Text(text = "Buy", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.width(80.dp))
            Text(text = "Sell", fontSize = 14.sp, color = Color.Gray)

        }

        LazyColumn(Modifier.fillMaxWidth()){
            itemsIndexed(valuteList){index: Int, item: valute ->
                ValuteElement(valute = item)
            }
        }
    }
}

@Composable
fun ValuteElement(valute: valute) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Black, RoundedCornerShape(10))) {
        Spacer(modifier = Modifier.height(3.dp))
        Row(Modifier.padding(start = 6.dp)) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_android_24),
                contentDescription = null,tint = Color.Black,
                modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = valute.code, fontSize = 14.sp,color = Color.Black)

            Spacer(modifier = Modifier.width(90.dp))
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_upward_24),
                contentDescription = null, modifier = Modifier.size(10.dp), tint = Color.Green )
            Text(text = valute.buy.toString(), fontSize = 12.sp)

            Spacer(modifier = Modifier.width(90.dp))
            Icon(painter = painterResource(
                id = R.drawable.ic_baseline_arrow_upward_24),
                contentDescription = null,
                modifier = Modifier.size(10.dp), tint = Color.Red )
            Text(text = valute.sell.toString(), fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "VALUTE", fontSize = 12.sp, color = Color.Blue)
    }
}