package com.dimatest.kernelfields.usecases

import com.dimatest.kernelfields.common.usecases.BaseUseCaseCompletable
import com.dimatest.kernelfields.repositories.local.FieldsLocalRepositoryInterface
import com.dimatest.kernelfields.repositories.remote.FieldsRemoteRepositoryInterface
import com.dimatest.kernelfields.utils.ErrorHandler
import io.reactivex.Completable
import io.reactivex.Scheduler

class FetchFieldsUseCase(
    private val fieldsRemoteRepository: FieldsRemoteRepositoryInterface,
    private val fieldsLocalRepository: FieldsLocalRepositoryInterface,
    mainThread: Scheduler,
    ioThread: Scheduler,
    errorHandler: ErrorHandler
) : BaseUseCaseCompletable<Unit>(mainThread, ioThread, errorHandler) {

    override fun buildUseCaseObservable(params: Unit): Completable {
        return fieldsRemoteRepository.getFieldsList()
            .flatMapCompletable { fields ->
                fieldsLocalRepository.insertNewFieldList(fields.map { it.toFieldDO() })
            }
    }
}