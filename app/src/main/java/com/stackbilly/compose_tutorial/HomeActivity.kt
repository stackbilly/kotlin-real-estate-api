package com.stackbilly.compose_tutorial

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.stackbilly.compose_tutorial.models.ApiHouseResponse
import com.stackbilly.compose_tutorial.models.Houses
import com.stackbilly.compose_tutorial.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

val fontFamily = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_black, FontWeight.Black),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold)
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeActivity(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                ),
                title = {
                    Text(
                        text = "Real Estate House",
                        style = TextStyle(
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.padding(end = 20.dp,)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .height(30.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding -> ScrollContent(innerPadding) }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues){
    val retrofitClient = RetrofitClient
    val ctx = LocalContext.current
    var housesData by remember {
        mutableStateOf<List<Houses>>(emptyList())
    }
    retrofitClient.getApiService(ctx).getHouses()
        .enqueue(object : Callback<ApiHouseResponse> {
            override fun onResponse(
                call: Call<ApiHouseResponse>,
                response: Response<ApiHouseResponse>
            ) {
                if (response.isSuccessful) {
                    housesData = response.body()!!.results
                    Log.e("Real Estate", "$housesData")
                }else{
                    Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ApiHouseResponse>, t: Throwable) {
                Toast.makeText(ctx, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Real Estate", "onFailure ${t.message}")
            }
        })
    LazyColumn(
        modifier = Modifier
            .padding(top = 60.dp)
    ){
        itemsIndexed(housesData){index, house ->
            if (housesData.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(5.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }else{
                HouseCard(houses = housesData[index])
            }
        }
    }
}

@Composable
fun HouseCard(houses:Houses){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        shape = RoundedCornerShape(5),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = houses.Image, 
                contentDescription = houses.Name,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = houses.Name.split("with")[0],
                overflow = TextOverflow.Clip,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 15.sp,
                ),
            )
            Text(
                text = "Price ${houses.Price}",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 15.sp,
                ),
            )
            Row{
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = houses.Location,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontFamily = fontFamily,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 15.sp,
                    )
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 10.dp),
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = "Check",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fontFamily,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp,
                    )
                )
            }
        }
    }
}