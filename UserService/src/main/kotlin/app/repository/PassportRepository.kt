package app.repository

import app.entity.Passport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PassportRepository : JpaRepository<Passport, UUID> {
}