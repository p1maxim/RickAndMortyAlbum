package com.example.rickandmortyalbum.data.model

data class CharacterList(
    val page: PageInfo,
    val results: List<CharacterItem>
)
