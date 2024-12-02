package com.example.donation.OnBoardingScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.donation.ui.theme.dRed


@Composable
fun IndicatorOne(
    totalPage: Int,
    currentPage: Int,
    selectedColor: androidx.compose.ui.graphics.Color = dRed,
    unSelectedColor :  androidx.compose.ui.graphics.Color = Color.Gray,
    ){

    Row(horizontalArrangement = Arrangement.SpaceBetween){
        repeat(totalPage){
            Box(modifier = Modifier
                .height(14.dp)
                .width(if (it == currentPage) 32.dp else 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = if (it == currentPage) selectedColor else unSelectedColor)
            )
            Spacer(modifier = Modifier.size(2.5.dp))
        }


    }

}


@Preview(showBackground = true)
@Composable
fun Preview1(){
    IndicatorOne(totalPage = 3, currentPage =0 )
}

@Preview(showBackground = true)
@Composable
fun Preview2(){
    IndicatorOne(totalPage = 3, currentPage =1 )
}

@Preview(showBackground = true)
@Composable
fun Preview3(){
    IndicatorOne(totalPage = 3, currentPage =2 )
}