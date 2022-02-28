package kz.arctan.minesweaper.auth.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean

    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    )

    suspend fun getUser(): FirebaseUser?

    suspend fun emailExists(email: String): Boolean
}