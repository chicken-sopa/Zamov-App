package features.detalhesContraord

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.testapp.R
import com.example.testapp.ui.theme.UserInfoGrey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ImagemContraord(
    imgURL: String =
        "https://www.sinasamaki.com/content/images/2022/11/Screenshot-2022-11-23-at-08.45.08.png",
    contentScale: ContentScale = ContentScale.FillWidth
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val imageLoader = rememberAsyncImagePainter(
        model = imgURL,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            // Display a progress bar while loading
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp),
                color = UserInfoGrey,
            )
        }

        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = if (isError.not()) {
                contentScale
            } else {
                ContentScale.Fit
            },
            painter = if (isError.not()) {
                imageLoader
            } else {
                painterResource(R.drawable.baseline_warning_24)
            },

            contentDescription = null,
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SmallPhotosCarousel(
    imgURLs: List<String>,
    pagerState: PagerState,
    scope: CoroutineScope,
    turnBig: () -> Unit
) {
    val percentPicScreen = 0.2f
    Box(
        modifier = Modifier
            .height(LocalConfiguration.current.screenHeightDp.dp * percentPicScreen)
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable(onClick = turnBig),

        contentAlignment = Alignment.Center
    ) {
        //Text(text = "PHOTOS OF INFRAÇAO HERE")
        HorizontalPager(
            //pageCount = animals.size,
            state = pagerState,
            key = { imgURLs[it] },
            pageSize = PageSize.Fill
        ) { index ->
            ImagemContraord(imgURLs[index])
        }
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage - 1
                        )
                    }
                },
                modifier = Modifier
                    //.align(Alignment.CenterStart)
                    .background(Color.LightGray, shape = RoundedCornerShape(100))
                    .clip(RoundedCornerShape(100))
                //.fillMaxWidth()
                //.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Go back"
                )
            }
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .background(Color.LightGray, shape = RoundedCornerShape(100))
                    .clip(RoundedCornerShape(100))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Go back"
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BigPhotosCarousel(
    imgURLs: List<String>,
    pagerState: PagerState,
    scope: CoroutineScope,
    turnSmall: () -> Unit
) {
    val percentPicScreen = 0.2f
    Box(
        modifier = Modifier
            .height(LocalConfiguration.current.screenHeightDp.dp)
            //.width(LocalConfiguration.current.screenWidthDp.dp)
            .fillMaxWidth()
            .background(Color.Black)

            .clickable(onClick = turnSmall),

        contentAlignment = Alignment.Center
    ) {
        //Text(text = "PHOTOS OF INFRAÇAO HERE")
        HorizontalPager(
            //pageCount = animals.size,
            state = pagerState,
            key = { imgURLs[it] },
            pageSize = PageSize.Fill
        ) { index ->
            ImagemContraord(imgURLs[index], contentScale = ContentScale.Fit)
        }
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage - 1
                        )
                    }
                },
                modifier = Modifier
                    //.align(Alignment.CenterStart)
                    .background(Color.LightGray, shape = RoundedCornerShape(100))
                    .clip(RoundedCornerShape(100))
                //.fillMaxWidth()
                //.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Go back"
                )
            }
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .background(Color.LightGray, shape = RoundedCornerShape(100))
                    .clip(RoundedCornerShape(100))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Go back"
                )
            }
        }
    }
}