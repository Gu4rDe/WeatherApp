package com.example.weatherapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.weatherapp.domain.model.WeatherType

/**
 * Отображает иконку погодного условия из локальных ассетов
 * (не из URL иконки API).
 *
 * @param weatherType Тип погодных условий, определяющий иконку и contentDescription.
 * @param size Размер иконки в dp.
 * @param modifier Модификатор для Image.
 */
@Composable
fun WeatherStatusImage(
    weatherType: WeatherType,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = weatherType.iconResId),
        contentDescription = stringResource(id = weatherType.descriptionResId),
        modifier = modifier.size(size)
    )
}
