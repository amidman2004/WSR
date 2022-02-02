package com.example.a01022022

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a01022022.TabScreens.disH

@Composable
fun MoreScreen(MainNav:NavHostController) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = { MainNav
            .navigate("MainScreen")}, modifier =
        Modifier
            .size(30.dp)
            .align(Start)) {
            Icon(painter =
            painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = null,)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Icon(painter = painterResource(id = disH.image), contentDescription = null,
        modifier = Modifier.size(300.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = disH.name)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = disH.price,color = Color.Red)

        Button(onClick = {
            MainNav
            .navigate("MainScreen") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ), modifier = Modifier.clip(RoundedCornerShape(10))
                .size(300.dp,70.dp)) {
            Text(text = "Add to Cart")
            Icon(painter = painterResource(id = R.drawable.ic_baseline_shopping_cart_24), contentDescription = null)
        }
    }
}