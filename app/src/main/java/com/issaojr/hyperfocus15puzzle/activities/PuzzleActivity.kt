package com.issaojr.hyperfocus15puzzle.activities

import android.os.Bundle
import androidx.core.app.ActivityCompat.recreate
import com.issaojr.hyperfocus15puzzle.PuzzleState
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.issaojr.hyperfocus15puzzle.R


class PuzzleActivity : ComponentActivity() {

    private lateinit var puzzleState: PuzzleState

    @Composable
    fun PuzzleScreen(puzzleState: PuzzleState) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PuzzleScreen(puzzleState = puzzleState)
        }
    }
}


