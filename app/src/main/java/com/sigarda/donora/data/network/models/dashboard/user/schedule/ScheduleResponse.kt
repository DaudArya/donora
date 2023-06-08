package com.sigarda.donora.data.network.models.dashboard.user.schedule

data class ScheduleResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)