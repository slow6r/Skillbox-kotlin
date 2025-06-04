import kotlinx.coroutines.*
import java.math.BigInteger
import kotlin.coroutines.coroutineContext

object Fibonacci {
    suspend fun take(n: Int): BigInteger {
        if (n <= 0) return BigInteger.ZERO
        if (n == 1) return BigInteger.ONE

        var prev = BigInteger.ZERO
        var current = BigInteger.ONE

        for (i in 2..n) {
            // Проверяем, активна ли корутина
            if (!coroutineContext.isActive) {
                throw CancellationException("Вычисление прервано")
            }
            
            // Делаем паузу для возможности отмены
            yield()
            
            val next = prev.add(current)
            prev = current
            current = next
        }
        
        return current
    }
}

suspend fun printProgress() {
    while (true) {
        print(".")
        delay(100)
    }
}

fun main() = runBlocking {
    try {
        // Запускаем индикатор прогресса
        val progressJob = launch {
            printProgress()
        }

        // Создаем список для хранения результатов
        val results = mutableListOf<Deferred<BigInteger>>()

        // Запускаем вычисления по возрастанию
        results.add(async { Fibonacci.take(5) })
        results.add(async { Fibonacci.take(10) })
        results.add(async { Fibonacci.take(15) })

        // Запускаем вычисления по убыванию
        results.add(async { Fibonacci.take(20) })
        results.add(async { Fibonacci.take(15) })
        results.add(async { Fibonacci.take(10) })

        // Ждем завершения всех вычислений
        results.forEach { deferred ->
            try {
                withTimeout(3000) {
                    val result = deferred.await()
                    println("\nРезультат: $result")
                }
            } catch (e: TimeoutCancellationException) {
                println("\nПревышено время вычисления")
            }
        }

        // Отменяем индикатор прогресса
        progressJob.cancel()
        
    } catch (e: Exception) {
        println("\nПроизошла ошибка: ${e.message}")
    }
} 