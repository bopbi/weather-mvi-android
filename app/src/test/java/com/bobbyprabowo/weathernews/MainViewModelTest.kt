package com.bobbyprabowo.weathernews

import com.bobbyprabowo.weathernews.domain.FetchWeather
import com.bobbyprabowo.weathernews.domain.LoadWeather
import com.bobbyprabowo.weathernews.fake.FakeBuilder
import com.bobbyprabowo.weathernews.rule.RxImmediateSchedulerListener
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class MainViewModelTest : FunSpec() {

    @MockK
    private lateinit var mockedFetchWeather: FetchWeather

    @MockK
    private lateinit var mockedLoadWeather: LoadWeather

    private lateinit var viewModel: MainViewModel

    private fun initViewModel() {
        viewModel = MainViewModel(mockedFetchWeather, mockedLoadWeather)
    }

    override fun beforeEach(testCase: TestCase) {
        super.beforeEach(testCase)
        MockKAnnotations.init(this)
    }

    init {
        extension(RxImmediateSchedulerListener)

        test("default state should be idle") {
            initViewModel()
            val testObserver = viewModel.states().test()
            testObserver.assertValue(MainViewModel.MainViewState.idle())
        }

        context("initial load") {

            context("when fetch weathers emit expectedWeathers") {
                initViewModel()
                val testObserver = viewModel.states().test()

                val expectedWeathers = listOf(FakeBuilder.createFakeWeather())
                every { mockedFetchWeather() }.returns(Single.just(expectedWeathers))

                viewModel.processIntents(Observable.just(MainViewModel.MainIntent.InitialIntent))

                test("default state should be idle") {
                    testObserver.assertValueAt(0) { viewState ->
                        viewState == MainViewModel.MainViewState.idle()
                    }
                }

                test("followed by loading") {
                    testObserver.assertValueAt(1) { viewState ->
                        viewState.isLoading &&
                            viewState.weathers.isEmpty()
                    }
                }

                test("state contain data and loading is false") {
                    testObserver.assertValueAt(2) { viewState ->
                        !viewState.isLoading &&
                            viewState.weathers == expectedWeathers
                    }
                }
            }

            context("when fetch weathers emit error") {
                initViewModel()
                val testObserver = viewModel.states().test()

                every { mockedFetchWeather() }.returns(Single.error(Throwable()))

                viewModel.processIntents(Observable.just(MainViewModel.MainIntent.InitialIntent))

                test("default state should be idle") {
                    testObserver.assertValueAt(0) { viewState ->
                        viewState == MainViewModel.MainViewState.idle()
                    }
                }

                test("followed by loading") {
                    testObserver.assertValueAt(1) { viewState ->
                        viewState.isLoading &&
                            viewState.weathers.isEmpty()
                    }
                }

                test("state have error, not contain data and loading is false") {
                    testObserver.assertValueAt(2) { viewState ->
                        !viewState.isLoading &&
                            viewState.weathers.isEmpty() &&
                            viewState.isError
                    }
                }
            }
        }

    }
}
