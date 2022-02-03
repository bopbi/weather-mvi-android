package com.bobbyprabowo.weathernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.androidx.lifecycle.autoDispose
import autodispose2.autoDispose
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val scopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.states()
            .autoDispose(scopeProvider)
            .subscribe({
                render(it)
            }, {

            })
    }

    fun render(viewState: MainViewModel.MainViewState) {

    }
}
