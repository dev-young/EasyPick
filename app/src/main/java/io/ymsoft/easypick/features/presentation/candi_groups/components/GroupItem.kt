package io.ymsoft.easypick.features.presentation.candi_groups.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.ui.theme.EasyPickTheme


@ExperimentalMaterialApi
@Composable
fun GroupItem(
    group: CandiGroup,
    modifier: Modifier
) {
    Surface(onClick = {

    }, modifier = modifier) {
        Text(
            text = group.name,
            color = MaterialTheme.colors.primary
        )
    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun GroupPreview() {
    EasyPickTheme {
        GroupItem(group = CandiGroup("그룹이름"), modifier = Modifier)
    }
}