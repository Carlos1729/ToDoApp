package com.example.todotestapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todotestapp.data.api.ToDoApi
import com.example.todotestapp.data.db.BaseListToDoResponse
import java.lang.Exception


const val STARTING_PAGE_INDEX = 1


class ListToDoPagingSource(private val toDoApi: ToDoApi,private val role: String, private val id: Int,  private val status: String?,
                           private val priority: String?,
                           private val orderBy: String?,
                           private val sort: String?) : PagingSource<Int,BaseListToDoResponse>()  {
    override fun getRefreshKey(state: PagingState<Int, BaseListToDoResponse>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BaseListToDoResponse> {
        return try {
            val positionPageNo = params.key ?: STARTING_PAGE_INDEX
            val positionResponse = toDoApi.listToDoPagination(role = role, id = id, pageNo = positionPageNo, status = status,priority = priority,orderBy = orderBy, sort = sort)
            LoadResult.Page(
                data = positionResponse.body()!!.tasks,
                prevKey = if (positionPageNo == STARTING_PAGE_INDEX) null else positionPageNo - 1,
                nextKey = if(positionPageNo == positionResponse.body()!!.totalPage) null else positionPageNo + 1
            )

        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}