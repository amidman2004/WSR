package com.example.a01022022

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a01022022.BottomScreens.HomeScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

data class bottomEl(var name:String,var image:Int)

@ExperimentalPagerApi
@Composable
fun MainScreen(MainNav:NavHostController,startDestination:String? = "Home") {
    var topTextFieldString by remember {
        mutableStateOf("")
    }



    val bottomNav = rememberNavController()
    val bottomNavItems by bottomNav.currentBackStackEntryAsState()

    val bottomList = arrayListOf<bottomEl>(
        bottomEl("Home",R.drawable.ic_baseline_home_24),
        bottomEl("Profile",R.drawable.ic_baseline_account_box_24),
        bottomEl("Cart",R.drawable.ic_baseline_shopping_cart_24),
        bottomEl("History",R.drawable.ic_baseline_history_24),
        )
    val context = LocalContext.current
    Scaffold(topBar = {
        Column(Modifier.background(Color.Gray)) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White
                )) {
                Spacer(modifier = Modifier.width(10.dp))
                TextField(value = topTextFieldString, onValueChange = {
                    topTextFieldString = it
                }, modifier = Modifier
                    .width(250.dp)
                    .background(Color.White))
                Spacer(modifier = Modifier.width(20.dp))
                Icon(painter = painterResource(id = R.drawable.ic_baseline_android_24)
                    , contentDescription = null, modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.width(20.dp))
                Icon(painter = painterResource(id = R.drawable.ic_baseline_android_24)
                    , contentDescription = null, modifier = Modifier.size(40.dp))
            }
            AndroidView(factory = { AdView(context).apply {
                adUnitId = "ca-app-pub-3940256099942544/6300978111"
                adSize = AdSize.BANNER
                loadAd(AdRequest.Builder().build())
            } }, modifier = Modifier.fillMaxWidth())
        }

    },
    bottomBar = { BottomNavigation(backgroundColor = Color.Gray){
        bottomList.forEachIndexed{index: Int, bottomEl: bottomEl ->
            BottomNavigationItem(selected = bottomEl.name == bottomNavItems?.destination?.route , onClick =
            {bottomNav.navigate(bottomEl.name)}, label = { Text(text = bottomEl.name)},
            icon = { Icon(painter = painterResource(id = bottomEl.image),
                contentDescription = null)}, unselectedContentColor = Color.White,
            selectedContentColor = Color.Red)
        }
    }
    }) {
        NavHost(navController = bottomNav, startDestination = startDestination!!){
            composable("Home"){ HomeScreen(bottomNav, MainNav )}
            composable("Profile"){}
            composable("Cart"){}
            composable("History"){}

        }
    }
}