import org.apache.commons.lang3.StringUtils
import kotlin.math.max
import kotlin.math.min

sealed class Monster(
    private val maxNumberOfLives: Int,
    var currentNumberOfLives: Int = maxNumberOfLives,
) : Token {
    fun damage() {
        currentNumberOfLives = max(0, currentNumberOfLives - 1)
    }

    fun heal() {
        currentNumberOfLives = min(currentNumberOfLives + 1, maxNumberOfLives)
    }

    override fun toString(): String {
        return StringUtils.join(
            StringUtils.splitByCharacterTypeCamelCase(this.javaClass.simpleName),
            ' '
        ) + " ($currentNumberOfLives/$maxNumberOfLives)"
    }
}

class Goblin : Monster(1)
class Orc : Monster(2)
class Troll : Monster(3)
class OrcWarlord : Monster(3)
class GoblinKing : Monster(3)
class Healer : Monster(3)
class TrollMage : Monster(3)
