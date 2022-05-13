data class Game(
    val numberOfPlayers: Int,
) {
    private val startingMonsters = mutableListOf(
        // 3/6 Goblins
        Goblin(), Goblin(), Goblin(),
        // 2/11 Orcs
        Orc(), Orc(),
        // 1/10 Trolls
        Troll(),
    )

    private val monsterTokens = mutableListOf(
        // 3/6 Goblins
        Goblin(), Goblin(), Goblin(),
        // 9/11 Orcs
        Orc(), Orc(), Orc(), Orc(), Orc(), Orc(), Orc(), Orc(), Orc(),
        // 9/10 Trolls
        Troll(), Troll(), Troll(), Troll(), Troll(), Troll(), Troll(), Troll(), Troll(),
        // 1 Orc Warlord
        OrcWarlord(),
        // 1 Goblin King
        GoblinKing(),
        // 1 Healer
        Healer(),
        // 1 Troll Mage
        TrollMage(),

        // 4 Giant Boulders
        GiantBoulder(), GiantBoulder(), GiantBoulder(), GiantBoulder(),

        // 1 Monster Move Clockwise
        MonstersMoveClockwise(),
        // 1 Monster Move Counter-Clockwise
        MonstersMoveCounterClockwise(),
        // 2 Green Monster Move 1
        MonstersMove(Color.GREEN), MonstersMove(Color.GREEN),
        // 2 Blue Monster Move 1
        MonstersMove(Color.BLUE), MonstersMove(Color.BLUE),
        // 2 Red Monster Move 1
        MonstersMove(Color.RED), MonstersMove(Color.RED),
        // 1 Plague: Archers
        Plague(FighterType.ARCHER),
        // 1 Plague: Knights
        Plague(FighterType.KNIGHT),
        // 1 Plague: Swordsmen
        Plague(FighterType.SWORDSMAN),
        // 1 Draw 3 Monster Tokens
        DrawMonsters(3),
        // 1 Draw 4 Monster Tokens
        DrawMonsters(4),
        // 1 All Players Discard 1 Card
        AllPlayersDiscard(),
    )

    private val cards = mutableListOf(
        // 1 Any Color Archer
        Archer(*Color.values()),
        // 1 Any Color Knight
        Knight(*Color.values()),
        // 1 Any Color Swordsman
        Swordsman(*Color.values()),

        // GREEN
        // 1 Green Hero
        Hero(Color.GREEN),
        // 3 Green Archers
        Archer(Color.GREEN), Archer(Color.GREEN), Archer(Color.GREEN),
        // 3 Green Knights
        Knight(Color.GREEN), Knight(Color.GREEN), Knight(Color.GREEN),
        // 3 Green Swordsmen
        Swordsman(Color.GREEN), Swordsman(Color.GREEN), Swordsman(Color.GREEN),

        // BLUE
        // 1 Blue Hero
        Hero(Color.BLUE),
        // 3 Blue Archers
        Archer(Color.BLUE), Archer(Color.BLUE), Archer(Color.BLUE),
        // 3 Blue Knights
        Knight(Color.BLUE), Knight(Color.BLUE), Knight(Color.BLUE),
        // 3 Blue Swordsmen
        Swordsman(Color.BLUE), Swordsman(Color.BLUE), Swordsman(Color.BLUE),

        // RED
        // 1 Red Hero
        Hero(Color.RED),
        // 3 Red Archers
        Archer(Color.RED), Archer(Color.RED), Archer(Color.RED),
        // 3 Red Knights
        Knight(Color.RED), Knight(Color.RED), Knight(Color.RED),
        // 3 Red Swordsmen
        Swordsman(Color.RED), Swordsman(Color.RED), Swordsman(Color.RED),

        // 4 Brick
        Brick(), Brick(), Brick(), Brick(),
        // 4 Mortar
        Mortar(), Mortar(), Mortar(), Mortar(),
        // 1 Draw 2 Cards
        DrawCards(2),
        // 1 Nice Shot
        NiceShot(),
        // 1 Fortify Wall
        FortifyWall(),
        // 1 Scavenge
        Scavenge(),
        // 1 Drive Him Back
        DriveHimBack(),
        // 1 Missing
        Missing(),
        // 1 Tar
        Tar(),
        // 1 Barbarian
        Barbarian(),
    )

    private val board = Board()
    private val die = Die()
    private val players = ArrayList<Player>()
    private val handLimit = handLimit()

    private fun handLimit() =
        when (numberOfPlayers) {
            2 -> 6
            3, 4, 5 -> 5
            6 -> 4
            else -> throw java.lang.IllegalArgumentException("The number of players must be 2-6, but was: $numberOfPlayers.")
        }

    fun play() {
        startingMonsters.shuffle()
        startingMonsters.forEachIndexed { index, monster ->
            board.placeMonster(monster, BoardPosition(index + 1, BoardFieldType.ARCHER))
        }

        monsterTokens.shuffle()
        cards.shuffle()
        for (playerNumber in 0 until numberOfPlayers) {
            val player = Player(handLimit)
            drawCards(player)
            players.add(player)
        }

        var roundNumber = 0
        var playerTurnIndex = -1
        while (true) {
            roundNumber++
            playerTurnIndex = (playerTurnIndex + 1) % numberOfPlayers
            print(roundNumber, playerTurnIndex)

            val player = players[playerTurnIndex]
            drawCards(player)
            board.moveMonsters()
            for (newMonsterNumber in 1..2) {
                drawMonsterToken()
            }
        }
    }

    private fun print(roundNumber: Int, playerTurnIndex: Int) {
        println()
        println("===============")
        println("Round #$roundNumber: Player #${playerTurnIndex + 1}")

        players.forEachIndexed { index, player ->
            println()
            println("Cards of player #${index + 1}")
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
            is Plague -> {
                players.forEachIndexed { index, player ->
                    player.cards.removeAll { card ->
                        if (card !is Fighter) return@removeAll false
                        val hasPlague = card.fighterType == token.fighterType
                        if (hasPlague) println("Player #${index + 1} lost $card because of plague")
                        hasPlague
                    }
                }
            }
            is DrawMonsters -> for (monsterCount in 0 until token.drawAmount) {
                drawMonsterToken()
            }
            is AllPlayersDiscard -> Unit // TODO()
            is Monster -> board.placeMonster(token, BoardPosition(die.roll(), BoardFieldType.FOREST))
        }
    }

    private fun drawCards(player: Player) {
        while (cards.isNotEmpty() && player.cards.size < handLimit) {
            player.receiveCard(cards.removeFirst())
        }
    }
}
