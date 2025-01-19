package com.example.interactivestandarttest.presentation.main

import com.example.interactivestandarttest.domain.model.Point

interface MainContract {
    interface IView {
        fun showPoints(points: List<Point>)
        fun showError(message: String)
        fun showLoading(isLoading: Boolean)
    }

    interface IPresenter{
        fun attachView(view: IView)
        fun detachView()
        fun onFetchPoints(count: Int)
    }
}