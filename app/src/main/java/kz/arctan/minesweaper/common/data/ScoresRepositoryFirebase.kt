package kz.arctan.minesweaper.common.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.arctan.minesweaper.common.domain.ScoresRepository

class ScoresRepositoryFirebase : ScoresRepository {
    private val firebaseDatabase = FirebaseFirestore.getInstance()

    override suspend fun insertScore(timeMillis: Long, email: String) {
        withContext(Dispatchers.IO) {
            firebaseDatabase.collection("scores")
                .add(mapOf("email" to email, "score" to timeMillis))
        }
    }

    override suspend fun getScores(email: String): List<Long> {
        return withContext(Dispatchers.IO) {
            firebaseDatabase.collection("scores").get()
                .result?.filter { it["email"].toString() == email }?.map { it["score"]!! as Long }
                ?: emptyList()
        }
    }

    override suspend fun getScores(): List<Long> {
        return withContext(Dispatchers.IO) {
            firebaseDatabase.collection("scores")
                .get()
                .result?.map { it["score"]!! as Long }
                ?: emptyList()
        }
    }
}