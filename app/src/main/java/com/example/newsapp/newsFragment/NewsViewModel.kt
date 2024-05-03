package com.example.newsapp.newsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.MessageView
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.newsResponse.Article
import com.example.newsapp.api.model.newsResponse.NewsResponse
import com.example.newsapp.api.model.sourcesResponse.Source
import com.example.newsapp.api.model.sourcesResponse.SourcesResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel : ViewModel() {

    val isLoadingVisible = MutableLiveData<Boolean>()
    val message = MutableLiveData<MessageView>()
    val newsLiveData = MutableLiveData<List<Article?>?>()

    fun loadNews(source: Source) {
        isLoadingVisible.value = true
        source.id?.let { sourceId ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = ApiManager.getServices().getNews(sources = sourceId)
                    newsLiveData.postValue(response.articles)
                } catch (ex: HttpException) {
                    val responseJson = ex.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(
                        responseJson,
                        NewsResponse::class.java
                    )
                    message.postValue(
                        MessageView(
                            message = errorResponse.message ?: "Error"
                        )
                    )
                } catch (ex: Exception) {
                    message.postValue(
                        MessageView(
                            message = ex.message ?: "Error"
                        )
                    )
                } finally {
                    isLoadingVisible.postValue(false)
                }
            }
        }


    }

    fun loadArticles(query: String) {
        isLoadingVisible.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiManager.getServices().getSearchArticles(query = query)
                newsLiveData.postValue(response.articles)
            } catch (ex: HttpException) {
                val responseJson = ex.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(
                    responseJson,
                    SourcesResponse::class.java
                )
                message.postValue(
                    MessageView(
                        message = errorResponse.message ?: "Error"
                    )
                )
            } catch (ex: Exception) {
                message.postValue(
                    MessageView(
                        message = ex.message ?: "Error"
                    )
                )
            } finally {
                isLoadingVisible.postValue(false)
            }

        }

    }

}