data class Player(
    val name: String,
    val handLimit: Int,
    val cards: MutableList<Card> = ArrayList(),
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
}
