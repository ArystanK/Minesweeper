package kz.arctan.minesweaper.auth.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.arctan.minesweaper.auth.common.EmailAlreadyExistsException
import kz.arctan.minesweaper.auth.common.UnableToRegisterException
import kz.arctan.minesweaper.auth.common.UnableToSaveToDatabaseException
import kz.arctan.minesweaper.auth.domain.AuthRepository

class AuthRepositoryFirebase : AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseFirestore.getInstance()

    override suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password).isSuccessful
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        if (emailExists(email)) throw EmailAlreadyExistsException()
        withContext(Dispatchers.IO) {
            if (!firebaseDatabase.collection("users").add(
                    mapOf(
                        "email" to email,
                        "firstName" to firstName,
                        "lastName" to lastName
                    )
                ).isSuccessful
            ) throw UnableToSaveToDatabaseException()
            if (!firebaseAuth.createUserWithEmailAndPassword(
                    email,
                    password
                ).isSuccessful
            ) throw UnableToRegisterException()
        }
    }

    override suspend fun getUser() = withContext(Dispatchers.IO) { firebaseAuth.currentUser }

    override suspend fun emailExists(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            val collection = firebaseDatabase.collection("users")
            var result = false
            collection.get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    if (task.result == null) return@addOnCompleteListener
                    for (document in task.result!!) {
                        if (document["email"] == email) {
                            result = true
                            return@addOnCompleteListener
                        }
                    }
                }
            }
            result
        }
    }
}