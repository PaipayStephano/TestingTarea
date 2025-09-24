package com.mexiti.costogasolina

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat
// Opcional si quieres forzar que solo corra en Android 10 (API 29):
// import androidx.test.filters.SdkSuppress

class CalcularMontoUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // @SdkSuppress(minSdkVersion = 29, maxSdkVersion = 29) // <- opcional
    @Test
    fun calcular_Monto_40L_22_35() {
        composeTestRule.setContent {
            CostoGasolinaTheme { CostGasLayout() }
        }

        // Escribir en los campos correctos por TAG
        composeTestRule.onNodeWithTag("precioTextField")
            .performTextInput("22.35")

        composeTestRule.onNodeWithTag("litrosTextField")
            .performTextInput("40")

        val montoEsperado = NumberFormat.getCurrencyInstance().format(22.35 * 40.0)

        // Puedes validar por texto completo...
        composeTestRule.onNodeWithText("Monto Total: $montoEsperado")
            .assertExists("No se encontró el texto del monto total esperado")

        // ...o por TAG del resultado:
        // composeTestRule.onNodeWithTag("montoText")
        //     .assertTextEquals("Monto Total: $montoEsperado")
    }
}
