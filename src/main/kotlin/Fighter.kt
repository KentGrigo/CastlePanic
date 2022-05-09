sealed class Fighter(fighterType: FighterType, vararg colors: Color) : Card

class Archer(vararg colors: Color) : Fighter(FighterType.ARCHER, *colors)
class Knight(vararg colors: Color) : Fighter(FighterType.KNIGHT, *colors)
class Swordsman(vararg colors: Color) : Fighter(FighterType.SWORDSMAN, *colors)
class Hero(vararg colors: Color) : Fighter(FighterType.HERO, *colors)
