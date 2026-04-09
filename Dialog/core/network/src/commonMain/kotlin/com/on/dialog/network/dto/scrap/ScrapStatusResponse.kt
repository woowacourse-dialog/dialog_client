package com.on.dialog.network.dto.scrap

import com.on.dialog.model.scrap.ScrapStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ScrapStatusResponse(
    @SerialName("isScraped")
    val isScraped: Boolean,
) {
    fun toDomain(): ScrapStatus = ScrapStatus(isScraped = isScraped)
}
