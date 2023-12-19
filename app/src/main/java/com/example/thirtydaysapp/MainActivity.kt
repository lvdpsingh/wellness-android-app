package com.example.thirtydaysapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thirtydaysapp.data.Datasource
import com.example.thirtydaysapp.model.Wellness
import com.example.thirtydaysapp.ui.theme.ThirtyDaysAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirtyDaysAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThirtyDaysApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirtyDaysApp() {
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ){
        WellnessList(
            wellnessList = Datasource().loadWellnessData(),
            modifier = Modifier.padding(it)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_small)),
                    painter = painterResource(R.drawable.ic_logo_1),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun WellnessList(wellnessList: List<Wellness>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(wellnessList) { wellness ->
            WellnessCard(
                wellness = wellness,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun WellnessCard(wellness: Wellness, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column (
            modifier = Modifier
                .clickable { expanded = !expanded }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ){
            Text(
                text = LocalContext.current.getString(wellness.stringResourceDay),
                modifier = Modifier.padding(start= 16.dp, end=16.dp, top=16.dp),
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = LocalContext.current.getString(wellness.stringResourceQuote),
                modifier = Modifier.padding(start= 16.dp, end=16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Image(
                painter = painterResource(wellness.imageResourceId),
                contentDescription = stringResource(wellness.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )
//            WellnessItemButton(
//                expanded = expanded,
//                onClick = { expanded = !expanded },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(0.dp),
//            )
        }
        if (expanded){
            WellnessDescription(stringResourceId = wellness.stringResourceId)
        }
        //WellnessDescription(stringResourceId = wellness.stringResourceId)
//        Column{
//            Text(
//                text = LocalContext.current.getString(wellness.stringResourceId),
//                modifier = Modifier.padding(start= 16.dp, end=16.dp, bottom=16.dp),
//                style = MaterialTheme.typography.titleSmall
//            )
//        }
    }
}

@Composable
private fun WellnessItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun WellnessDescription(
    @StringRes stringResourceId: Int,
    modifier: Modifier = Modifier
) {
//    Text(
//        text = stringResource(dogHobby),
//        style = MaterialTheme.typography.bodyLarge
//    )
    Text(
        text = stringResource(stringResourceId),
        modifier = modifier.padding(start= 16.dp, end=16.dp, bottom=16.dp),
        style = MaterialTheme.typography.titleSmall
    )
}

@Preview
@Composable
private fun WellnessCardPreview() {
    WellnessCard(Wellness(R.string.WellnessDay1, R.string.Wellness1Title, R.string.Wellness1, R.drawable.image1))
}

@Preview
@Composable
private fun ThirtyDaysAppPreview() {
    ThirtyDaysAppTheme {
        ThirtyDaysApp()
    }
}

@Preview
@Composable
private fun ThirtyDaysAppDarkTheme() {
    ThirtyDaysAppTheme(darkTheme = true) {
        ThirtyDaysApp()
    }
}
