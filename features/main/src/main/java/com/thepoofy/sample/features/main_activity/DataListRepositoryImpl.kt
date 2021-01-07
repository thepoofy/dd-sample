package com.thepoofy.sample.features.main_activity

import com.squareup.moshi.Types
import com.thepoofy.sample.features.main_activity.storage.FileStorage
import com.thepoofy.sample.lib.api.SampleApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class DataListRepositoryImpl constructor(
    private val api: SampleApi,
    private val fileStorage: FileStorage,
) : DataListRepository {

    private val cache = BehaviorSubject.create<List<String>>()

    override fun getData(): Single<List<String>> {
        return readFromDisk()
            .ambWith(getFromCache())
            .ambWith(getFromApi())
            .singleOrError()
    }

    private fun getFromApi(): Observable<List<String>> {
        return api.getData()
            .doOnSuccess {
                writeToDisk(it)
                cache.onNext(it)
            }
            .map { it }
            .toObservable()
    }

    private fun getFromCache(): Observable<List<String>> {
        return if (cache.hasValue()) {
            cache
        } else {
            Observable.never()
        }
    }

    private fun readFromDisk(): Observable<List<String>> {
        val listOptional = Optional.ofNullable(
            fileStorage.read<List<String>>(
                DATA_LIST_FILE_NAME,
                Types.newParameterizedType(List::class.java, String::class.java)
            )
        )

        return if (listOptional.isPresent) {
            Observable.just(listOptional.get())
        } else {
            Observable.never()
        }
    }

    private fun writeToDisk(data: List<String>) {
        return fileStorage.write(
            DATA_LIST_FILE_NAME,
            data,
            Types.newParameterizedType(List::class.java, String::class.java)
        )
    }

    companion object {
        private const val DATA_LIST_FILE_NAME = "data.json"
    }
}