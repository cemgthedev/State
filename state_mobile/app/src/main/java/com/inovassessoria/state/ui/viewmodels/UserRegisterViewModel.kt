package com.inovassessoria.state.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inovassessoria.state.models.TUser
import com.inovassessoria.state.repositories.UserRepository
import com.inovassessoria.state.services.UserRegisterResponse
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _document = MutableLiveData<String>()
    val document: LiveData<String> get() = _document

    private val _avatarUrl = MutableLiveData<String>()
    val avatarUrl: LiveData<String> get() = _avatarUrl

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> get() = _phone

    private val _uf = MutableLiveData<String>()
    val uf: LiveData<String> get() = _uf

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> get() = _city

    private val _street = MutableLiveData<String>()
    val street: LiveData<String> get() = _street

    private val _neighborhood = MutableLiveData<String>()
    val neighborhood: LiveData<String> get() = _neighborhood

    private val _number = MutableLiveData<String>()
    val number: LiveData<String> get() = _number

    private val _complement = MutableLiveData<String>()
    val complement: LiveData<String> get() = _complement

    private val _cep = MutableLiveData<String>()
    val cep: LiveData<String> get() = _cep

    private val _userResult = MutableLiveData<Result<UserRegisterResponse>>()
    val userResult: LiveData<Result<UserRegisterResponse>> get() = _userResult

    // Métodos de set
    fun setName(value: String) { _name.value = value }
    fun setEmail(value: String) { _email.value = value }
    fun setPassword(value: String) { _password.value = value }
    fun setDocument(value: String) { _document.value = value }
    fun setAvatarUrl(value: String) { _avatarUrl.value = value }
    fun setPhone(value: String) { _phone.value = value }
    fun setUf(value: String) { _uf.value = value }
    fun setCity(value: String) { _city.value = value }
    fun setStreet(value: String) { _street.value = value }
    fun setNeighborhood(value: String) { _neighborhood.value = value }
    fun setNumber(value: String) { _number.value = value }
    fun setComplement(value: String) { _complement.value = value }
    fun setCep(value: String) { _cep.value = value }

    // Métodos de get
    fun getName(): String = _name.value ?: ""
    fun getEmail(): String = _email.value ?: ""
    fun getPassword(): String = _password.value ?: ""
    fun getDocument(): String = _document.value ?: ""
    fun getAvatarUrl(): String = _avatarUrl.value ?: ""
    fun getPhone(): String = _phone.value ?: ""
    fun getUf(): String = _uf.value ?: ""
    fun getCity(): String = _city.value ?: ""
    fun getStreet(): String = _street.value ?: ""
    fun getNeighborhood(): String = _neighborhood.value ?: ""
    fun getNumber(): String = _number.value ?: ""
    fun getComplement(): String = _complement.value ?: ""
    fun getCep(): String = _cep.value ?: ""

    fun userRegister() {
        val user = TUser(
            name = getName(),
            email = getEmail(),
            password = getPassword(),
            document = getDocument(),
            avatarUrl = getAvatarUrl(),
            phone = getPhone(),
            city = getCity(),
            street = getStreet(),
            neighborhood = getNeighborhood(),
            number = getNumber(),
            complement = getComplement(),
            cep = getCep(),
        )

        viewModelScope.launch {
            val result = userRepository.userRegister(user)
            _userResult.value = result
        }
    }
}