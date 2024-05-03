package com.example.newsapp.newsSources

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.MessageView
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.sourcesResponse.Source
import com.example.newsapp.api.model.sourcesResponse.SourcesResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SourcesViewModel : ViewModel() {

    val isLoadingVisible = MutableLiveData<Boolean>()
    val message = MutableLiveData<MessageView>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()

    fun getNewsSources(category: String) {
        isLoadingVisible.value = true
        category.let { selectedCategory ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response =
                        ApiManager.getServices().getNewsSources(category = selectedCategory)
                    sourcesLiveData.postValue(response.sources)
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

}