package com.inovassessoria.state.di

import com.inovassessoria.state.services.AuthenticationService
import com.inovassessoria.state.ui.viewmodels.AuthViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.inovassessoria.state.BuildConfig
import com.inovassessoria.state.repositories.AuthRepository
import com.inovassessoria.state.repositories.EnterpriseRepository
import com.inovassessoria.state.repositories.UserRepository
import com.inovassessoria.state.services.EnterpriseService
import com.inovassessoria.state.services.UserRegisterService
import com.inovassessoria.state.ui.viewmodels.UserViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    singleOf(::AuthRepository) { bind<AuthRepository>() }
    singleOf(::UserRepository) { bind<UserRepository>() }
    singleOf(::EnterpriseRepository) { bind<EnterpriseRepository>() }

    viewModelOf(::AuthViewModel)
    viewModelOf(::UserViewModel)
}

val networkModule = module {
    // Configuração do Retrofit com Logging Interceptor
    single {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder().build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // Serviço de Autenticação
    single {
        get<Retrofit>().create(AuthenticationService::class.java)
    }

    // Serviço de Usuário
    single {
        get<Retrofit>().create(UserRegisterService::class.java)
    }

    // Serviço de Empresas
    single {
        get<Retrofit>().create(EnterpriseService::class.java)
    }
}