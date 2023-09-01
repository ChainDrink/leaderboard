package orthe.com.github.ethwarsaw.leaderboard

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@RestController
class Leaderboard(
    private val repository: LeaderboardRepository
) {

    @GetMapping("leaderboard")
    fun fetch(): MutableList<Entry> {
        return repository.findAll()
    }

    @PostMapping("leaderboard")
    fun create(@RequestBody command: CreateEntryCommand) {
        val entity = Entry(
            id = UUID.randomUUID(),
            walletAddress = command.walletAddress,
            created = OffsetDateTime.now(),
            amount = command.amount,
            image = command.image
        )
        repository.save(entity)
    }
}

data class CreateEntryCommand(
    val walletAddress: String,
    val amount: Int,
    val image: String
)

@Entity
@Table(name = "leaderboard")
data class Entry(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "account_id")
    val walletAddress: String,

    @Column(name = "amount")
    val amount: Int,

    @Column(name = "image")
    val image: String,

    @Column(name = "created")
    val created: OffsetDateTime
)

interface LeaderboardRepository : JpaRepository<Entry, UUID>