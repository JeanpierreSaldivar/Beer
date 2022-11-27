package com.saldivar.ga

class NativeLib {

    /**
     * A native method that is implemented by the 'ga' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'ga' library on application startup.
        init {
            System.loadLibrary("ga")
        }
    }
}