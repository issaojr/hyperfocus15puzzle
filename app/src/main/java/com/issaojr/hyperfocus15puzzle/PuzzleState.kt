package com.issaojr.hyperfocus15puzzle

import androidx.compose.runtime.mutableStateListOf
import kotlin.random.Random

class PuzzleState(val gridSize: Int) {
    val tiles = mutableStateListOf(*List(gridSize * gridSize) { Tile(it) }.toTypedArray())

    init {
        shuffle()
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
}