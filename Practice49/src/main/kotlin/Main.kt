fun main() {
    val n = getValidN()
    val result = fibonacci(n)
    println("\nF($n) = $result")
}

fun getValidN(): Int {
    while (true) {
        print("Введите целое число n > 0: ")
        val input = readlnOrNull()
        val number = input?.toIntOrNull()
        if (number != null && number > 0) {
            return number
        } else {
            println("Ошибка ввода. Пожалуйста, введите целое число больше 0.")
        }
    }
}

fun fibonacci(n: Int): Int {
    if (n == 1) return 0
    if (n == 2) return 1

    var a = 0
    var b = 1
    for (i in 3..n) {
        val next = a + b
        a = b
        b = next
    }
    return b
}