package com.example.a01022022

import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a01022022.retrofit.ApiRequests
import com.example.a01022022.retrofit.user
import com.example.a01022022.ui.theme.A01022022Theme
import com.google.accompanist.pager.ExperimentalPagerApi
import java.util.*

lateinit var pref:SharedPreferences
var LOGIN:String? = ""

var PWD:String? = ""
class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A01022022Theme {
                pref = getSharedPreferences("TABLE", MODE_PRIVATE)
                LOGIN = pref.getString("LOGIN","")
                PWD = pref.getString("PWD","")

                // A surface container using the 'background' color from the theme
                val MainNav = rememberNavController()


                NavHost(navController = MainNav, startDestination = "LoginScreen"){
                    composable("LoginScreen"){LoginScreen(MainNav = MainNav)}
                    composable("BankomatScreen"){ BankomatScreen(MainNav = MainNav)}
                    composable("ValuteScreen"){ ValuteScreen(MainNav = MainNav)}
                    composable("MainScreen"){ MainScreen(MainNav = MainNav) }
                    composable("MoreScreen"){ MoreScreen(MainNav = MainNav) }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(MainNav:NavHostController) {
    val dialogValue = remember {
        mutableStateOf(false)
    }
    if (dialogValue.value)
        Dialog(dialogValue = dialogValue, MainNav = MainNav)
    Column(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color.Cyan, Color.Green)))
    , horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.padding(top = 20.dp)) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_museum_24),
                contentDescription = null,modifier = Modifier.size(60.dp),tint = Color.Magenta)
            Spacer(Modifier.width(20.dp))
            Text(text = "WSR BANK",color = Color.Magenta)
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        BankCard( MainNav = MainNav)
        Spacer(modifier = Modifier.height(80.dp))
        ValuteCard( MainNav = MainNav)
        Spacer(modifier = Modifier.height(40.dp))
        Button(modifier = Modifier.size(230.dp,120.dp), onClick = {
            if (LOGIN!!.isEmpty()){
                dialogValue.value = true
            }else
            {
                ApiRequests().Auth(user(LOGIN!!,PWD!!)){
                    MainNav.navigate("MainScreen")
                }
            }
             }, colors = ButtonDefaults
            .buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            )) {
            Text(text = "LOGIN")
        }
    }
    
}

@Composable
fun Dialog(dialogValue: MutableState<Boolean>,MainNav: NavHostController) {
    var login by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = {}, buttons = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Authorization", fontSize = 16.sp)
            Text(text = "Enter your Login and Password", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(5.dp))
            TextField(value = login, onValueChange = {
                login = it
            }, placeholder = { Text(text = "Login")})
            TextField(value = password, onValueChange = {
                password = it
            }, placeholder = { Text(text = "Password")}, visualTransformation = PasswordVisualTransformation())
            Row() {
                Button(modifier = Modifier.size(120.dp,40.dp), onClick = { dialogValue.value = false }, colors = ButtonDefaults
                    .buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    )) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(modifier = Modifier.size(120.dp,40.dp), onClick = {
                    ApiRequests().Auth(user(login,password)){
                        val edit = pref.edit()
                        edit.putString("LOGIN",login)
                        edit.putString("PWD",password)
                        edit.apply()
                        MainNav.navigate("MainScreen")
                    }
                     }, colors = ButtonDefaults
                    .buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    )) {
                    Text(text = "Cancel")
                }
            }
        }
    })
}


@Composable
fun BankCard(MainNav: NavHostController) {
    Row(modifier = Modifier
        .size(350.dp, 170.dp)
        .clip(shape = RoundedCornerShape(10))
        .background(Color.White)
        .border(1.dp, Color.Black, RoundedCornerShape(10))
        .clickable(
            onClick = { MainNav.navigate("BankomatScreen") }
        ), verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.width(20.dp))
        Icon(painter = painterResource(id = R.drawable.ic_baseline_museum_24), contentDescription = null,
        Modifier.size(50.dp), tint = Color.Magenta)
        Spacer(modifier = Modifier.width(30.dp))
        Text(text = "Bankomats", color = Color.Black)
    }
}

@Composable
fun ValuteCard(MainNav: NavHostController) {
    var usd by remember {
        mutableStateOf("--")
    }
    var eur by remember {
        mutableStateOf("--")
    }

    ApiRequests().getValute {
        it.forEach {
            when(it.code){
                "EUR" -> eur = it.buy.toString()
                "USD" -> usd = it.buy.toString()
            }
        }
    }
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    val time =Calendar.getInstance().time
    val currentTime = formatter.format(time)
    Column(modifier = Modifier
        .size(350.dp, 170.dp)
        .clip(shape = RoundedCornerShape(10))
        .background(Color.White)
        .border(1.dp, Color.Black, RoundedCornerShape(10))
        .clickable(
            onClick = {
                MainNav.navigate("ValuteScreen")
            }
        )
        ) {
        Spacer(modifier = Modifier.height(15.dp))
        Row() {
            Spacer(modifier = Modifier.width(15.dp))
            Icon(painter = painterResource(id = R.drawable.ic_baseline_attach_money_24),
                contentDescription = null, modifier = Modifier.size(50.dp), tint = Color.Black)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Valute", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(70.dp))
            Text(text = currentTime, fontSize = 12.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(modifier = Modifier.align(CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "USD: ", fontSize = 12.sp)
            Text(text = usd, fontSize = 12.sp)
            Spacer(modifier = Modifier.size(60.dp))
            Text(text = "EUR: ", fontSize = 12.sp)
            Text(text = eur, fontSize = 12.sp)
        }
    }
}