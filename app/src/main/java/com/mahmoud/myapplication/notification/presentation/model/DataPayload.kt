package com.mahmoud.myapplication.notification.presentation.model

import com.google.gson.annotations.SerializedName

data class DataPayload(
    @SerializedName("screenName")
    val screenName: String,
    @SerializedName("id")
    val id: String
)
