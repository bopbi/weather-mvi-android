package com.bobbyprabowo.weathernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.androidx.lifecycle.autoDispose
import autodispose2.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

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
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                render(it)
            }, {

            })

        viewModel.processIntents(Observable.merge(
            Observable.just(MainViewModel.MainIntent.InitialIntent),
            Observable.just(MainViewModel.MainIntent.LoadIntent),
        ))
    }

    private fun render(viewState: MainViewModel.MainViewState) {
        Toast.makeText(this, viewState.weathers.toString(), Toast.LENGTH_LONG).show()
    }
}
