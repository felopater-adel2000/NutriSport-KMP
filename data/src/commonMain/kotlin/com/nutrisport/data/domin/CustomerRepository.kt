package com.nutrisport.data.domin

import dev.gitlive.firebase.auth.FirebaseUser

interface CustomerRepository {

    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

}