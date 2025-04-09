package com.example.rickandmortyalbum.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.data.model.DataResponse
import com.example.rickandmortyalbum.domain.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _data = MutableStateFlow<DataResponse<CharacterItem>>(DataResponse.loading())
    val data: StateFlow<DataResponse<CharacterItem>> = _data

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            characterRepository.getCharacter(id).collectLatest {
               _data.value = it
            }
        }
    }
}