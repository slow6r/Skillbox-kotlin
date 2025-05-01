fun main() {
    // Неизменяемые переменные (val)
    val firstName: String = "Иван"
    val lastName: String = "Иванов"
    val weight: Float = 38.5f

    // Изменяемые переменные (var)
    var height: Double = 1.45

    // Вычисляемое значение переменной isChild
    var isChild: Boolean = height < 1.5 || weight < 40

    // Строка с информацией о пользователе
    var info = """
        Имя: $firstName
        Фамилия: $lastName
        Рост: $height м
        Вес: $weight кг
        Ребёнок: $isChild
    """.trimIndent()

    // Первый вывод
    println("Вывод информации 1:")
    println(info)

    // Изменим рост
    height = 1.7

    // Обновим переменные, зависящие от роста
    isChild = height < 1.5 || weight < 40
    info = """
        Имя: $firstName
        Фамилия: $lastName
        Рост: $height м
        Вес: $weight кг
        Ребёнок: $isChild
    """.trimIndent()

    // Второй вывод
    println("\nВывод информации 2:")
    println(info)
}
