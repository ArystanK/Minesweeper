package kz.arctan.minesweaper.common.data

import com.google.firebase.firestore.FirebaseFirestore
import kz.arctan.minesweaper.common.domain.ScoresRepository

class ScoresRepositoryFirebase : ScoresRepository {
    private val firebaseDatabase = FirebaseFirestore.getInstance()

    override fun insertScore(timeMillis: Long, email: String) {
        firebaseDatabase
    }

    override fun getScores(email: String): List<Long> {
        TODO("Not yet implemented")
    }

    override fun getScores(): List<Long> {
        TODO("Not yet implemented")
    }
}