package battle

sealed class BattleState {
    data class Progress(
        val team1Health: Int,
        val team1Alive: Int,
        val team2Health: Int,
        val team2Alive: Int
    ) : BattleState()

    object Team1Win : BattleState()
    object Team2Win : BattleState()
    object Draw : BattleState()
}