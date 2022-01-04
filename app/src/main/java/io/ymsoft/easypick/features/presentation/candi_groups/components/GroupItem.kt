package io.ymsoft.easypick.features.presentation.candi_groups.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.presentation.candi_groups.GroupList
import io.ymsoft.easypick.features.presentation.candi_groups.HomeScaffold
import io.ymsoft.easypick.ui.theme.EasyPickTheme


@ExperimentalMaterialApi
@Composable
fun GroupItem(
    group: CandiGroup,
    modifier: Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 4.dp
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "...",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
    }


}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun GroupPreview() {
    EasyPickTheme(darkTheme = true) {
        HomeScaffold {
            val groups = (0..5).map { CandiGroup("그룹$it") }
            GroupList(groups = groups)
        }
    }
}