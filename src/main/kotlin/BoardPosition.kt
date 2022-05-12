data class BoardPosition(
    val fieldNumber: Int,
    val fieldType: BoardFieldType,
) {
    // This method is used for boulders, rolling straight.
    fun moveForward(origin: BoardPosition) =
        if (fieldNumber == origin.fieldNumber) {
            // Rolling in
            when (fieldType) {
                BoardFieldType.FOREST -> BoardPosition(fieldNumber, BoardFieldType.ARCHER)
                BoardFieldType.ARCHER -> BoardPosition(fieldNumber, BoardFieldType.KNIGHT)
                BoardFieldType.KNIGHT -> BoardPosition(fieldNumber, BoardFieldType.SWORDSMAN)
                // TODO: Check for barricades and towers
                BoardFieldType.SWORDSMAN -> BoardPosition(fieldNumber, BoardFieldType.CASTLE)
                // TODO: Check for towers
                BoardFieldType.CASTLE -> BoardPosition((fieldNumber + 2) % 6 + 1, BoardFieldType.CASTLE)
            }
        } else {
            // Rolling out
            when (fieldType) {
                BoardFieldType.FOREST -> null
                BoardFieldType.ARCHER -> BoardPosition(fieldNumber, BoardFieldType.FOREST)
                BoardFieldType.KNIGHT -> BoardPosition(fieldNumber, BoardFieldType.ARCHER)
                BoardFieldType.SWORDSMAN -> BoardPosition(fieldNumber, BoardFieldType.KNIGHT)
                // TODO: Check for barricades
                BoardFieldType.CASTLE -> BoardPosition(fieldNumber, BoardFieldType.SWORDSMAN)
            }
        }

    // This method is used for monsters, walking towards the castle and ravaging it from the inside.
    fun moveInwards() =
        when (fieldType) {
            BoardFieldType.FOREST -> BoardPosition(fieldNumber, BoardFieldType.ARCHER)
            BoardFieldType.ARCHER -> BoardPosition(fieldNumber, BoardFieldType.KNIGHT)
            BoardFieldType.KNIGHT -> BoardPosition(fieldNumber, BoardFieldType.SWORDSMAN)
            // TODO: Check for barricades and towers
            BoardFieldType.SWORDSMAN -> BoardPosition(fieldNumber, BoardFieldType.CASTLE)
            // TODO: Check for towers
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
