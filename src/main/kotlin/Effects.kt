sealed class Effects : Token

class GiantBoulder : Effects()
class MonstersMoveClockwise : Effects()
class MonstersMoveCounterClockwise : Effects()
data class MonstersMove(val color: Color) : Effects()
class Plague(heroType: FighterType) : Effects()
data class DrawMonsters(val drawAmount: Int) : Effects()
class AllPlayersDiscard : Effects()
