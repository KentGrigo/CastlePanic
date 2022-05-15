open class Game(
    private val numberOfPlayers: Int,
    private val startingMonsters: MutableList<Monster>,
    private val monsterTokens: MutableList<Token>,
    private val cards: MutableList<Card>,

    private val board: Board = Board(),
    private val die: Die = Die(),
    private val players: ArrayList<Player> = ArrayList(),
    private val handLimit: Int = handLimit(numberOfPlayers),
) {
    companion object {
        private fun handLimit(numberOfPlayers: Int) =
            when (numberOfPlayers) {
                2 -> 6
                3, 4, 5 -> 5
                6 -> 4
                else -> throw java.lang.IllegalArgumentException("The number of players must be 2-6, but was: $numberOfPlayers.")
            }
    }

    fun play() {
        startingMonsters.shuffle()
        startingMonsters.forEachIndexed { index, monster ->
            board.placeMonster(monster, BoardPosition(index + 1, BoardFieldType.ARCHER))
        }
        for (fieldNumber in 1..6) {
            board.placeDefense(BoardPosition(fieldNumber, BoardFieldType.CASTLE))
        }

        monsterTokens.shuffle()
        cards.shuffle()
        for (playerNumber in 1..numberOfPlayers) {
            val player = Player("Player #$playerNumber", handLimit)
            drawCards(player)
            players.add(player)
        }

        var roundNumber = 0
        var playerTurnIndex = -1
        while (true) {
            roundNumber++
            playerTurnIndex = (playerTurnIndex + 1) % numberOfPlayers
            val player = players[playerTurnIndex]
            print(roundNumber, player)

            println()
            println("Drawing cards")
            drawCards(player)

            println()
            println("Moving monsters")
            board.moveMonsters()
            if (!board.hasDefense()) {
                println("Game over!")
                break
            }

            println()
            println("Drawing monster tokens")
            for (newMonsterNumber in 1..2) {
                drawMonsterToken()
            }
            if (!board.hasDefense()) {
                println("Game over!")
                break
            }
        }
    }

    private fun print(roundNumber: Int, currentPlayer: Player) {
        println()
        println("===============")
        println("Round #$roundNumber: ${currentPlayer.name}")

        println()
        println("Defense")
        board.boardPositionToDefense.forEach { (boardPosition, defense) ->
            println("$defense: $boardPosition")
        }

        players.forEach { player ->
            println()
            println("Cards of ${player.name}")
            for (card in player.cards) {
                println(card)
            }
        }

        println()
        board.monsterToBoardPosition.forEach { (monster, boardPosition) ->
            println("$monster: $boardPosition")
        }
    }

    private fun drawMonsterToken() {
        if (monsterTokens.isEmpty()) return

        val token = monsterTokens.removeFirst()
        println("Drew: $token")
        when (token) {
            is GiantBoulder -> board.placeBoulder(BoardPosition(die.roll(), BoardFieldType.FOREST))
            is MonstersMoveClockwise -> board.moveMonstersClockwise()
            is MonstersMoveCounterClockwise -> board.moveMonstersCounterClockwise()
            is MonstersMove -> board.moveMonsters(token.color)
            is Plague -> players.forEach { player -> player.loseFightersOfType(token.fighterType) }
            is DrawMonsters -> for (drawCount in 0 until token.drawAmount) {
                drawMonsterToken()
            }
            is AllPlayersDiscard -> Unit // TODO()
            is Monster -> {
                board.placeMonster(token, BoardPosition(die.roll(), BoardFieldType.FOREST))
                when (token) {
                    is OrcWarlord -> {
                        val color = board.monsterToBoardPosition[token]!!.fieldColor()
                        board.moveMonsters(color)
                    }
                    is GoblinKing -> for (drawCount in 0 until 3) {
                        drawMonsterToken()
                    }
                    is Healer -> board.monsterToBoardPosition.keys.forEach { monster ->
                        monster.heal()
                    }
                    is TrollMage -> board.moveMonsters()
                    else -> {}
                }
            }
        }
    }

    private fun drawCards(player: Player) {
        while (cards.isNotEmpty() && player.cards.size < handLimit) {
            val card = cards.removeFirst()
            println("Drew: $card")
            player.receiveCard(card)
        }
    }
}
