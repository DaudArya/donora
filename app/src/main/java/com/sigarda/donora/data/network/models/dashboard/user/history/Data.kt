package com.sigarda.donora.data.network.models.dashboard.user.history

data class Data(
    val jumlah: Int,
    val lastHistory: List<String>,
    val totalHistory: List<String>
)