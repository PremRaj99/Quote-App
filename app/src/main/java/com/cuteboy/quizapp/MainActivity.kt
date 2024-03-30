package com.cuteboy.quizapp

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.cuteboy.quizapp.screens.QuoteDetail
import com.cuteboy.quizapp.screens.QuoteListScreen
import com.cuteboy.quizapp.utils.GifImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.LoadAssetsFromFile(applicationContext)
        }
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    if (DataManager.isDataLoded.value) {
        if(DataManager.currentPage.value == Pages.LISTING) {
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)
            }
        }
        else {
            DataManager.currentQuote?.let { QuoteDetail(quote = it) }
        }
        
    } else {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GifImage()
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Loading...", style = MaterialTheme.typography.headlineLarge
                )
            }


        }
    }
}


