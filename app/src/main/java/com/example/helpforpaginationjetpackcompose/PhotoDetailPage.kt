package com.example.helpforpaginationjetpackcompose

import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest

@Composable
fun PhotoDetailPage(photoDetailPageViewModel: PhotoDetailPageViewModel = hiltViewModel()) {

    val photolist by remember { photoDetailPageViewModel.photodetaillist }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
    ) {

        val itemcount = photolist.size

        Log.d("itemcount",itemcount.toString()+"")

        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            itemsIndexed(
                items = photolist
            ) { index, photo ->
                photoDetailPageViewModel.onChangephotoScrollPosition(index)
                if((index + 1) >= (photoDetailPageViewModel.page.value * PAGE_SIZE) ){
                    photoDetailPageViewModel.getnextpage()
                }
                PhotoDetail(photo)
            }
        }
    }
}

@Composable
fun PhotoDetail(mainPhoto: MainPhoto) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .background(color = Color.White)
            .padding(20.dp)
    ) {

        Text(text = "Comment id: " + mainPhoto.id)
        Text(text = "Comment title: " + mainPhoto.title)
        Text(text = "Comment album id: " + mainPhoto.albumId)
        Text(text = "Comment album id: " + mainPhoto.thumbnailUrl)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            val painter = rememberImagePainter(data = mainPhoto.thumbnailUrl)
            //coil usage
            Box(modifier = Modifier
                .height(100.dp)
                .width(100.dp)) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
            }
        }
    }
}