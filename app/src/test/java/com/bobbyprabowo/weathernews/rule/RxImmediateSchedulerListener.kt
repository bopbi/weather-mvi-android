package com.bobbyprabowo.weathernews.rule

import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by t-bobby.prabowo on 05,February,2022
 */
object RxImmediateSchedulerListener : BeforeTestListener, AfterTestListener {

    override suspend fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)

        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}
