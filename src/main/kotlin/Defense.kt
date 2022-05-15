data class Defense(
    var towerState: DefenseState = DefenseState.STANDING,
    var wallState: DefenseState = DefenseState.STANDING,
    var fortifiedWallState: DefenseState = DefenseState.NONE,
) {
    fun hasWall(): Boolean =
        fortifiedWallState != DefenseState.NONE || wallState != DefenseState.NONE

    fun hasStructure(): Boolean =
        fortifiedWallState != DefenseState.NONE || wallState != DefenseState.NONE || towerState != DefenseState.NONE
}
