package com.example.teachmintassignment

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teachmintassignment.ui.theme.BackgroundColor
import com.example.teachmintassignment.ui.theme.CardBackgroundColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Screen(
    viewModel: ScreenViewModel,
    onCardClicked: (GitHubRepo) -> Unit
) {
    val state = viewModel.state
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp)
    ) {
        Crossfade(
            targetState = state.isSearchBarVisible,
            animationSpec = tween(durationMillis = 500), label = ""
        ) {
            if (it){
                SearchBar(
                    onCloseIconClicked = {
                        viewModel.onAction(UserAction.CLoseIconClicked)
                    },
                    onInputValueChange = { newText ->
                        viewModel.onAction(
                            UserAction.TextFieldInput(newText)
                        )
                    },
                    text = state.searchText,
                    onSearchClicked = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            } else{
                TopAppBar(
                    onSearchIconClicked = {
                        viewModel.onAction(UserAction.SearchIconClicked)
                    }
                )
            }
        }
        Divider(
            color = CardBackgroundColor,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 30.dp)
        )
        LazyColumn {
            items(state.repoList) { repo ->
                SingleItemCard(
                    repoName = repo.name,
                    onClick = { onCardClicked(repo) }
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onCloseIconClicked: () -> Unit,
    onInputValueChange: (String) -> Unit,
    text: String,
    onSearchClicked: () -> Unit
){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            onInputValueChange(it)
        },
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 18.sp
        ),
        placeholder = {
                      Text(text = "Search...",
                          fontFamily = FontFamily.Default,
                          color = Color.LightGray
                      )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = onCloseIconClicked) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Icon",
                    tint = Color.White
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.White,
            focusedBorderColor = Color.White,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchClicked() }
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleItemCard(
    repoName: String,
    onClick: () -> Unit
){
    Card(onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgroundColor,
            contentColor = Color.White
        )
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.ic_web),
                contentDescription = "Web icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = repoName,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun TopAppBar(
    onSearchIconClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Jeet Github",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.Cursive
            )
        IconButton(onClick = onSearchIconClicked) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview
@Composable
fun Prev(){
    Screen(
        viewModel = ScreenViewModel(),
        onCardClicked = {}
    )
}