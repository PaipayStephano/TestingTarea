package com.mexiti.costogasolina

import org.junit.Test
import java.text.NumberFormat
import org.junit.Assert.assertEquals

class CalcularMontoTest {

    @Test
    fun calcularMonto_40L_22_35() {
        val precio = 22.35
        val cantLitros = 40.0
        val darPropina = false

        val montoEsperado = NumberFormat.getCurrencyInstance().format(22.35 * 40.0) // 894.0
        val montoActual = calcularMonto(precio, cantLitros, darPropina, 0.0)

        assertEquals("Comparación entre montos", montoEsperado, montoActual)
    }
}
