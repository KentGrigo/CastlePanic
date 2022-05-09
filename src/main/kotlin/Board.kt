data class Board(
    val monsterToBoardPosition: MutableMap<Monster, BoardPosition> = HashMap(),
) {
    fun placeMonster(monster: Monster, boardPosition: BoardPosition) {
        monsterToBoardPosition[monster] = boardPosition
    }

    fun moveMonsters() {
        monsterToBoardPosition.forEach { (monster, boardPosition) ->
            monsterToBoardPosition[monster] = boardPosition.moveInwards()
        }
    }

    fun moveMonsters(color: Color) {
        monsterToBoardPosition.forEach { (monster, boardPosition) ->
            if (color == boardPosition.fieldNumberToColor()) {
                monsterToBoardPosition[monster] = boardPosition.moveInwards()
            }
        }
    }

    fun moveMonstersClockwise() {
        monsterToBoardPosition.forEach { (monster, boardPosition) ->
            monsterToBoardPosition[monster] = boardPosition.moveClockwise()
        }
    }

    fun moveMonstersCounterClockwise() {
        monsterToBoardPosition.forEach { (monster, boardPosition) ->
            monsterToBoardPosition[monster] = boardPosition.moveCounterClockwise()
        }
    }
}
