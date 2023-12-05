package com.nutricatch.dev.model

data class LatestPostResponse(
    val error: Boolean?,
    val message: String?,
    val latestPosts: List<LatestPost>,
)

data class LatestPost(
    val id: Int,
    val title: String?,
    val description: String?,
    val imgUrl: String?,
)
