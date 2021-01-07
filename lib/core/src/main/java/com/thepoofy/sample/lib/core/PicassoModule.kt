package com.thepoofy.sample.lib.core

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class PicassoModule {

    @Provides
    fun picasso(context: Context): Picasso = Picasso.Builder(context).build()
}