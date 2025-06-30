package app.repository

import app.entity.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WalletRepository : JpaRepository<Wallet, UUID> {

    fun existsByUserId(userId: UUID): Boolean

    fun findByUserId(userId: UUID): Wallet?
}