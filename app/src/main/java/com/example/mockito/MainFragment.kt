package com.example.mockito

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mockito.data.Repository
import com.example.mockito.data.UserService
import com.example.mockito.util.LiveDataResult

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private val dataObserver = Observer<LiveDataResult<List<Repository>>> { result ->
        when (result?.status) {
            LiveDataResult.Status.LOADING -> {
                // Loading data
            }

            LiveDataResult.Status.ERROR -> {
                // Error for http request
            }

            LiveDataResult.Status.SUCCESS -> {
                // Data from API
            }
        }
    }

    private val loadingObserver = Observer<Boolean> { visibile ->
        // Show hide a progress
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ViewModel factory
        val factory = MainViewModelFactory((activity?.application as GithubApplication).retrofit.create(
            UserService::class.java))

        // Create ViewModel and bind observer
        this.viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        this.viewModel.repositoriesLiveData.observe(this, this.dataObserver)
        this.viewModel.loadingLiveData.observe(this, this.loadingObserver)
    }
}