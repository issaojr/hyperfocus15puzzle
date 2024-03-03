package com.issaojr.hyperfocus15puzzle

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    PuzzleBoard()
                }
            }
        }
    }
}

@Composable
fun PuzzleBoard() {
    val puzzleState = remember {PuzzleState(4)}
    val gridSize = puzzleState.gridSize
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Movimentos: ${puzzleState.movesCounter}", fontSize = 20.sp)
        for (i in 0 until gridSize) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (j in 0 until gridSize) {
                    val index = i * gridSize + j
                    TileView(puzzleState, index)
                }
            }
        }
    }
}


@Composable
fun RowScope.TileView(puzzleState: PuzzleState, index: Int) {
    val context = LocalContext.current
    val number = puzzleState.tiles[index].number
    val showDialog = remember { mutableStateOf(false) }

    Button(
        onClick = {
            if (puzzleState.canMove(index)) {
                puzzleState.move(index)
                Toast.makeText(context, "Tile $number moved", Toast.LENGTH_SHORT).show()
                puzzleState.incrementMovesCounter()
                if(puzzleState.isGameFinished()) {
                    showDialog.value = true
                }
            }
        },
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

    if(showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                puzzleState.reset()
            },
            title = { Text("Parabéns!") },
            text = { Text("Você finalizou o jogo!\nMovimentos: ${puzzleState.movesCounter}") },
            confirmButton = {
                Button(onClick = {
                /* código para recomeçar o jogo */
                    showDialog.value = false
                    puzzleState.reset()
                }) {
                    Text("Recomeçar")
                }
            },
            dismissButton = {
                Button(onClick = { (context as Activity).finishAffinity() }) {
                    Text("Sair")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
