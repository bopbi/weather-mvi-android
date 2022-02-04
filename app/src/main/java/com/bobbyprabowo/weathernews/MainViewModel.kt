package com.bobbyprabowo.weathernews

import androidx.lifecycle.ViewModel
import com.bobbyprabowo.weathernews.domain.FetchWeather
import com.bobbyprabowo.weathernews.domain.LoadWeather
import com.bobbyprabowo.weathernews.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchWeather: FetchWeather,
    private val loadWeather: LoadWeather
) : ViewModel() {

    data class MainViewState(
        val isInitialLoad: Boolean,
        val isLoading: Boolean,
        val isError: Boolean,
        val weathers: List<Weather>
    ) {
        companion object {
            fun idle(): MainViewState {
                return MainViewState(
                    isInitialLoad = false,
                    isLoading = false,
                    isError = false,
                    weathers = emptyList()
                )
            }
        }
    }

    sealed class MainViewEffect {
        object Hurray : MainViewEffect()
        object Sad : MainViewEffect()
    }

    sealed class MainIntent {
        object InitialIntent : MainIntent()
        object RefreshIntent : MainIntent()
        object LoadIntent : MainIntent()
    }

    sealed class MainAction {
        data class RefreshAction(val isInitial: Boolean) : MainAction()
        object LoadAction : MainAction()
    }

    sealed class MainResult {
        sealed class FetchResult : MainResult() {
            object Loading : FetchResult()
            data class Fail(val throwable: Throwable) : FetchResult()
            data class Success(val weathers: List<Weather>) : FetchResult()
        }

        sealed class LoadResult : MainResult() {
            object Loading : LoadResult()
            data class Fail(val throwable: Throwable) : LoadResult()
            data class Success(val weathers: List<Weather>) : LoadResult()
        }
    }

    private val intentsSubject: PublishSubject<MainIntent> = PublishSubject.create()
    private val effectsSubject: PublishSubject<MainViewEffect> = PublishSubject.create()

    private val statesObservable: Observable<MainViewState> = compose()

    private val intentFilter: ObservableTransformer<MainIntent, MainIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                    listOf(
                        shared.ofType(MainIntent.InitialIntent::class.java).take(1),
                        shared.ofType(MainIntent.RefreshIntent::class.java),
                        shared.ofType(MainIntent.LoadIntent::class.java)
                    )
                )
            }
        }

    private val actionProcessor: ObservableTransformer<MainAction, MainResult>
        get() = ObservableTransformer { actions ->
            actions.publish { shared ->
                Observable.merge(
                    listOf(
                        shared.ofType(MainAction.RefreshAction::class.java)
                            .compose(refreshProcessor),
                        shared.ofType(MainAction.LoadAction::class.java).compose(loadProcessor)
                    )
                ).cast(MainResult::class.java)
            }
        }

    private val refreshProcessor: ObservableTransformer<MainAction.RefreshAction, MainResult.FetchResult>
        get() = ObservableTransformer { actions ->
            actions.flatMap {
                fetchWeather()
                    .map { weathers ->
                        MainResult.FetchResult.Success(weathers = weathers)
                    }
                    .delay(2000, TimeUnit.MILLISECONDS)
                    .cast(MainResult.FetchResult::class.java)
                    .toObservable()
                    .onErrorReturn(MainResult.FetchResult::Fail)
                    .startWithItem(MainResult.FetchResult.Loading)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

    private val loadProcessor: ObservableTransformer<MainAction.LoadAction, MainResult.LoadResult>
        get() = ObservableTransformer { actions ->
            actions.flatMap {
                loadWeather()
                    .map { weathers ->
                        MainResult.LoadResult.Success(weathers = weathers)
                    }
                    .cast(MainResult.LoadResult::class.java)
                    .toObservable()
                    .onErrorReturn(MainResult.LoadResult::Fail)
                    .startWithItem(MainResult.LoadResult.Loading)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

    fun processIntents(intents: Observable<MainIntent>) {
        intents.subscribe(intentsSubject)
    }

    fun effects(): Observable<MainViewEffect> = effectsSubject

    fun states(): Observable<MainViewState> = statesObservable

    private fun compose(): Observable<MainViewState> {
        return intentsSubject
            .compose(intentFilter)
            .map(this::actionFromIntent)
            .compose(actionProcessor)
            .doOnNext(this::handleEffect)
            .scan(MainViewState.idle(), this::reduce)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: MainIntent): MainAction {
        return when (intent) {
            MainIntent.InitialIntent -> MainAction.RefreshAction(isInitial = true)
            MainIntent.LoadIntent -> MainAction.LoadAction
            MainIntent.RefreshIntent -> MainAction.RefreshAction(isInitial = false)
        }
    }

    private fun reduce(currentState: MainViewState, result: MainResult): MainViewState {
        return when (result) {
            is MainResult.FetchResult.Fail -> {
                if (!currentState.isInitialLoad) {
                    currentState.copy(
                        isInitialLoad = false,
                        isLoading = false,
                        isError = true,
                    )
                } else {
                    currentState.copy(
                        isLoading = false,
                        isError = true,
                    )
                }

            }
            is MainResult.FetchResult.Loading -> {
                currentState.copy(
                    isLoading = true
                )
            }
            is MainResult.FetchResult.Success -> {
                currentState.copy(
                    isLoading = false,
                    isError = false,
                    weathers = result.weathers
                )
            }
            is MainResult.LoadResult.Fail -> {
                currentState
            }
            is MainResult.LoadResult.Loading -> {
                currentState
            }
            is MainResult.LoadResult.Success -> {
                currentState.copy(
                    weathers = result.weathers
                )
            }
        }
    }

    private fun handleEffect(result: MainResult) {
        when (result) {
            is MainResult.FetchResult.Fail -> {
                effectsSubject.onNext(MainViewEffect.Sad)
            }
            is MainResult.FetchResult.Success -> {
                effectsSubject.onNext(MainViewEffect.Hurray)
            }
            else -> { }
        }
    }
}
