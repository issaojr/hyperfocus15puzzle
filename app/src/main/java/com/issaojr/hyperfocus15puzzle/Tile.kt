package com.issaojr.hyperfocus15puzzle

data class Tile(val number: Int) {
    val isEmpty get() = number == 0
}