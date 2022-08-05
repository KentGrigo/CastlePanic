abstract class Player(
    open val name: String,
    open val handLimit: Int,
    open val cards: MutableList<Card> = ArrayList(),
) {
    fun receiveCard(card: Card) {
        cards.add(card)
    }

    fun loseFightersOfType(fighterType: FighterType) {
        cards.removeAll { card ->
            if (card !is Fighter) return@removeAll false
            val hasPlague = card.fighterType == fighterType
            if (hasPlague) println("$name lost $card because of plague")
            hasPlague
        }
    }

    abstract fun action(): PlayerAction
}
