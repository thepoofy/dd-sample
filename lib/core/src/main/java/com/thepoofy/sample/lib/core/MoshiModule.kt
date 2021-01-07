package com.thepoofy.sample.lib.core

import com.squareup.moshi.Moshi
import dagger.Provides

@dagger.Module
class MoshiModule {

    @Provides
    fun moshi(): Moshi {
        return Moshi.Builder()
            .build()
    }
}