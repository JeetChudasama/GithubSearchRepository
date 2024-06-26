package com.example.teachmintassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.teachmintassignment.ui.theme.BackgroundColor
import com.example.teachmintassignment.ui.theme.CardBackgroundColor
import com.example.teachmintassignment.ui.theme.TeachmintAssignmentTheme

class RepoDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the repository name from the intent extras
        val repoName = intent.getStringExtra("repo_name")
        val repodescription = intent.getStringExtra("description")
        val repoUrl = intent.getStringExtra("html_Url")
        val repoOwner = intent.getSerializableExtra("owner") as? owner
        val ownerAvatarUrl = repoOwner?.avatar_url ?: ""


        setContent {
            TeachmintAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .background(BackgroundColor)
                ) {
                    // Calling the composable function to display the repository name & details
                    RepoDetailsContent(repoName ?: "No Repository Name",
                        repodescription ?: "No Description",
                        repoUrl ?: "No URL", ownerAvatarUrl
                    )
                }
            }
        }
    }
}

@Composable
fun RepoDetailsContent(repoName: String, repodescription: String, repoUrl: String, ownerAvatarUrl: String) {
    // Displaying the repository Details using Text composable

    val uriHandler = LocalUriHandler.current
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Name: $repoName",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.Cursive
        )
        Divider(
            color = CardBackgroundColor,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 30.dp)
        )
        Text(
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = repodescription,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "View Repository",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = buildAnnotatedString {
                append("")
                withStyle(
                    style = SpanStyle(
                        color = Color.Cyan,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(repoUrl)
                    addStringAnnotation(
                        tag = "URL",
                        annotation = repoUrl,
                        start = 0,
                        end = repoUrl.length
                    )
                }
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            modifier = Modifier.clickable {
                uriHandler.openUri(repoUrl)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Owner Avatar",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.height(5.dp))
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.CenterHorizontally)
        ) {

            // Load and display the avatar image using Coil
            Image(
                painter = rememberImagePainter(ownerAvatarUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(maxWidth, maxWidth)
            )
        }
    }
}

@Preview
@Composable
fun RepoDetailsPreview() {
    RepoDetailsContent(
        "Sample Repository",
        "No Description",
        "No Repository Name",
        "No image"
    )
}

