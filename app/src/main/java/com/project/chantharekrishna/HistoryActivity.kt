package com.project.chantharekrishna

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.chantharekrishna.ui.theme.ChantHareKrishnaTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ChantHareKrishnaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    HistoryScreen(context.applicationContext as Application)
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(application: Application) {
    val dao = HistoryDatabase.getInstance(application).historyDao()
    val historyList by dao.fetchAllDates().collectAsState(initial = emptyList())
    HistoryCards(historyData = historyList)
}

@Composable
fun HistoryCards(historyData: List<HistoryEntity>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.krishna),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alpha = 0.7f,
            modifier =  Modifier.fillMaxSize()
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 24.dp, 0.dp, 8.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            items(historyData) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xffede7b1),
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Text(text = "Mala: ${item.malaCount}",
                            fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Mantra: ${item.mantraCount}", fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Date: ${item.date}", fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}