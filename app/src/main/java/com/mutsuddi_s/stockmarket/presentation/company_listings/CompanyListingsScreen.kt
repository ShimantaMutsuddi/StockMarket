package com.mutsuddi_s.stockmarket.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start= true)
fun CompanyListingsScreen(
    navigator: DestinationsNavigator ,
    viewmodel: CompanyListingsViewModel = hiltViewModel()
) {
    val swipeRefreashState= rememberSwipeRefreshState(
        isRefreshing = viewmodel.state.isRefreshing
    )
    //val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })
    
    val state= viewmodel.state
    
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewmodel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it),
                    )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text("Search...")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(
            state = swipeRefreashState ,
            onRefresh = {
                viewmodel.onEvent(CompanyListingsEvent.Refresh)
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(state.companis.size) {i->
                    val company = state.companis[i]
                    CompanyItem(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                //TODO: Navigate to detail screen
                            }
                            .padding(16.dp)

                    )
                    if(i< state.companis.size){
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }

                }
            }
            
        }
    }
}
