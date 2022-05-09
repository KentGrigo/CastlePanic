data class BoardPosition(
    val fieldNumber: Int,
    val fieldType: BoardFieldType,
) {
    fun moveInwards() =
        when (fieldType) {
            BoardFieldType.FOREST -> BoardPosition(fieldNumber, BoardFieldType.ARCHER)
            BoardFieldType.ARCHER -> BoardPosition(fieldNumber, BoardFieldType.KNIGHT)
            BoardFieldType.KNIGHT -> BoardPosition(fieldNumber, BoardFieldType.SWORDSMAN)
            BoardFieldType.SWORDSMAN -> BoardPosition(fieldNumber, BoardFieldType.CASTLE)// TODO: Check for barricades
            BoardFieldType.CASTLE -> BoardPosition(fieldNumber % 6 + 1, BoardFieldType.CASTLE)
        }

    fun moveClockwise() =
        BoardPosition(fieldNumber % 6 + 1, fieldType)

    fun moveCounterClockwise() =
        BoardPosition(Math.floorMod(fieldNumber - 2, 6) + 1, fieldType)

    override fun toString(): String {
        return "$fieldNumber $fieldType"
    }

    fun fieldNumberToColor() =
        when (fieldNumber) {
            1, 2 -> Color.RED
            3, 4 -> Color.GREEN
            5, 6 -> Color.BLUE
            else -> throw java.lang.IllegalStateException("The field number `$fieldNumber` is not allowed")
        }
}
