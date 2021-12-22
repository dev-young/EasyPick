package io.ymsoft.easypick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ymsoft.easypick.ui.theme.EasyPickTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyPickTheme {
                Column {

                    ClickCounter(clicks = 1) {
                    }
                    // A surface container using the 'background' color from the theme
                    ListView(listOf(1, 2, 3, 4, 5, 6).map { it.toString() })
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = MaterialTheme.colors.background) {
        Text(text = "Hello $name!")
    }

}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    var c by remember { mutableStateOf(clicks) }
    Button(onClick = {
        c++
        onClick.invoke()
    }) {
        Text("I've been clicked $c times")
    }
}

@Composable
fun ItemView(
    name: String,
    description: String = "asd;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj;fkljasdklfjask;dfljasdklfjask;dfljask;dfadfd;kfj"
) {
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor: Color by animateColorAsState(
        if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
    )
    Row(
        Modifier.padding(8.dp, 4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_temp), null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colors.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.clickable {
            isExpanded = !isExpanded
        }) {
            Text(
                text = name,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = surfaceColor,
//                modifier = Modifier
//                    .animateContentSize()
            ) {
                Text(
                    text = description,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }

        }


    }
}

@Composable
fun ListView(nameList: List<String>) {
    LazyColumn {
        items(nameList) {
            ItemView(it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EasyPickTheme {
        ListView(listOf(1, 2, 3, 4, 5, 6).map { it.toString() })
//        ItemView(name = "000")
    }

}