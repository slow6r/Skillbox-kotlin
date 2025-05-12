fun main() {
    val n = getValidatedInput()
    val fibNumber = calculateFibonacci(n)
    println("Число Фибоначчи для n=$n равно $fibNumber")
}

fun getValidatedInput(): Int {
    while (true) {
        print("Введите целое число n > 0: ")
        val input = readlnOrNull() ?: continue
        try {
            val n = input.toInt()
            if (n > 0) {
                return n
            } else {
                println("Ошибка: число должно быть больше 0. Попробуйте снова.")
            }
        } catch (e: NumberFormatException) {
            println("Ошибка: введено не целое число. Попробуйте снова.")
        }
    }
}

fun calculateFibonacci(n: Int): Int {
    var prevPrev = 0
    var prev = 1
    for (i in 1 until n) {
        val current = prevPrev + prev
        prevPrev = prev
        prev = current
    }
    return prev
}