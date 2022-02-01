package com.example.a01022022.TabScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.resolveDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a01022022.R

var disH = dish("","",0)
data class dish(var name:String,var price:String,var image:Int)
@ExperimentalFoundationApi
@Composable
fun FoodScreen(MainNav: NavHostController,bottomNav: NavHostController) {
    val foodNav = rememberNavController()

    NavHost(navController = foodNav, startDestination = "FoodList"){
        composable("FoodList"){FoodListScreen(foodNav )}
        composable("SelectedScreen"){ SelectedScreen(foodNav, MainNav, bottomNav)}
    }
}

@ExperimentalFoundationApi
@Composable
fun FoodListScreen(foodNav: NavHostController) {
    val foodList = arrayListOf<dish>(
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
        dish("MAKAROHI","100 RUB", R.drawable.ic_baseline_shopping_cart_24),
    )
    LazyVerticalGrid(cells = GridCells.Fixed(2)){
        itemsIndexed(foodList){index, item ->
            FoodEl(dish = item, foodNav  )
        }
    }
}

@Composable
fun FoodEl(dish: dish,foodNav:NavHostController) {
    Column(modifier = Modifier
        .padding(20.dp)
        .clip(RoundedCornerShape(10))
        .background(Color.White)
        .border(1.dp, Color.Black, RoundedCornerShape(10))
        .clickable(
            onClick = {
                disH = dish
                foodNav.navigate("SelectedScreen")
            }
        ), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = dish.image),
            contentDescription = null,
            Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, RoundedCornerShape(10)),
            tint = Color.Black)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = dish.name, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = dish.price, fontSize = 14.sp,color = Color.Red)

    }
}


@Composable
fun SelectedScreen(foodNav: NavHostController,MainNav:NavHostController,bottomNav:NavHostController) {
    var checker by remember {
        mutableStateOf(false)
    }
    var quantity by remember {
        mutableStateOf(0)
    }

    @Composable
    fun StartButtons() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (quantity>0)
                    quantity-=1
            }) {
                Icon(painter =
                painterResource(id = R.drawable.ic_baseline_android_24),
                    contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Text(text = quantity.toString())
            IconButton(onClick = {
                    quantity+=1
            }) {
                Icon(painter =
                painterResource(id = R.drawable.ic_baseline_android_24),
                    contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(onClick = {checker = true},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ), modifier = Modifier.clip(RoundedCornerShape(10))) {
                Text(text = "Add to Cart")
                Icon(painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24), contentDescription = null)
            }
        }
    }

    @Composable
    fun EndButtons() {
        Column {
            Button(onClick = {foodNav.navigate("FoodList")},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White
                ), modifier = Modifier
                    .size(200.dp, 40.dp)
                    .clip(RoundedCornerShape(10))) {
                Text(text = "Continue Shoping")
            }
            Spacer(modifier = Modifier.height(3.dp))
            Button(onClick = {bottomNav.navigate("Cart")},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White
                ), modifier = Modifier
                    .size(200.dp, 40.dp)
                    .clip(RoundedCornerShape(10))) {
                Text(text = "Go to Cart")
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .size(300.dp)
            .clip(RoundedCornerShape(10))
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(10.dp))

            Row() {
                IconButton(onClick = { foodNav
                    .navigate("FoodList")}, modifier =
                Modifier
                    .size(30.dp)) {
                    Icon(painter =
                    painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = null,)
                }
                Spacer(modifier = Modifier.width(160.dp))
                TextButton(onClick = {MainNav
                    .navigate("MoreScreen")},) {
                    Text(text = "More",color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter =
                painterResource(id = disH.image),
                    contentDescription = null, modifier =
                    Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape))
                Spacer(modifier = Modifier.width(50.dp))
                Column() {
                    Text(text = disH.name)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = disH.price)
                }
            }

                Spacer(modifier = Modifier.height(20.dp))
            if (!checker)
                StartButtons()
            else
                EndButtons()

        }
    }
}