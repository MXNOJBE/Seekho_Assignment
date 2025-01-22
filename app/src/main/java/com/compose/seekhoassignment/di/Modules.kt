package com.compose.seekhoassignment.di

import com.compose.seekhoassignment.data.AnimeListApi
import com.compose.seekhoassignment.data.AnimeListRepository
import com.compose.seekhoassignment.data.AnimeListRepositoryImpl
import com.compose.seekhoassignment.data.viewmodel.AnimeListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



val appModules = module {

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.jikan.moe/v4/")
            .build()
    }

    //Api Fetches
    single { get<Retrofit>().create(AnimeListApi::class.java) }

    //Dispatcher
    single { Dispatchers.IO }

    //Repositories
    single<AnimeListRepository> { AnimeListRepositoryImpl(get(), get()) }

    //ViewModels
    viewModel { AnimeListViewModel(get()) }

}