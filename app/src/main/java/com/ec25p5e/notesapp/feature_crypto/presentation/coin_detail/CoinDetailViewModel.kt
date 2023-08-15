package com.ec25p5e.notesapp.feature_crypto.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.feature_crypto.domain.use_case.CoinUseCases
import com.ec25p5e.notesapp.feature_crypto.domain.use_case.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinUseCase: CoinUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>("coinId")?.let { coinId ->
            coinUseCase.getCoinUseCase(coinId).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CoinDetailState(coin = result.data)
                    }
                    is Resource.Error -> {
                        _state.value = CoinDetailState(
                            error = result.uiText.toString()  ?: "An unexpected error occured"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = CoinDetailState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}