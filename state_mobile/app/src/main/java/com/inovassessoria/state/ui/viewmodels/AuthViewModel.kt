package com.inovassessoria.state.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.inovassessoria.state.repositories.AuthRepository
import com.inovassessoria.state.services.AuthenticationResponse
import com.inovassessoria.state.services.Credential

class AuthenticationException(message: String) : Exception(message)

data class Message(val type: String, val text: String)

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _authResult = MutableLiveData<Result<AuthenticationResponse>?>(null)
    val authResult: LiveData<Result<AuthenticationResponse>?> get() = _authResult

    private val _message = MutableLiveData<Message>(Message(type = "warning", text = "Usuário ou senha inválidos"))
    val message: LiveData<Message> get() = _message

    fun clear() {
        _authResult.value = null
    }

    fun getToken(): String? {
        val token = _authResult.value?.getOrNull()?.accessToken
        return token?.let { "Bearer $it" }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(Credential(email, password))
            _authResult.value = result

            if (result.isFailure) {
                _message.value = Message(type = "warning", text = "Usuário ou senha inválidos")
            }
        }
    }

    fun logout() {
        _authResult.value = Result.failure(AuthenticationException("Sessão expirada"))
        _message.value = Message(type = "info", text = "Sessão expirada")
    }
}