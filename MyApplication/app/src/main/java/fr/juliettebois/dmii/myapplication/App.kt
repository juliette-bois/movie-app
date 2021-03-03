package fr.juliettebois.dmii.myapplication

import android.app.Application
import fr.juliettebois.dmii.myapplication.repositories.CategoryRepository
import fr.juliettebois.dmii.myapplication.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App : Application() {
    private val viewModelModule = module {
        viewModel {
            HomeViewModel(get())
        }
    }

    private val categoryModule = module {
        single {
            CategoryRepository()
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(viewModelModule, categoryModule))
        }
    }
}
