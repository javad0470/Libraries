package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.test.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                val lis = listOf(
                    "ali",
                    "hashem",
                    "javad",
                    "hasan",
                    "ghasem",
                )
                Log.d("Kotlin",convertListToString(list = lis))
            }
        }
    }

    private val separator = ","

    fun convertListToString(list: List<String>): String {
        val stringBuilder = StringBuilder()
        for (item in list) {
            stringBuilder.append(item).append(separator)
        }

        stringBuilder.setLength(stringBuilder.length - 1)
        return stringBuilder.toString()
    }

    fun convertStringToList(string: String): List<String> {
        return string.split(separator)
    }
}
