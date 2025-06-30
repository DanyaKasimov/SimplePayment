package app.dto

import java.util.*

data class PassportDTO(
    var id: UUID? = null,

    var series: String,

    var number: String,

    var s3Url: String,
)