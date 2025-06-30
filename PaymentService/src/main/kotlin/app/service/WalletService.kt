package app.service

import app.entity.Wallet
import java.util.UUID

interface WalletService {

    fun createWallet(userId: UUID): Wallet

    fun changeBalance(walletId: UUID, balance: Long): Wallet

    fun getWallet(userId: UUID): Wallet

    fun getWalletById(id: UUID): Wallet

    fun saveAll(wallets: List<Wallet>)
}