package com.example.mockito

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mockito.data.Repository
import com.example.mockito.data.UserService
import com.example.mockito.util.LiveDataResult
import io.reactivex.Maybe
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userService: UserService
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.mainViewModel = MainViewModel(this.userService)
    }

    @Test
    fun fetchRepositories_positiveResponse() {
        Mockito.`when`(this.userService.getRepositories(ArgumentMatchers.anyString())).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.anyList<Repository>())
        }

        val observer = mock(Observer::class.java) as Observer<LiveDataResult<List<Repository>>>
        this.mainViewModel.repositoriesLiveData.observeForever(observer)

        this.mainViewModel.fetchUserRepositories(ArgumentMatchers.anyString())

        assertNotNull(this.mainViewModel.repositoriesLiveData.value)
        assertEquals(LiveDataResult.Status.SUCCESS, this.mainViewModel.repositoriesLiveData.value?.status)
    }
}