package app.api

import app.dto.ApiErrorResponse
import app.dto.MessageDTO
import app.dto.PassportDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/passport")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Паспорт", description = "Методы для управления паспортом.")
interface PassportApi {

    @Operation(description = "Добавить скан паспорта.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Паспорт добавлен.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MessageDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Паспорт не найден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @PostMapping(
        value = ["/upload"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    fun addPassportScan(@RequestPart("series") @NotBlank series: String,
                        @RequestPart("number") @NotBlank number: String,
                        @RequestPart("scan") @NotNull scan: MultipartFile): MessageDTO


    @Operation(description = "Получить паспорт.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Паспорт получен.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PassportDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Паспорт не найден.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApiErrorResponse::class)
                )]
            )
        ]
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getPassport(@RequestParam("series") @NotBlank series: String,
                    @RequestParam("number") @NotBlank number: String): PassportDTO

}