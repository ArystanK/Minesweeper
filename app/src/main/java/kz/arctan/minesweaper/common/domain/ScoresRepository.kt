package kz.arctan.minesweaper.common.domain

interface ScoresRepository {
    fun insertScore(timeMillis: Long, email: String)

    fun getScores(email: String): List<Long>

    fun getScores(): List<Long>
}