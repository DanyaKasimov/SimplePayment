package app.dto

import app.constants.Gender
import app.constants.Role
import java.time.LocalDate

data class UserDTO(
    var name: String,

    var surname: String,

    var middle: String? = null,

    var birthday: LocalDate,

    var gender: Gender,

    var inn: String,

    var address: String,

    var role: Role,

    var email: String,
) {
}