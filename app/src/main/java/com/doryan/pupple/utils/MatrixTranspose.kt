package com.doryan.pupple.utils

fun <T> matrixTranspose(matrix: List<List<T>>): List<List<T>> =
        matrix.first().mapIndexed { i, _ ->
            matrix.map { row -> row[i] }
        }