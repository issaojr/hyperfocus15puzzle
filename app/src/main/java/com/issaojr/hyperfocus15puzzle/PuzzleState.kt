package com.issaojr.hyperfocus15puzzle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

class PuzzleState(val gridSize: Int) {
    private val initialTiles = List(gridSize * gridSize - 1) { Tile(it + 1) } + Tile(0)
    val tiles = mutableStateListOf(*List(gridSize * gridSize - 1) { Tile(it + 1) }.toTypedArray(), Tile(0))
    var movesCounter: Int by mutableIntStateOf(0)


    init {
        shuffle()
    }

    private fun shuffle() {

        repeat(300) { // Make 300 random moves to shuffle the tiles
            while (true) {
                val index = (0..15).random()
                if(canMove(index)) {
                    move(index)
                    break
                }
            }
        }
    }

    public fun canMove(index: Int): Boolean {
        val emptyTileIndex = tiles.indexOfFirst { it.isEmpty }

        return (index == emptyTileIndex - 1 && index % gridSize != gridSize - 1) || // à esquerda
                (index == emptyTileIndex + 1 && index % gridSize != 0) || // à direita
                index == emptyTileIndex - gridSize || // acima
                index == emptyTileIndex + gridSize // abaixo
    }

    public fun move(index: Int){
        val emptyTileIndex = tiles.indexOfFirst { it.isEmpty }
        tiles[emptyTileIndex] = tiles[index].also { tiles[index] = tiles[emptyTileIndex] }
    }

    public fun isGameFinished(): Boolean {
        return tiles.zip(initialTiles).all { (tile, initialTile) -> tile.number == initialTile.number }
    }

    public fun incrementMovesCounter() {
        movesCounter++
    }

    fun reset() {
        tiles.clear()
        tiles.addAll(List(gridSize * gridSize - 1) { Tile(it + 1) } + Tile(0))
        shuffle()
        movesCounter = 0
    }
}