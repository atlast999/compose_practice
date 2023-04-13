package com.example.composepractice.ui.features

class Board(
    val cells: Array<Array<Cell>> = Array(5) {
        Array(5) {
            Cell()
        }
    },
) {
    init {
        cells[0][0].state = CellState.WHITE_PAWN_OCCUPIED
        cells[0][1].state = CellState.WHITE_PAWN_OCCUPIED
        cells[0][2].state = CellState.WHITE_KING_OCCUPIED
        cells[0][3].state = CellState.WHITE_PAWN_OCCUPIED
        cells[0][4].state = CellState.WHITE_PAWN_OCCUPIED
        cells[1][0].state = CellState.WHITE_PAWN_OCCUPIED
        cells[1][1].state = CellState.WHITE_PAWN_OCCUPIED
        cells[1][3].state = CellState.WHITE_PAWN_OCCUPIED
        cells[1][4].state = CellState.WHITE_PAWN_OCCUPIED
        cells[2][0].state = CellState.WHITE_PAWN_OCCUPIED

        cells[4][0].state = CellState.BLACK_PAWN_OCCUPIED
        cells[4][1].state = CellState.BLACK_PAWN_OCCUPIED
        cells[4][2].state = CellState.BLACK_KING_OCCUPIED
        cells[4][3].state = CellState.BLACK_PAWN_OCCUPIED
        cells[4][4].state = CellState.BLACK_PAWN_OCCUPIED
        cells[3][0].state = CellState.BLACK_PAWN_OCCUPIED
        cells[3][1].state = CellState.BLACK_PAWN_OCCUPIED
        cells[3][3].state = CellState.BLACK_PAWN_OCCUPIED
        cells[3][4].state = CellState.BLACK_PAWN_OCCUPIED
        cells[2][4].state = CellState.BLACK_PAWN_OCCUPIED
    }

    fun selectCell(row: Int, col: Int): Board {
        cells[row][col].apply {
            selected = !selected
        }
        return Board(cells = this.cells)
    }
}

enum class CellState {
    EMPTY,
    WHITE_PAWN_OCCUPIED,
    WHITE_KING_OCCUPIED,
    BLACK_PAWN_OCCUPIED,
    BLACK_KING_OCCUPIED
}

data class Cell(
    internal var state: CellState = CellState.EMPTY,
    internal var selected: Boolean = false,
)
