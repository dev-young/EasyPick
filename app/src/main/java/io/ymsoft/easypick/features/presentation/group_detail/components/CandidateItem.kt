package io.ymsoft.easypick.features.presentation.group_detail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ymsoft.easypick.features.domain.model.Candidate
import io.ymsoft.easypick.features.presentation.candi_groups.HomeScaffold
import io.ymsoft.easypick.features.presentation.group_detail.CandidateList
import io.ymsoft.easypick.ui.theme.EasyPickTheme


@ExperimentalMaterialApi
@Composable
fun CandidateItem(
    candi: Candidate,
    modifier: Modifier
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 4.dp
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = candi.name,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
//            Spacer(modifier = Modifier.height(6.dp))
//            Text(
//                text = "...",
//                style = MaterialTheme.typography.body1,
//                color = Color.White
//            )
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
            val candis = (0..5).map { Candidate("후보$it") }
            CandidateList(candis)
        }
    }
}