package app.entity

import app.constants.Gender
import app.constants.Role
import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(nullable = false)
    var name: String = ""

    @Column(nullable = false)
    var surname: String = ""

    @Column
    var middle: String? = null

    @Column(nullable = false)
    var birthday: LocalDate = LocalDate.now()

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var gender: Gender = Gender.MALE

    @Column(nullable = false, unique = true)
    var inn: String = ""

    @Column(nullable = false)
    var address: String = ""

    @Column(nullable = false, unique = true)
    var email: String = ""

    @Column(nullable = false)
    var password: String = ""

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = Role.USER

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "passport_id", nullable = false)
    var passport: Passport = Passport()
}