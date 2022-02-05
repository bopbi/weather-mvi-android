package com.bobbyprabowo.weathernews.domain

import io.reactivex.rxjava3.core.Completable

/**
 * Created by t-bobby.prabowo on 05,February,2022
 */
interface SyncData {

    operator fun invoke(): Completable
}
