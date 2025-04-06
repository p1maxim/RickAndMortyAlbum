package com.example.rickandmortyalbum.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.data.model.RemoteKeys

@Database(entities = [CharacterItem::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class AlbumDb : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile private var instance: AlbumDb? = null

        fun getDatabase(context: Context): AlbumDb =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AlbumDb::class.java, "characters")
                .fallbackToDestructiveMigration()
                .build()
    }

}