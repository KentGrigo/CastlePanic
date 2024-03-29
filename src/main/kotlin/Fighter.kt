import org.apache.commons.lang3.StringUtils

sealed class Fighter(val fighterType: FighterType, private vararg val colors: Color) : Card {
    override fun toString(): String {
        return StringUtils.join(
            StringUtils.splitByCharacterTypeCamelCase(this.javaClass.simpleName),
            ' '
        ) + " " + this.colors.map { it.name }
    }
}

class Archer(vararg colors: Color) : Fighter(FighterType.ARCHER, *colors)
class Knight(vararg colors: Color) : Fighter(FighterType.KNIGHT, *colors)
class Swordsman(vararg colors: Color) : Fighter(FighterType.SWORDSMAN, *colors)
class Hero(vararg colors: Color) : Fighter(FighterType.HERO, *colors)
