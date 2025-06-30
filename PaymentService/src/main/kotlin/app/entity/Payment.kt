package app.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "payments")
class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(nullable = false, name = "sender_wallet_id")
    var senderWallet: Wallet = Wallet()

    @ManyToOne
    @JoinColumn(nullable = false, name = "recipient_wallet_id")
    var recipientWallet: Wallet = Wallet()

    @Column(nullable = false)
    var amount: Long = 0L

    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
}