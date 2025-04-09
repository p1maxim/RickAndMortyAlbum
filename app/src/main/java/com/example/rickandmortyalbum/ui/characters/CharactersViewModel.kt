package com.example.rickandmortyalbum.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyalbum.data.model.CharacterItem
import com.example.rickandmortyalbum.domain.CharacterRepository
import com.example.rickandmortyalbum.domain.ConfigDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val configDataRepository: ConfigDataRepository
) : ViewModel() {
    private val _charactersStateFlow = MutableStateFlow<PagingData<CharacterItem>>(PagingData.empty())
    val charactersStateFlow: StateFlow<PagingData<CharacterItem>> get() = _charactersStateFlow

    private val _searchStateFlow = MutableStateFlow<String>("")
    val searchStateFlow: StateFlow<String> get() = _searchStateFlow

    val useDb = configDataRepository.getUseDbFlag()

    private val _requestedSearchFlow = MutableStateFlow<String>("")
    val requestedSearchFlow: StateFlow<String> get() = _requestedSearchFlow

    init {
        fetchAllCharacters(requestedSearchFlow.value)
    }

    fun fetchAllCharacters(name: String?) {
        viewModelScope.launch {
            characterRepository.getCharacters(name).cachedIn(viewModelScope).collectLatest {
                _charactersStateFlow.value = it
            }
        }
    }

    fun onSearchClicked() {
        fetchAllCharacters(searchStateFlow.value)
        _requestedSearchFlow.value = searchStateFlow.value
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchStateFlow.update { newQuery }
    }

    fun onUseDbChanged(useDb: Boolean) {
        viewModelScope.launch {
            configDataRepository.setUseDbFlag(useDb)
        }
    }
}