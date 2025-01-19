package com.example.interactivestandarttest.presentation.main

import com.example.interactivestandarttest.domain.usecase.GetPointsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase
) : MainContract.IPresenter {

    private var view: MainContract.IView? = null
    private val disposables = CompositeDisposable()

    override fun attachView(view: MainContract.IView) {
        this.view = view
    }

    override fun detachView() {
        disposables.clear()
    }

    override fun onFetchPoints(count: Int) {
        view?.showLoading(true)
        val disposable = getPointsUseCase.execute(count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ points ->
                view?.showLoading(false)
                view?.showPoints(points)
            }, { error ->
                view?.showLoading(false)
                view?.showError(error.message ?: "Unknown Error")
            })
        disposables.add(disposable)
    }
}