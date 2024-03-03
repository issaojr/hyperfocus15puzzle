package com.issaojr.hyperfocus15puzzle

import androidx.lifecycle.ViewModel

class PuzzleViewModel : ViewModel() {

    private val puzzleState = PuzzleState(4) // Inicializa o estado do jogo com 4x4 blocos

    fun onTileClick(clickedTileIndex: Int) {
        val emptyTileIndex = puzzleState.tiles.indexOfFirst { it.isEmpty }
        // Verifica se o bloco clicado é adjacente ao bloco vazio
        if (puzzleState.canSwapTiles(clickedTileIndex)) {
            // Troca os blocos clicado e vazio
            puzzleState.performTileSwap(clickedTileIndex, emptyTileIndex)

            // Verifica se o jogo foi finalizado
            if (puzzleState.isSolved()) {
                // Exibe um diálogo de vitória
                // ...
            }
        }
    }

    fun getPuzzleState(): PuzzleState {
        return puzzleState
    }
}
