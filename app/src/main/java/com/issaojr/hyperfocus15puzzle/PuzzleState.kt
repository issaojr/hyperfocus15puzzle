package com.issaojr.hyperfocus15puzzle

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import kotlin.math.abs
import kotlin.random.Random
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

class PuzzleState(val gridSize: Int, val viewModel: PuzzleViewModel) {
    val tiles = mutableStateListOf(*List(gridSize * gridSize) { Tile(it) }.toTypedArray())

    init {
        shuffle()
    }

    @Composable
    fun onTileClickFromNonComposable(clickedTileIndex: Int) {
        // Access composable function using a coroutine
        viewModelScope.launch {
            onTileClick(clickedTileIndex)
        }
    }

    @Composable
    fun onTileClick(clickedTileIndex: Int) {
        val emptyTileIndex = tiles.indexOfFirst { it.isEmpty }

        if (isAdjacent(clickedTileIndex, emptyTileIndex, gridSize)) {
            swapTiles(clickedTileIndex, emptyTileIndex)

            if (isSolved()) {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text(stringResource(id = R.string.victory_title),
                        onTextLayout = {}) },
                    text = { Text(stringResource(id = R.string.victory_message),
                        onTextLayout = {}) },
                    confirmButton = {
                        Button(onClick = { /* Reiniciar o jogo */ }) {
                            Text(stringResource(id = R.string.restart),
                                onTextLayout = {})
                        }
                    },
                    dismissButton = {
                        Button(onClick = { /* Sair do jogo */ }) {
                            Text(stringResource(id = R.string.exit),
                                onTextLayout = {})
                        }
                    }
                )
            }
        }
    }

    private fun shuffle() {
        val random = Random.Default
        repeat(300) { // Make 300 random moves to shuffle the tiles
            val emptyTileIndex = tiles.indexOfFirst { it.isEmpty }
            val direction = listOf(-1, 1, -gridSize, gridSize)
                .filterNot { emptyTileIndex % gridSize == 0 && it == -1 }
                .filterNot { emptyTileIndex % gridSize == gridSize - 1 && it == 1 }
                .random(random)
            val otherTileIndex = emptyTileIndex + direction
            if (otherTileIndex in 0 until gridSize * gridSize) {
                tiles[emptyTileIndex] = tiles[otherTileIndex].also { tiles[otherTileIndex] = tiles[emptyTileIndex] }
            }
        }
    }

    private fun isAdjacent(clickedTileIndex: Int, emptyTileIndex: Int, gridSize: Int): Boolean {
        val rowDiff = abs((clickedTileIndex / gridSize) - (emptyTileIndex / gridSize))
        val colDiff = abs((clickedTileIndex % gridSize) - (emptyTileIndex % gridSize))
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)
    }

    public fun canSwapTiles(clickedTileIndex: Int): Boolean {
        val emptyTileIndex = tiles.indexOfFirst { it.isEmpty }
        return isAdjacent(clickedTileIndex, emptyTileIndex, gridSize)
    }

    private fun swapTiles(clickedTileIndex: Int, emptyTileIndex: Int) {
        val tempTile = tiles[clickedTileIndex]
        tiles[clickedTileIndex] = tiles[emptyTileIndex]
        tiles[emptyTileIndex] = tempTile
    }

    public fun performTileSwap(clickedTileIndex: Int, emptyTileIndex: Int? = null) {
        if (canSwapTiles(clickedTileIndex)) {
            val actualEmptyTileIndex = emptyTileIndex ?: tiles.indexOfFirst { it.isEmpty }
            swapTiles(clickedTileIndex, actualEmptyTileIndex)
        }
    }

    fun isSolved(): Boolean {
        for (i in 0 until tiles.size - 1) {
            if (tiles[i].number != i + 1) {
                return false
            }
        }
        return true
    }
}