package com.example.rickandmortyalbum.di

import android.content.Context
import com.example.rickandmortyalbum.data.db.AlbumDb
import com.example.rickandmortyalbum.data.remote.CharacterRemoteService
import com.example.rickandmortyalbum.data.repository.DataStoreManager
import com.example.rickandmortyalbum.data.remote.DataStoreManagerImpl
import com.example.rickandmortyalbum.domain.CharacterRepository
import com.example.rickandmortyalbum.data.repository.CharacterRepositoryImpl
import com.example.rickandmortyalbum.domain.ConfigDataRepository
import com.example.rickandmortyalbum.data.repository.ConfigDataRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterRemoteService = retrofit.create(CharacterRemoteService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AlbumDb.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AlbumDb) = db.characterDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(db: AlbumDb) = db.remoteKeysDao()

    @Singleton
    @Provides
    fun provideCharacterRepository(
        db: AlbumDb,
        apiService: CharacterRemoteService,
        dataStoreManager: DataStoreManager
    ): CharacterRepository = CharacterRepositoryImpl(
        localDb = db,
        apiService = apiService,
        dataStoreManager = dataStoreManager
    )

    @Singleton
    @Provides
    fun provideConfigDataRepository(
        dataStoreManager: DataStoreManager
    ): ConfigDataRepository = ConfigDataRepositoryImpl(
        dataStoreManager = dataStoreManager
    )

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManagerImpl(context = appContext)
}