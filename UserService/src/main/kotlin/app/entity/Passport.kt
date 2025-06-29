package app.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "passports",
    uniqueConstraints = [UniqueConstraint(columnNames = ["series", "number"])]
)
class Passport{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false, length = 4)
    var series: String = ""

    @Column(nullable = false, length = 6)
    var number: String = ""

    @Column(nullable = true)
    var url: String = ""
}