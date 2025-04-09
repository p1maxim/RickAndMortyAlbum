package com.example.rickandmortyalbum.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyalbum.data.model.CharacterItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): Flow<CharacterItem?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterItem: CharacterItem)

    @Query("SELECT * FROM characters")
    fun pagingSource(): PagingSource<Int, CharacterItem>

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}