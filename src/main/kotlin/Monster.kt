import org.apache.commons.lang3.StringUtils

sealed class Monster(lives: Int) : Token {
    override fun toString(): String {
        return StringUtils.join(
            StringUtils.splitByCharacterTypeCamelCase(this.javaClass.simpleName),
            ' '
        )
    }
}

class Goblin : Monster(1)
class Orc : Monster(2)
class Troll : Monster(3)
class OrcWarlord : Monster(3)
class GoblinKing : Monster(3)
class Healer : Monster(3)
class TrollMage : Monster(3)
