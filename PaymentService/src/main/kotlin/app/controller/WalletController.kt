package app.controller

import app.api.WalletApi
import app.entity.Wallet
import app.service.WalletService
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
open class WalletController(private val walletService: WalletService) : WalletApi {

    override fun createWallet(userId: UUID): Wallet = walletService.createWallet(userId)

    override fun changeBalance(walletId: UUID, balance: Long): Wallet = walletService.changeBalance(walletId, balance)

    override fun getWallet(userId: UUID): Wallet = walletService.getWallet(userId)
}