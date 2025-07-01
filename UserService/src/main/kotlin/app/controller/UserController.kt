package app.controller

import app.api.UserApi
import app.dto.ExistDTO
import app.dto.UserDTO
import app.service.UserService
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
open class UserController(private val userService: UserService) : UserApi {

    override fun existUser(userId: UUID): ExistDTO = ExistDTO(userService.existsById(userId))

    override fun getUser(userId: UUID): UserDTO = userService.findById(userId)

}