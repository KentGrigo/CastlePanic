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
            if (color == boardPosition.fieldColor()) {
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

    fun placeBoulder(boulderOrigin: BoardPosition) {
        println("Rolling boulder from $boulderOrigin")
        var boulderPosition: BoardPosition? = boulderOrigin
        while (boulderPosition != null) {
            val killedMonsters = ArrayList<Monster>()
            for ((monster, monsterPosition) in monsterToBoardPosition.entries) {
                if (monsterPosition != boulderPosition) continue
                killedMonsters.add(monster)
            }
            for (killedMonster in killedMonsters) {
                println("Killed $killedMonster at ${monsterToBoardPosition[killedMonster]}")
                monsterToBoardPosition.remove(killedMonster)
            }
            boulderPosition = boulderPosition.moveForward(boulderOrigin)
        }
    }
}
