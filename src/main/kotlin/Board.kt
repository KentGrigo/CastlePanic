data class Board(
    val monsterToBoardPosition: MutableMap<Monster, BoardPosition> = HashMap(),
    val boardPositionToDefense: MutableMap<BoardPosition, Defense> = LinkedHashMap(),
) {
    fun placeMonster(monster: Monster, boardPosition: BoardPosition) {
        monsterToBoardPosition[monster] = boardPosition
    }

    fun moveMonsters() {
        val killedMonsters = ArrayList<Monster>()
        monsterToBoardPosition.forEach { (monster, oldPosition) ->
            val newPosition = oldPosition.moveInwards()
            val defense = boardPositionToDefense[newPosition]
            if (defense == null || !defense.hasStructure()) {
                monsterToBoardPosition[monster] = newPosition
            } else if (!defense.hasWall() || oldPosition.fieldType == BoardFieldType.CASTLE) {
                monsterToBoardPosition[monster] = newPosition
                if (defense.towerState == DefenseState.STANDING) {
                    defense.towerState = DefenseState.NONE
                    monster.damage()
                    if (monster.currentNumberOfLives <= 0) {
                        killedMonsters.add(monster)
                    }
                }
            } else if (defense.fortifiedWallState == DefenseState.STANDING) {
                defense.fortifiedWallState = DefenseState.BREAKING
                monster.damage()
                if (monster.currentNumberOfLives <= 0) {
                    killedMonsters.add(monster)
                }
            } else if (defense.fortifiedWallState == DefenseState.BREAKING) {
                // Do nothing
            } else if (defense.wallState == DefenseState.STANDING) {
                defense.wallState = DefenseState.BREAKING
                monster.damage()
                if (monster.currentNumberOfLives <= 0) {
                    killedMonsters.add(monster)
                }
            }
        }
        for (killedMonster in killedMonsters) {
            println("Killed $killedMonster at ${monsterToBoardPosition[killedMonster]}")
            monsterToBoardPosition.remove(killedMonster)
        }
        for (defense in boardPositionToDefense.values) {
            if (defense.wallState == DefenseState.BREAKING) {
                defense.wallState = DefenseState.NONE
            }
            if (defense.fortifiedWallState == DefenseState.BREAKING) {
                defense.fortifiedWallState = DefenseState.NONE
            }
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

    fun placeDefense(boardPosition: BoardPosition) {
        boardPositionToDefense[boardPosition] = Defense()
    }

    fun hasDefense(): Boolean =
        boardPositionToDefense.values.any { it.towerState == DefenseState.STANDING }

    fun placeBoulder(boulderOrigin: BoardPosition) {
        println("Rolling boulder from $boulderOrigin")
        var boulderPosition: BoardPosition? = boulderOrigin
        while (boulderPosition != null) {
            val defense = boardPositionToDefense[boulderPosition]
            if (defense == null || !defense.hasStructure()) {
                rollOverMonsterWithBoulder(boulderPosition)
                boulderPosition = boulderPosition.moveForward(boulderOrigin)
            } else {
                rollIntoDefenseWithBoulder(boulderOrigin, boulderPosition, defense)
                break
            }
        }
    }

    private fun rollOverMonsterWithBoulder(boulderPosition: BoardPosition) {
        val killedMonsters = ArrayList<Monster>()
        for ((monster, monsterPosition) in monsterToBoardPosition.entries) {
            if (monsterPosition != boulderPosition) continue
            killedMonsters.add(monster)
        }
        for (killedMonster in killedMonsters) {
            println("Killed $killedMonster at ${monsterToBoardPosition[killedMonster]}")
            monsterToBoardPosition.remove(killedMonster)
        }
    }

    private fun rollIntoDefenseWithBoulder(
        boulderOrigin: BoardPosition,
        boulderPosition: BoardPosition,
        defense: Defense,
    ) {
        if (boulderOrigin.fieldNumber == boulderPosition.fieldNumber) {
            when {
                defense.fortifiedWallState != DefenseState.NONE -> defense.fortifiedWallState = DefenseState.NONE
                defense.wallState != DefenseState.NONE -> defense.wallState = DefenseState.NONE
                defense.towerState != DefenseState.NONE -> defense.towerState = DefenseState.NONE
            }
        } else {
            when {
                defense.towerState != DefenseState.NONE -> defense.towerState = DefenseState.NONE
                defense.fortifiedWallState != DefenseState.NONE -> defense.fortifiedWallState = DefenseState.NONE
                defense.wallState != DefenseState.NONE -> defense.wallState = DefenseState.NONE
            }
        }
    }
}
