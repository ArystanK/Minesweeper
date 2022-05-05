package kz.arctan.minesweaper.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.arctan.minesweaper.auth.domain.AuthRepository

class AuthRepositoryFirebase : AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseFirestore.getInstance()
    private val users = firebaseDatabase.collection("users")


    override suspend fun login(email: String, password: String): Boolean {
        if (!emailExists(email)) throw SuchEmailDoesNotExistsException()
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
            if (!users.add(
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
            var result = false
            users.get().addOnSuccessListener { snapshot ->
                result = snapshot.find { it["email"] == email } != null
            }
            result
        }
    }
}