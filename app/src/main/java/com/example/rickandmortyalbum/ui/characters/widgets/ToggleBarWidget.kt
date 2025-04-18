package com.example.rickandmortyalbum.ui.characters.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmortyalbum.R

@Composable
fun ToggleBarWidget(
    modifier: Modifier = Modifier,
    useDb: Boolean,
    onValueChanged: (Boolean) -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 36.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${stringResource(R.string.caching_data)} " +
                        if (useDb) stringResource(R.string.enabled)
                        else stringResource(R.string.disabled),
            modifier = modifier.padding(vertical = 8.dp)
        )

        Switch(
            checked = useDb,
            onCheckedChange = {
                onValueChanged(it)
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ToggleBarWidgetPreview() {
    ToggleBarWidget(
        useDb = true,
        onValueChanged = {}
    )
}