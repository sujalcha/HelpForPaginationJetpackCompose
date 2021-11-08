package com.example.helpforpaginationjetpackcompose

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

const val PAGE_SIZE = 50

@HiltViewModel
class PhotoDetailPageViewModel @Inject constructor(private val photoRepository: PhotoRepository) :
    ViewModel() {

    val page = mutableStateOf(1)
    var photoListScrollPosition = 0

    private var currpage = 0
    var endReached = mutableStateOf(false)


    init {
        getphotos()
    }

    var photodetaillist = mutableStateOf<List<MainPhoto>>(listOf())

    fun getphotos() {
        viewModelScope.launch {
            var result = photoRepository.getphotos(1)

            when (result) {
                is Resource.Success -> {
                    val photoentry = result.data!!.mapIndexed { index, entry ->

                        MainPhoto(
                            entry.albumId,
                            entry.id,
                            entry.thumbnailUrl,
                            entry.title,
                            entry.url
                        )
                    }
                    currpage++
                    photodetaillist.value += photoentry
                    Log.d("photoentry", photoentry.toString() + "")
                }
                is Resource.Error -> {
                }
            }
        }
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun getnextpage() {
        viewModelScope.launch {
            // prevent duplicate event due to recompose happening to quickly
            if ((photoListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                incrementPage()
                Log.d("Tag", "nextPage: triggered: ${page.value}")

                // just to show pagination, api is fast
//                delay(1000)

                if (page.value > 1) {
                    //   val result = repository.search(token = token, page = page.value, query = query.value)
                    var result = photoRepository.getphotos(page.value)
                    when (result) {
                        is Resource.Success -> {

                            val photoentry = result.data!!.mapIndexed { index, entry ->
                                MainPhoto(
                                    entry.albumId,
                                    entry.id,
                                    entry.thumbnailUrl,
                                    entry.title,
                                    entry.url
                                )
                            }
                            photodetaillist.value += photoentry
                            Log.d("photoentry", photoentry.toString() + "")
                        }
                        is Resource.Error -> {
                        }
                    }


                    Log.d("TAG", "search: appending")
                  //  appendRecipes(result)
                }
//                loading.value = false
            }
        }
    }

    private fun appendRecipes(photolist: List<MainPhoto>) {
//        val current = photolist
//        current.addAll(photolist)
//        this.photolist.value = current
    }

    fun onChangephotoScrollPosition(position: Int){
        photoListScrollPosition = position
    }
}