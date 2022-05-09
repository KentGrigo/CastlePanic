import kotlin.random.Random

class Die {
    fun roll(): Int =
        Random.nextInt(6) + 1
}
