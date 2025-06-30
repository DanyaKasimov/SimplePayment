package app.entity

import app.exceptions.InvalidDataException
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import java.util.*

@Entity
@Table(name = "wallets")
class Wallet{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, unique = true)
    var userId: UUID = UUID.randomUUID()

    @field:Min(0)
    @Column(nullable = false)
    var balance: Long = 0L


    fun changeBalance(amount: Long) {
        if (amount < 0) {
            val absAmount = -amount
            if (balance < absAmount) {
                throw InvalidDataException("Недостаточно средств")
            }
            balance -= absAmount
        } else {
            balance += amount
        }
    }
}