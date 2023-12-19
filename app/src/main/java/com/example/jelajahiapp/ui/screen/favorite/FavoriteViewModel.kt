package com.example.jelajahiapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.location.PlaceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: JelajahiRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<Result<List<PlaceResult>>> =
        MutableStateFlow(Result.Loading)
    val uiState: StateFlow<Result<List<PlaceResult>>>
        get() = _uiState

    fun getallFavoritePlaces() {
        viewModelScope.launch {
            repository.getAllFavoritePlaces()
                .catch {
                    _uiState.value = Result.Error(it.message.toString())
                }

                .collect { favDogList ->
                    _uiState.value = Result.Success(favDogList)
                }
        }
    }
}