sealed class PlayerAction

class DiscardAction(val cardIndex: Int) : PlayerAction()
class TradeAction : PlayerAction()
class PlayAction(val cardIndex: Int) : PlayerAction()
class EndPhaseAction : PlayerAction()
