package com.bobbyprabowo.weathernews.domain.impl

import com.bobbyprabowo.weathernews.repository.AnalyticsRepository
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class LogClickImplTest : FunSpec() {

    private val mockedAnalyticsRepository: AnalyticsRepository = mockk()

    private val logClickImpl: LogClickImpl = LogClickImpl(mockedAnalyticsRepository)

    init {
        context("when use case invoked") {

            context("sendEventClicked did not return error") {

                every { mockedAnalyticsRepository.sendEventClicked() }.returns( Unit )

                logClickImpl()

                test("it should call analytics send clicked") {
                    verify { mockedAnalyticsRepository.sendEventClicked() }
                }

            }

        }
    }

}
