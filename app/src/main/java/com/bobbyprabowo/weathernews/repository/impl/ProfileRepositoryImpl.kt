package com.bobbyprabowo.weathernews.repository.impl

import com.bobbyprabowo.weathernews.model.Profile
import com.bobbyprabowo.weathernews.repository.ProfileRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProfileRepositoryImpl(private val profile: Profile) : ProfileRepository {

    override fun fetchOnlyProfile(): Completable {
        return Completable.complete() // fake
    }

    override fun fetchProfile(): Single<Profile> {
        return fetchOnlyProfile().andThen(getProfile())
    }

    override fun getProfile(): Single<Profile> {
        return Single.just(profile)
    }
}
