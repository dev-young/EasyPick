package io.ymsoft.easypick

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class EasyPickApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(MyDebugTree())
    }
}

class MyDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format(
            "[C:%s] [L:%s] [M:%s] ",
            super.createStackElementTag(element),
            element.lineNumber,
            element.methodName
        )
    }
}