package com.ec25p5e.notesapp.feature_crypto.domain.use_case

import com.ec25p5e.notesapp.core.util.Resource
import com.ec25p5e.notesapp.core.util.UiText
import com.ec25p5e.notesapp.feature_crypto.data.mapper.toCoinDetail
import com.ec25p5e.notesapp.feature_crypto.domain.model.CoinDetail
import com.ec25p5e.notesapp.feature_crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetCoinUseCase(
    private val repository: CoinRepository
) {

    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading<CoinDetail>())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success<CoinDetail>(coin))
        } catch(e: HttpException) {
            emit(Resource.Error<CoinDetail>(UiText.DynamicString(e.localizedMessage ?: "An unexpected error occured")))
        } catch(e: IOException) {
            emit(Resource.Error<CoinDetail>(UiText.DynamicString("Couldn't reach server. Check your internet connection.")))
        }
    }
}