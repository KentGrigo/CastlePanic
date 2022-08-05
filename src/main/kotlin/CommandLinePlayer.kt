import java.util.*

data class CommandLinePlayer(
    override val name: String,
    override val handLimit: Int,
    override val cards: MutableList<Card> = ArrayList(),
    val scanner: Scanner = Scanner(System.`in`),
) : Player(name, handLimit, cards) {
    override fun action(): PlayerAction {
        while (true) {
            print("INPUT> ")
            val input = scanner.nextLine().trim()
            when (input) {
                "END" -> return EndPhaseAction()
                "Play 1", "Play 2", "Play 3", "Play 4", "Play 5", "Play 6" -> {
                    val cardIndex = input.cardIndex()
                    return PlayAction(cardIndex)
                }
                "Discard 1", "Discard 2", "Discard 3", "Discard 4", "Discard 5", "Discard 6" -> {
                    val cardIndex = input.cardIndex()
                    return DiscardAction(cardIndex)
                }
                else -> println("No match")
            }
        }
    }

    private fun String.cardIndex(): Int {
        val stringIndex = length - 1
        val cardNumber = substring(stringIndex).toInt()
        return cardNumber - 1
    }
}
