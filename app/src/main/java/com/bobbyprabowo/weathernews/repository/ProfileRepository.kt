package com.bobbyprabowo.weathernews.repository

import com.bobbyprabowo.weathernews.model.Profile
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by t-bobby.prabowo on 05,February,2022
 */
interface ProfileRepository {

    fun fetchOnlyProfile(): Completable

    fun fetchProfile(): Single<Profile>

    fun getProfile(): Single<Profile>
}
