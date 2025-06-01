// Абстрактный класс BankCard
abstract class BankCard {
    protected var balance: Double = 0.0

    // Абстрактные методы, которые должны быть реализованы в наследниках
    abstract fun topUp(amount: Double)
    abstract fun pay(amount: Double): Boolean
    abstract fun getBalanceInfo(): String
    abstract fun getAvailableFundsInfo(): String
}

// Класс DebitCard (наследуется от BankCard)
open class DebitCard : BankCard() {
    // Бонусные баллы (1% от покупок)
    protected var bonusPoints: Double = 0.0

    override fun topUp(amount: Double) {
        if (amount > 0) {
            balance += amount
            println("Счет пополнен на $amount. Текущий баланс: $balance")
        } else {
            println("Сумма пополнения должна быть положительной")
        }
    }

    override fun pay(amount: Double): Boolean {
        if (amount > 0 && balance >= amount) {
            balance -= amount
            bonusPoints += amount * 0.01 // Начисляем 1% бонусов от покупки
            println("Оплата $amount прошла успешно. Остаток: $balance")
            return true
        }
        println("Оплата $amount не удалась. Недостаточно средств")
        return false
    }

    override fun getBalanceInfo(): String {
        return "Баланс: $balance"
    }

    override fun getAvailableFundsInfo(): String {
        return "Доступные средства:\nБаланс: $balance\nБонусные баллы: $bonusPoints"
    }
}

// Класс CreditCard (наследуется от BankCard)
open class CreditCard(private val creditLimit: Double) : BankCard() {
    private var creditFunds: Double = creditLimit
    // Потенциальный кэшбэк (5% от покупок при тратах больше 5000)
    protected var potentialCashback: Double = 0.0

    override fun topUp(amount: Double) {
        if (amount > 0) {
            // Сначала погашаем кредитные средства

            val remainingCredit = creditLimit - creditFunds
            if (remainingCredit > 0) {
                if (amount <= remainingCredit) {
                    creditFunds += amount
                    println("Кредитные средства пополнены на $amount")
                } else {
                    creditFunds = creditLimit
                    balance += (amount - remainingCredit)
                    println("Кредитные средства полностью погашены. Собственные средства пополнены на ${amount - remainingCredit}")
                }
            } else {
                balance += amount
                println("Собственные средства пополнены на $amount")
            }
        } else {
            println("Сумма пополнения должна быть положительной")
        }
    }

    override fun pay(amount: Double): Boolean {
        if (amount > 0) {
            // Сначала списываем собственные средства, затем кредитные
            if (balance >= amount) {
                balance -= amount
            } else {
                val remainingAmount = amount - balance
                balance = 0.0
                if (creditFunds >= remainingAmount) {
                    creditFunds -= remainingAmount
                } else {
                    println("Оплата $amount не удалась. Недостаточно средств")
                    return false
                }
            }

            // Начисляем потенциальный кэшбэк (5% при тратах больше 5000)
            if (amount > 5000) {
                potentialCashback += amount * 0.05
            }

            println("Оплата $amount прошла успешно")
            return true
        }
        println("Сумма оплаты должна быть положительной")
        return false
    }

    override fun getBalanceInfo(): String {
        return "Собственные средства: $balance, Кредитные средства: $creditFunds"
    }

    override fun getAvailableFundsInfo(): String {
        return "Доступные средства:\nСобственные: $balance\nКредитные: $creditFunds\nПотенциальный кэшбэк: $potentialCashback"
    }
}

// Производный класс от DebitCard с дополнительными бонусами
class PremiumDebitCard : DebitCard() {
    // Накопление 0.005% от суммы пополнений
    private var savings: Double = 0.0

    override fun topUp(amount: Double) {
        super.topUp(amount)
        savings += amount * 0.00005
    }

    override fun getAvailableFundsInfo(): String {
        return super.getAvailableFundsInfo() + "\nНакопления: $savings"
    }
}

// Производный класс от CreditCard с дополнительными бонусами
class GoldCreditCard(creditLimit: Double) : CreditCard(creditLimit) {
    // Дополнительные бонусные баллы
    private var additionalBonusPoints: Double = 0.0

    override fun pay(amount: Double): Boolean {
        val result = super.pay(amount)
        if (result) {
            additionalBonusPoints += amount * 0.02 // Дополнительные 2% бонусов
        }
        return result
    }

    override fun getAvailableFundsInfo(): String {
        return super.getAvailableFundsInfo() + "\nДополнительные бонусные баллы: $additionalBonusPoints"
    }
}

fun main() {
    // Тестирование DebitCard
    println("=== Тестирование DebitCard ===")
    val debitCard = DebitCard()
    debitCard.topUp(10000.0)
    debitCard.pay(5000.0)
    println(debitCard.getBalanceInfo())
    println(debitCard.getAvailableFundsInfo())
    println()

    // Тестирование CreditCard
    println("=== Тестирование CreditCard ===")
    val creditCard = CreditCard(10000.0)
    println(creditCard.getBalanceInfo())
    creditCard.topUp(5000.0)
    println(creditCard.getBalanceInfo())
    creditCard.pay(5000.0)
    println(creditCard.getBalanceInfo())
    creditCard.pay(3000.0)
    println(creditCard.getBalanceInfo())
    creditCard.topUp(2000.0)
    println(creditCard.getBalanceInfo())
    creditCard.topUp(2000.0)
    println(creditCard.getBalanceInfo())
    println(creditCard.getAvailableFundsInfo())
    println()

    // Тестирование PremiumDebitCard
    println("=== Тестирование PremiumDebitCard ===")
    val premiumDebit = PremiumDebitCard()
    premiumDebit.topUp(100000.0)
    premiumDebit.pay(20000.0)
    println(premiumDebit.getAvailableFundsInfo())
    println()

    // Тестирование GoldCreditCard
    println("=== Тестирование GoldCreditCard ===")
    val goldCredit = GoldCreditCard(20000.0)
    goldCredit.topUp(5000.0)
    goldCredit.pay(10000.0)
    goldCredit.pay(6000.0) // Должен сработать кэшбэк 5%
    println(goldCredit.getAvailableFundsInfo())
}