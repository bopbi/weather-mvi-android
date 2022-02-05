package com.bobbyprabowo.weathernews.fake

import com.bobbyprabowo.weathernews.model.Weather
import kotlin.random.Random

/**
 * Created by t-bobby.prabowo on 05,February,2022
 */
class FakeBuilder {

    companion object {

        private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        private fun createRandomString(length: Int) = (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        fun createFakeWeather() = Weather(
            weatherStateName = createRandomString(1),
            predictability = Random.nextInt(0, 100)
        )
    }
}
