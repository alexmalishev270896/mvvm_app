package com.alex_malishev.presentation_layer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsParcelable(
    val title: String,
    val date: String,
    val author: String? = null,
    val description: String? = null,
    val url: String? = null,
    val imageUrl: String? = null,
    val content: String? = null,
    val source: NewsSourceParcelable? = null
) : Parcelable