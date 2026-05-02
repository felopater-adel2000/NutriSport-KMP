package com.nutrisport.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrisport.data.domin.CustomerRepository
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(
    private val customerRepo: CustomerRepository
) : ViewModel() {

    fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            customerRepo.createCustomer(user, onSuccess, onError)
        }
    }

}