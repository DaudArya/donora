package com.sigarda.donora.data.network.models.leaderboard.commonplace

data class AllLeaderBoardResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)