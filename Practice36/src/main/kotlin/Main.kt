fun main() {
    val encryptedMessage = "F2p)v\"y233{0->c}ttelciFc"
    val halfLength = encryptedMessage.length / 2
    val firstHalf = encryptedMessage.substring(0, halfLength)
    val secondHalf = encryptedMessage.substring(halfLength)

    val decryptedFirst = decryptFirstHalf(firstHalf)
    val decryptedSecond = decryptSecondHalf(secondHalf)

    println(decryptedFirst + decryptedSecond)
}

fun shift(str: String, transform: (Char) -> Char): String {
    return str.map(transform).joinToString("")
}

fun decryptFirstHalf(encrypted: String): String {
    var result = encrypted
    // Отмена шага 2.5 (сдвиг влево на 1 → сдвиг вправо на 1)
    result = shift(result) { it + 1 }
    // Отмена шага 2.4 (5 → s)
    result = result.replace('5', 's')
    // Отмена шага 2.3 (4 → u)
    result = result.replace('4', 'u')
    // Отмена шага 2.2 (сдвиг вправо на 3 → сдвиг влево на 3)
    result = shift(result) { it - 3 }
    // Отмена шага 2.1 (0 → o)
    result = result.replace('0', 'o')
    return result
}

fun decryptSecondHalf(encrypted: String): String {
    var result = encrypted
    // Отмена шага 3.3 (разворот)
    result = result.reversed()
    // Отмена шага 3.2 (сдвиг вправо на 4 → сдвиг влево на 4)
    result = shift(result) { it - 4 }
    // Отмена шага 3.1 (_ → пробел)
    result = result.replace('_', ' ')
    return result
}