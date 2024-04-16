package com.marwa.ar.data.model

import com.google.gson.annotations.SerializedName


class NewsResponse<T:Any>(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("articles") var articles: ArrayList<Articles> = arrayListOf()

)