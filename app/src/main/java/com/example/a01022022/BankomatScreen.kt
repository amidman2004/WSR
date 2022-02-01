package com.example.a01022022

import android.annotation.SuppressLint
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.a01022022.retrofit.ApiRequests
import com.example.a01022022.retrofit.bankomat
import java.text.SimpleDateFormat
import java.util.jar.Manifest

@SuppressLint("MissingPermission")
@Composable
fun BankomatScreen(MainNav:NavHostController) {
    val mapView = rememberMapView()
    var bankomatList:ArrayList<bankomat> by remember {
        mutableStateOf(arrayListOf())
    }
    ApiRequests().getBankomats {
        bankomatList = it
    }
    val permissionCheck = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(), ){result->
        mapView.getMapAsync {
            it.isMyLocationEnabled = result
        }
    }

    LaunchedEffect(LocalContext.current){
        permissionCheck.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
    Column() {
        IconButton(onClick = {MainNav.navigate("LoginScreen")}, modifier = Modifier
            .size(40.dp)
            .padding(start = 10.dp)) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription =  null)
        }
        AndroidView(factory = {mapView}, modifier =
        Modifier
            .fillMaxWidth()
            .height(500.dp)){
            it.getMapAsync {
                it.uiSettings.isZoomControlsEnabled = true
            }
        }
        Text(text = "Bankomats", modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray))
        LazyColumn(Modifier.fillMaxWidth()){
            itemsIndexed(bankomatList){index: Int, item: bankomat ->  
                BankomatElement(bankomat = item)
            }
        }
    }
}

fun time(time_from:Long,time_to:Long):String{
    val formatter = SimpleDateFormat("hh:mm")
    val time0 = formatter.format(time_from)
    val time1 = formatter.format(time_to)
    return "Working by $time0 - $time1"
}

@Composable
fun BankomatElement(bankomat: bankomat) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.Black, RoundedCornerShape(10))) {
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = bankomat.name, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(7.dp))

        Row(Modifier.padding(start = 7.dp)) {
            Text(text = "Bankomat", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = if (bankomat.is_working) "Open" else "Close", color = if (bankomat.is_working) Color.Green
            else Color.Red, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(50.dp))
            Text(text = time(bankomat.time_from,bankomat.time_to), fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(5.dp))
    }
}

