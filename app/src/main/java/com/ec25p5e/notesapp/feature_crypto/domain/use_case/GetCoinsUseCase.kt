package com.ec25p5e.notesapp.feature_crypto.domain.use_case

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_crypto.data.mapper.toCoin
import com.ec25p5e.notesapp.feature_crypto.domain.model.Coin
import com.ec25p5e.notesapp.feature_crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCoinsUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading<List<Coin>>())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success<List<Coin>>(coins))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Coin>>(UiText.DynamicString(e.localizedMessage ?: "An unexpected error occured")))
        } catch(e: IOException) {
            emit(Resource.Error<List<Coin>>(UiText.DynamicString("Couldn't reach server. Check your internet connection.")))
        }
    }
}