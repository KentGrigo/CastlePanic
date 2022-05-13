import org.apache.commons.lang3.StringUtils

sealed class SpecialCard : Card {
    override fun toString(): String {
        return StringUtils.join(
            StringUtils.splitByCharacterTypeCamelCase(this.javaClass.simpleName),
            ' '
        )
    }
}

class Brick : SpecialCard()
class Mortar : SpecialCard()
class DrawCards(drawAmount: Int) : SpecialCard()
class NiceShot : SpecialCard()
class FortifyWall : SpecialCard()
class Scavenge : SpecialCard()
class DriveHimBack : SpecialCard()
class Missing : SpecialCard()
class Tar : SpecialCard()
class Barbarian : SpecialCard()
