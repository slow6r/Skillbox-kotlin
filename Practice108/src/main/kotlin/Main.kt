class Stack<T> {
    private val items = mutableListOf<T>()

    fun push(item: T) {
        items.add(item)
    }

    fun pop(): T? {
        return if (isEmpty()) null else items.removeAt(items.size - 1)
    }

    fun isEmpty(): Boolean = items.isEmpty()

    override fun toString(): String = items.toString()
}

fun main() {
    val stack = Stack<Int>()
    println("Is empty: ${stack.isEmpty()}") // true
    stack.push(1)
    stack.push(2)
    stack.push(3)
    println("Pop: ${stack.pop()}") // 3
    println("Is empty: ${stack.isEmpty()}") // false
    println("Stack: $stack") // [1, 2]
}
