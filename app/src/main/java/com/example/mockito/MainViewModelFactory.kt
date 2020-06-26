package com.example.mockito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mockito.data.UserService

class MainViewModelFactory (private val userService: UserService): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java!!)) {
                return MainViewModel(this.userService) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}