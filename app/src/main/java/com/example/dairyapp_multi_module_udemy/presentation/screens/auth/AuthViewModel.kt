package com.example.dairyapp_multi_module_udemy.presentation.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dairyapp_multi_module_udemy.utils.Constants.MONGO_APP_ID
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    var authenticated = mutableStateOf(false)
        private set
    var loadingState = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        println("I am tring to update state : " + loading)
        loadingState.value = loading
    }

    fun signInWithMongoAtlas(
        tokenId: String, onSuccess: (Boolean) -> Unit, onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    App.create(MONGO_APP_ID)
                        .login(
//                            Credentials.google(tokenId, GoogleAuthType.ID_TOKEN)
                            Credentials.jwt(tokenId)
                        )
                        .loggedIn
                }
                withContext(Dispatchers.Main) {
                    onSuccess(result)
                    delay(600)
                    authenticated.value = true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure(e)
                }
            }
        }

    }
}