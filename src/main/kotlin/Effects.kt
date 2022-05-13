import org.apache.commons.lang3.StringUtils

sealed class Effects : Token {
    override fun toString(): String {
        return StringUtils.join(
            StringUtils.splitByCharacterTypeCamelCase(this.javaClass.simpleName),
            ' '
        )
    }
}

class GiantBoulder : Effects()
class MonstersMoveClockwise : Effects()
class MonstersMoveCounterClockwise : Effects()
data class MonstersMove(val color: Color) : Effects()
class Plague(heroType: FighterType) : Effects()
data class DrawMonsters(val drawAmount: Int) : Effects()
class AllPlayersDiscard : Effects()
