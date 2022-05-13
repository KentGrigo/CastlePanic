data class Player(
    val handLimit: Int,
    val cards: MutableList<Card> = ArrayList(),
) {
    fun receiveCard(card: Card) {
        cards.add(card)
    }
}
