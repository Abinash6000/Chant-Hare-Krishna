package com.project.chantharekrishna

import android.content.Intent
import android.os.Bundle
import android.service.credentials.Action
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.chantharekrishna.ui.theme.ChantHareKrishnaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var historyDao: HistoryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        historyDao = HistoryDatabase.getInstance(this).historyDao()
        setContent {
            ChantHareKrishnaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChantApp()
                }
            }
        }
    }
}

@Composable
fun ChantApp() {
    ChantHareKrishna()
}

@Preview
@Composable
fun ChantHareKrishna() {
    val context = LocalContext.current
    val historyDao = HistoryDatabase.getInstance(context).historyDao()
    val dataStoreManager = DataStoreManager(context)
    var data by rememberSaveable { mutableStateOf(Pair("0", "0")) }
    val coroutineScope = rememberCoroutineScope()

    var malaCount by rememberSaveable {
        mutableIntStateOf(data.first.toInt())
    }
    var mantraCount by rememberSaveable {
        mutableIntStateOf(data.second.toInt())
    }


    LaunchedEffect(key1 = dataStoreManager) {
        dataStoreManager.getFromDataStore().collect {
            data = it
            malaCount = it.first.toInt()
            mantraCount = it.second.toInt()

            val c = Calendar.getInstance()
            val dateTime = c.time
            val sdf = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
            val dateList = sdf.format(dateTime).split(" ")
            val date = "${dateList[0]} ${dateList[1]} ${dateList[2]}"

            // Create a HistoryEntity
            val historyEntity = HistoryEntity(date, malaCount, mantraCount)

            // Insert or update the HistoryEntity
            coroutineScope.launch(Dispatchers.IO) {
                historyDao.insertOrUpdate(historyEntity)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.caitanyanitai), contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.60f
        )
        IconButton(
            onClick = {
                val intent = Intent(context, HistoryActivity::class.java)
                context.startActivity(intent)
                      },
            Modifier
                .align(Alignment.TopStart)
                .padding(4.dp, 32.dp, 0.dp, 0.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.ic_history_edu), contentDescription = "History"
            ,modifier = Modifier.width(64.dp).height(64.dp))
        }
        IconButton(
            onClick = {
                val intent = Intent(context, FocusChantActivity::class.java)
                context.startActivity(intent)
            },
            Modifier
                .width(108.dp)
                .height(108.dp)
                .align(Alignment.TopEnd)
                .padding(4.dp, 32.dp, 0.dp, 0.dp)
        ) {}
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Hare Krishna\nHare Krishna\nKrishna Krishna\nHare Hare\nHare Rama\nHare Rama\nRama Rama\nHare Hare",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = Color(0xff9d0208),
                lineHeight = 48.sp,
                modifier = Modifier.padding(12.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$malaCount",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xff6a040f),
                    modifier = Modifier.padding(12.dp)
                )
                Text(text = "-",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xff6a040f),
                    modifier = Modifier.padding(12.dp)
                )
                Text(text = "$mantraCount",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                color = Color(0xff6a040f),
                modifier = Modifier.padding(12.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    mantraCount = 0
                    malaCount = 0
                    coroutineScope.launch {
                        dataStoreManager.saveToDataStore("0", "0")
                    }
                                 },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xfff48c06))
                    ) {
                    Text(text = "R",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
                Button(onClick = {
                    mantraCount++
                    if(mantraCount == 108) {
                        mantraCount = 0
                        malaCount++
                    }
                    coroutineScope.launch {
                        val stringMlc = malaCount.toString()
                        val stringMnc = mantraCount.toString()
                        dataStoreManager.saveToDataStore(stringMlc, stringMnc)
                    }
                                 },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xfff48c06))
                ) {
                    Text(text = "+",
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(horizontal = 48.dp, vertical = 64.dp)
                    )
                }
                Button(onClick = {
                    if(mantraCount != 0) mantraCount--
                    coroutineScope.launch {
                        val stringMlc = malaCount.toString()
                        val stringMnc = mantraCount.toString()
                        dataStoreManager.saveToDataStore(stringMlc, stringMnc)
                    }
                                 },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(2.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xfff48c06))) {
                    Text(text = "-",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }

}