package app.service.impl

import app.clients.UserClient
import app.entity.Wallet
import app.exceptions.InvalidDataException
import app.exceptions.NotFoundDataException
import app.repository.WalletRepository
import app.service.WalletService
import org.springframework.stereotype.Service
import java.util.*

@Service
class WalletServiceImpl(private val walletRepository: WalletRepository,
                        private val userClient: UserClient) : WalletService {

    override fun createWallet(userId: UUID): Wallet {
        if (walletRepository.existsByUserId(userId)) {
            throw InvalidDataException("Кошелек для пользователя $userId уже существует")
        }
        if (!userClient.existUser(userId).isExist) {
            throw NotFoundDataException("Пользователь с id=$userId не найден")
        }

        val wallet = Wallet()
        wallet.userId = userId

        return walletRepository.save(wallet)
    }

    override fun changeBalance(walletId: UUID, balance: Long): Wallet {
        val wallet = walletRepository.findById(walletId)
            .orElseThrow { throw NotFoundDataException("Кошелек не найден")}

        wallet.changeBalance(balance)
        return walletRepository.save(wallet)
    }

    override fun getWallet(userId: UUID): Wallet = walletRepository.findByUserId(userId)
        ?: throw NotFoundDataException("Кошелек не найден")


    override fun getWalletById(id: UUID): Wallet = walletRepository.findById(id).orElseThrow {
        throw NotFoundDataException("Кошелек не найден")
    }

    override fun saveAll(wallets: List<Wallet>) {
        walletRepository.saveAll(wallets)
    }
}