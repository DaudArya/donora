package com.sigarda.donora.data.network.models.leaderboard.bestplace

data class BestLeaderBoardResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)