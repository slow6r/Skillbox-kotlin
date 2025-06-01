package stack

class Stack<T> {
    private val elements = mutableListOf<T>()

    fun push(item: T) {
        elements.add(item)
    }

    fun pop(): T? {
        if (elements.isEmpty()) {
            return null
        }
        return elements.removeAt(elements.size - 1)
    }

    fun isEmpty(): Boolean = elements.isEmpty()
}