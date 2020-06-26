package com.example.mockito

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mockito.data.Repository
import com.example.mockito.data.UserService
import com.example.mockito.util.LiveDataResult
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable

class MainViewModel (private val userService: UserService) : ViewModel() {

    val repositoriesLiveData = MutableLiveData<LiveDataResult<List<Repository>>>()

    val loadingLiveData = MutableLiveData<Boolean>()

    /**
     * Request user's repositories
     * @param githubUser Github usename
     */
    fun fetchUserRepositories(githubUser: String) {
        this.setLoadingVisibility(true)
        this.userService.getRepositories(githubUser).subscribe(GetRepositoriesConsumer())
    }

    /**
     * Set a progress dialog visible on the View
     * @param visible visible or not visible
     */
    fun setLoadingVisibility(visible: Boolean) {
        loadingLiveData.postValue(visible)
    }

    /**
     * userService.getRepositories() Observer
     */
    inner class GetRepositoriesConsumer : MaybeObserver<List<Repository>> {
        override fun onSubscribe(d: Disposable) {
            this@MainViewModel.repositoriesLiveData.postValue(LiveDataResult.loading())
        }

        override fun onError(e: Throwable) {
            this@MainViewModel.repositoriesLiveData.postValue(LiveDataResult.error(e))
            this@MainViewModel.setLoadingVisibility(false)
        }

        override fun onSuccess(t: List<Repository>) {
            this@MainViewModel.repositoriesLiveData.postValue(LiveDataResult.succes(t))
            this@MainViewModel.setLoadingVisibility(false)
        }

        override fun onComplete() {
            this@MainViewModel.setLoadingVisibility(false)
        }

    }
}