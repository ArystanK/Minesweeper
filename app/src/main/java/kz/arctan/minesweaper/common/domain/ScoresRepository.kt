package kz.arctan.minesweaper.common.domain

interface ScoresRepository {
    suspend fun insertScore(timeMillis: Long, email: String)

    suspend fun getScores(email: String): List<Long>

    suspend fun getScores(): List<Long>
}