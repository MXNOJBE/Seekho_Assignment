package com.compose.seekhoassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.compose.seekhoassignment.nav.Navigation
import com.compose.seekhoassignment.ui.AnimeList
import com.compose.seekhoassignment.ui.theme.SeekhoAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeekhoAssignmentTheme {
                Surface(){
                    Navigation()
                }
            }
        }
    }
}
