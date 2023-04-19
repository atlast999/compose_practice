package com.example.composepractice.ui.features

class Board(
    val cells: Array<Array<Cell>> = Array(BoardSize) { row ->
        Array(BoardSize) { col ->
            if (row.rem(2) == col.rem(2)) {
                Cell.EightDirectionCell(id = row to col)
            } else {
                Cell.FourDirectionCell(id = row to col)
            }
        }
    },
    selectedCell: Cell? = null,
    initPieces: Boolean = true,
    var currentTurn: Int = WhiteTurn,
) {
    companion object {
        const val BoardSize = 5
        const val WhiteTurn = 0
        const val BlackTurn = 1
    }

    var selectedCell: Cell? = selectedCell
        private set

    init {
        if (initPieces) {
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
    }

    fun selectCell(row: Int, col: Int): Board {
        val newSelectedCell = cells[row][col]
        if (newSelectedCell.hintState != CellState.EMPTY) {
            this.selectedCell ?: return this
            removeHint()
            newSelectedCell.state = this.selectedCell!!.state
            this.selectedCell!!.state = CellState.EMPTY
            this.selectedCell = null
            this.currentTurn = if (this.currentTurn == WhiteTurn) BlackTurn else WhiteTurn
            return this.copy()
        }
        if (newSelectedCell.state.type != currentTurn) return this
        when (selectedCell?.id) {
            newSelectedCell.id -> {
                selectedCell = null
                removeHint(newSelectedCell)
            }
            else -> {
                if (selectedCell != null) {
                    removeHint()
                }
                selectedCell = newSelectedCell
                showHint(newSelectedCell)
            }
        }
        return this.copy()
    }

    private fun removeHint(
        cell: Cell? = selectedCell,
        movements: List<Pair<Int, Int>> = cell?.getPossibleMovements() ?: emptyList()
    ) {
        cell ?: return
        movements.forEach { (offsetX, offsetY) ->
            val movableCell =
                this.cells[cell.id.first + offsetX][cell.id.second + offsetY]
            movableCell.hintState = CellState.EMPTY
        }
    }

    private fun showHint(
        cell: Cell? = this.selectedCell,
        movements: List<Pair<Int, Int>> = cell?.getPossibleMovements() ?: emptyList()
    ) {
        cell ?: return
        movements.forEach { (offsetX, offsetY) ->
            val movableCell =
                this.cells[cell.id.first + offsetX][cell.id.second + offsetY]
            movableCell.hintState = cell.state
        }
    }

    private fun Cell.getPossibleMovements(): List<Pair<Int, Int>> {
        val movements = when (this) {
            is Cell.FourDirectionCell -> arrayOf(
                CellMovement.Up,
                CellMovement.Down,
                CellMovement.Left,
                CellMovement.Right,
            )
            is Cell.EightDirectionCell -> CellMovement.values()
        }
        val validRange = 0 until BoardSize
        return movements.filter { move ->
            val x = this.id.first + move.direction.first
            val y = this.id.second + move.direction.second
            if (x !in validRange || y !in validRange) return@filter false
            if (this@Board.cells[x][y].state != CellState.EMPTY) return@filter false
            true
        }.map { it.direction } + this.getCapturingMovements()
    }

    private fun Cell.getCapturingMovements(): List<Pair<Int, Int>> {
        if (this.state !in setOf(
                CellState.WHITE_KING_OCCUPIED,
                CellState.BLACK_KING_OCCUPIED
            )
        ) return emptyList()
        val movements = when (this) {
            is Cell.FourDirectionCell -> arrayOf(
                CellMovement.Up,
                CellMovement.Down,
                CellMovement.Left,
                CellMovement.Right,
            )
            is Cell.EightDirectionCell -> CellMovement.values()
        }
        val validRange = 0 until BoardSize
        return movements.filter { move ->
            val xHelper = this.id.first + move.direction.first
            val yHelper = this.id.second + move.direction.second
            if (xHelper !in validRange || yHelper !in validRange) return@filter false
            if (this@Board.cells[xHelper][yHelper].state.type != this.state.type) return@filter false
            val xTarget = this.id.first + move.direction.first * 2
            val yTarget = this.id.second + move.direction.second * 2
            if (xTarget !in validRange || yTarget !in validRange) return@filter false
            if (this@Board.cells[xTarget][yTarget].state == CellState.EMPTY) return@filter false
            if (this@Board.cells[xTarget][yTarget].state.type == this.state.type) return@filter false
            true
        }.map { it.direction.first.times(2) to it.direction.second.times(2) }
    }

    private fun copy(
        cells: Array<Array<Cell>> = this.cells,
        selectedCell: Cell? = this.selectedCell,
    ) = Board(
        cells = cells,
        selectedCell = selectedCell,
        initPieces = false,
        currentTurn = this.currentTurn,
    )

}

enum class CellMovement(val direction: Pair<Int, Int>) {
    Up(-1 to 0),
    Down(1 to 0),
    Left(0 to -1),
    Right(0 to 1),
    UpLeft(-1 to -1),
    UpRight(-1 to 1),
    DownLeft(1 to -1),
    DownRight(1 to 1),
}

enum class CellState(val type: Int) {
    EMPTY(-1),
    WHITE_PAWN_OCCUPIED(Board.WhiteTurn),
    WHITE_KING_OCCUPIED(Board.WhiteTurn),
    BLACK_PAWN_OCCUPIED(Board.BlackTurn),
    BLACK_KING_OCCUPIED(Board.BlackTurn),
}

sealed class Cell(
    val id: Pair<Int, Int>,
    var state: CellState = CellState.EMPTY,
    var hintState: CellState = CellState.EMPTY,
) {
    class FourDirectionCell(id: Pair<Int, Int>) : Cell(id)
    class EightDirectionCell(id: Pair<Int, Int>) : Cell(id)
}
