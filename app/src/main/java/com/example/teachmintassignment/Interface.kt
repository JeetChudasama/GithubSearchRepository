package com.example.teachmintassignment

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.io.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

interface GitHubService {
    @Headers("Accept: application/vnd.github+json", "Authorization: <Api-KEy>")
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String
    ): RepoSearchResponse
}

@Entity(tableName = "repositories")
data class GitHubRepo(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String?,
    val html_url: String? = "",
    val forks_count: Int,
    val owner: owner,
    // Add other relevant fields as needed
): Serializable

data class RepoSearchResponse(
    @SerializedName("items")
    val items: List<GitHubRepo>
)

interface RepoSearchListener {
    fun onSearchError(errorMessage: String)
}

data class owner(
    val login: String? = "",
    val avatar_url: String? = ""
): Serializable

