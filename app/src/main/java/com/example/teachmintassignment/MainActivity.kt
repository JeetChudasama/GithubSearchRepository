package com.example.teachmintassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teachmintassignment.ui.theme.TeachmintAssignmentTheme

class MainActivity : ComponentActivity(), RepoSearchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeachmintAssignmentTheme {
                val viewModel = viewModel<ScreenViewModel>()
                viewModel.listener = this@MainActivity
                Screen(
                    viewModel = viewModel,
                    onCardClicked = { repo ->
                        navigateToRepoDetails(repo)
                    }
                )
            }
        }
    }
    private fun navigateToRepoDetails(repo: GitHubRepo) {
        val intent = Intent(this, RepoDetails::class.java).apply {
            // Pass necessary data to RepoDetails activity using extras
            putExtra("repo_name", repo.name)
            putExtra("description", repo.description)
            putExtra("html_Url", repo.html_url)
            putExtra("owner", repo.owner)
        }
        startActivity(intent)
    }

    override fun onSearchError(errorMessage: String) {
        Log.e("Error", errorMessage)
//        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}

////