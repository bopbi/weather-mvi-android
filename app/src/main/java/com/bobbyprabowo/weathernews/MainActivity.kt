package com.bobbyprabowo.weathernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import com.bobbyprabowo.weathernews.databinding.ActivityMainBinding
import com.bobbyprabowo.weathernews.domain.LogClick
import com.bobbyprabowo.weathernews.domain.LogLastDataDisplayed
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val scopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var logLastDataDisplayed: LogLastDataDisplayed

    @Inject
    lateinit var logClick: LogClick

    private val interactionSubject = PublishSubject.create<MainViewModel.MainIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.effects()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                renderEffect(it)
            }, {

            })

        viewModel.states()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe({
                renderState(it)
            }, {

            })

        viewModel.processIntents(Observable.merge(
            Observable.just(MainViewModel.MainIntent.InitialIntent),
            Observable.just(MainViewModel.MainIntent.LoadIntent),
            interactionSubject
        ))

        binding.refreshButton.setOnClickListener {
            logClick()
            interactionSubject.onNext(MainViewModel.MainIntent.RefreshIntent)
        }
    }

    private fun renderEffect(viewEffect: MainViewModel.MainViewEffect) {
        when (viewEffect) {
            MainViewModel.MainViewEffect.Hurray -> {
                logLastDataDisplayed()
                Toast.makeText(this, "Hurray", Toast.LENGTH_LONG).show()
            }
            MainViewModel.MainViewEffect.Sad -> {
                Toast.makeText(this, "SAD", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderState(viewState: MainViewModel.MainViewState) {
        binding.statusTextView.text = if (viewState.isLoading) {
            "Loading"
        } else {
            ""
        }
        binding.jsonTextView.text = viewState.weathers.toString()
    }
}
