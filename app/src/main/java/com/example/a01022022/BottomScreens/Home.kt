package com.example.a01022022.BottomScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.a01022022.TabScreens.FoodScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalPagerApi
@Composable
fun HomeScreen(bottomNav:NavHostController,MainNav:NavHostController) {
    val tabList = arrayListOf<String>("Snacks","Food","Drinks","Sauces")
    val pagerState = rememberPagerState(pageCount = tabList.size)
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        TabRow(selectedTabIndex = 0, backgroundColor = Color.Gray,
        indicator = {
            TabRowDefaults.Indicator(
            color = Color.Red, modifier = Modifier.pagerTabIndicatorOffset(pagerState,it)
        )}) {
            tabList.forEachIndexed{index: Int, name: String ->
                Tab(selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                index
                            )
                        }
                    }, text = { Text(text = name)},
                    selectedContentColor = Color.Red,
                unselectedContentColor = Color.White)
            }
        }
    }) {
        HorizontalPager(state = pagerState) {
            when(it){
                0 -> FoodScreen(MainNav,bottomNav)
            }
        }
    }
}