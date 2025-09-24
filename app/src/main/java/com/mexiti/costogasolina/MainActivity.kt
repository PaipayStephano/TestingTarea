package com.mexiti.costogasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CostGasLayout()
                }
            }
        }
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardsOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        label = { Text(text = stringResource(id = label)) },
        value = value,
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null
            )
        },
        keyboardOptions = keyboardsOptions,
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChanged
    )
}

/* ---------- SWITCH DE PROPINA ---------- */
@Composable
fun AddTip(
    darPropina: Boolean,
    onTipCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.agregar_propina),
            modifier = Modifier.padding(20.dp)
        )
        Switch(checked = darPropina, onCheckedChange = onTipCheckedChange)
    }
}


@Composable
fun CostGasLayout() {
    var precioLitroEntrada by remember { mutableStateOf("") }
    var cantLitrosEntrada by remember { mutableStateOf("") }
    var propinaEntrada by remember { mutableStateOf("") }
    var darPropina by remember { mutableStateOf(false) }

    val precioLitro = precioLitroEntrada.toDoubleOrNull() ?: 0.0
    val cantLitros = cantLitrosEntrada.toDoubleOrNull() ?: 0.0
    val propina = propinaEntrada.toDoubleOrNull() ?: 0.0
    val total = calcularMonto(precioLitro, cantLitros, darPropina, propina)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(15.dp)),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calcular_monto),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )

        EditNumberField(
            label = R.string.ingresa_gasolina,
            leadingIcon = R.drawable.money_gas,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = precioLitroEntrada,
            onValueChanged = { newValue -> precioLitroEntrada = newValue },
            modifier = Modifier.testTag("precioTextField")
        )

        EditNumberField(
            label = R.string.litros,
            leadingIcon = R.drawable.gasolina,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = cantLitrosEntrada,
            onValueChanged = { newValue -> cantLitrosEntrada = newValue },
            modifier = Modifier.testTag("litrosTextField")
        )

        EditNumberField(
            label = R.string.propina,
            leadingIcon = R.drawable.outline_18_up_rating_24,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = propinaEntrada,
            onValueChanged = { newValue -> propinaEntrada = newValue },
            modifier = Modifier.testTag("propinaTextField")
        )

        AddTip(
            darPropina = darPropina,
            onTipCheckedChange = { checked -> darPropina = checked }
        )

        Text(
            text = stringResource(R.string.monto_total, total),
            fontWeight = FontWeight.Black,
            fontSize = 30.sp,
            modifier = Modifier.testTag("montoText")
        )
    }
}


@VisibleForTesting
internal fun calcularMonto(
    precio: Double,
    cantLitros: Double,
    darPropina: Boolean,
    propina: Double
): String {
    var monto = precio * cantLitros
    if (darPropina) monto += propina
    return NumberFormat.getCurrencyInstance().format(monto)
}
