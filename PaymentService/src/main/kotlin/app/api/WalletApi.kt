package app.api

import app.dto.ApiErrorResponse
import app.dto.MessageDTO
import app.entity.Wallet
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RequestMapping("/wallet")
//@PreAuthorize("isAuthenticated()")
@Tag(name = "Кошельки", description = "Методы для управления кошельками.")
interface WalletApi {

    @Operation(description = "Создать кошелек.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Кошелек создан.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Wallet::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь не найден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun createWallet(@PathVariable("userId") @Valid userId: UUID) : Wallet


    @Operation(description = "Пополнить баланс.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Баланс пополнен.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Wallet::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Кошелек не найден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @PutMapping("/{walletId}/{balance}")
    @ResponseStatus(HttpStatus.OK)
    fun changeBalance(@PathVariable("walletId") @Valid walletId: UUID,
                      @PathVariable("balance") @Valid balance: Long) : Wallet


    @Operation(description = "Получить кошелек.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Кошелек получен.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Wallet::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Пользователь не найден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getWallet(@PathVariable("userId") @Valid userId: UUID) : Wallet
}