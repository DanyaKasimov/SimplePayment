package app.clients

import app.dto.EmailDTO
import app.dto.ExistDTO
import jakarta.validation.Valid
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Component
@FeignClient("user-service", url = "\${app.user-service.url}/user")
interface UserClient {

    @GetMapping("/{userId}")
    fun existUser(@PathVariable("userId") @Valid userId: UUID) : ExistDTO
}