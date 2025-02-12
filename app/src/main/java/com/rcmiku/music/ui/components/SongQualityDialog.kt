package com.rcmiku.music.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rcmiku.music.R
import com.rcmiku.ncmapi.api.player.SongLevel

@Composable
fun SongQualityDialog(
    currentLevel: SongLevel,
    onDismiss: () -> Unit,
    onQualitySelected: (SongLevel) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier
                    .selectableGroup()
                    .padding(vertical = 24.dp)
            ) {
                SongLevel.entries.forEach { level ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (level == currentLevel),
                                onClick = {
                                    onQualitySelected(level)
                                    onDismiss()
                                },
                                role = Role.RadioButton,
                            )
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = level == currentLevel,
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = when (level) {
                                SongLevel.STANDARD -> stringResource(R.string.standard)
                                SongLevel.HIGHER -> stringResource(R.string.higer)
                                SongLevel.EXHIGH -> stringResource(R.string.exhigh)
                                SongLevel.LOSSLESS -> stringResource(R.string.lossless)
                                SongLevel.HIRES -> stringResource(R.string.hi_res)
//                                SongLevel.JYEFFECT -> stringResource(R.string.jyeffect)
//                                SongLevel.SKY -> stringResource(R.string.sky)
//                                SongLevel.JYMASTER -> stringResource(R.string.jymaster)
                            }
                        )
                    }
                }
            }
        }
    }
}
