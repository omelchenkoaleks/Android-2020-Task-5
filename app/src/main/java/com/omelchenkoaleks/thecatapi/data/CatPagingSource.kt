package com.omelchenkoaleks.thecatapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omelchenkoaleks.thecatapi.api.CatApi
import retrofit2.HttpException
import java.io.IOException

class CatPagingSource(val catApi: CatApi) : PagingSource<Int, Cat>() {

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        val page = params.key ?: 1
        return try {
            val response = catApi.getCats(page, params.loadSize)
            LoadResult.Page(
                response, prevKey = if (page == 1) null else page + 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
