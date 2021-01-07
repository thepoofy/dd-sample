package com.thepoofy.sample.features.main_activity

import io.reactivex.rxjava3.core.Single

interface DataListRepository {

    fun getData(): Single<List<String>>
}