package no.brauterfallet.myapplication

import no.brauterfallet.myapplication.di.appModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class CheckModulesTest : KoinTest {
    @Test
    fun checkAllModules() {
        appModule.verify()
    }
}
