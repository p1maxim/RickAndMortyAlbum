package com.example.rickandmortyalbum.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "characters")
@TypeConverters(Converters::class)
data class CharacterItem(
    @PrimaryKey
    val id: Int,
    val gender: String,
    val image: String,
    val name: String,
    val status: String,
    val location: CharLocation,
)

data class CharLocation(
    val name: String
)

class Converters {
    @TypeConverter
    fun fromCharLocation(location: CharLocation?): String? {
        return location?.name
    }

    @TypeConverter
    fun toCharLocation(name: String?): CharLocation? {
        return name?.let {
            CharLocation(it)
        }
    }
}