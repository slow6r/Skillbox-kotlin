import exceptions.*

class Wheel {
    private var currentPressure: Double = 0.0
    
    companion object {
        const val MIN_NORMAL_PRESSURE = 1.6
        const val MAX_NORMAL_PRESSURE = 2.5
        const val MAX_POSSIBLE_PRESSURE = 10.0
    }
    
    fun pump(pressure: Double) {
        when {
            pressure < 0 || pressure > MAX_POSSIBLE_PRESSURE -> 
                throw IncorrectPressure("Давление должно быть от 0 до $MAX_POSSIBLE_PRESSURE атмосфер")
            pressure > MAX_NORMAL_PRESSURE -> 
                throw TooHighPressure("Давление превышает максимально допустимое значение $MAX_NORMAL_PRESSURE атмосфер")
            pressure < MIN_NORMAL_PRESSURE -> 
                throw TooLowPressure("Давление ниже минимально допустимого значения $MIN_NORMAL_PRESSURE атмосфер")
            else -> currentPressure = pressure
        }
    }
    
    fun checkPressure() {
        when {
            currentPressure > MAX_NORMAL_PRESSURE -> 
                throw TooHighPressure("Давление превышает максимально допустимое значение $MAX_NORMAL_PRESSURE атмосфер")
            currentPressure < MIN_NORMAL_PRESSURE -> 
                throw TooLowPressure("Давление ниже минимально допустимого значения $MIN_NORMAL_PRESSURE атмосфер")
        }
    }
    
    fun getCurrentPressure(): Double = currentPressure
} 