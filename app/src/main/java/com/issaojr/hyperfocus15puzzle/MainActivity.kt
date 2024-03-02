package com.issaojr.hyperfocus15puzzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.RowScope
import androidx.compose.ui.graphics.RectangleShape

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    PuzzleBoard()
                }
            }
        }
    }
}

@Composable
fun PuzzleBoard() {
    val puzzleState = PuzzleState(4)
    val gridSize = puzzleState.gridSize

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until gridSize) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (j in 0 until gridSize) {
                    val index = i * gridSize + j
                    TileView(puzzleState.tiles[index].number)
                }
            }
        }
    }
}


@Composable
fun RowScope.TileView(number: Int) {
    Button(
        onClick = { /* TODO: Implement tile click logic */ },
        modifier = Modifier
            .aspectRatio(1f)
            .weight(1f),
        enabled = number != 0,
        shape = RectangleShape
    ) {
        if (number != 0) {
            Text(number.toString())
        }
    }
}



