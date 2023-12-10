package com.stackbilly.compose_tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.stackbilly.compose_tutorial.ui.theme.ConsumeAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConsumeAPITheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeActivity()
                }
            }
        }
    }
}
