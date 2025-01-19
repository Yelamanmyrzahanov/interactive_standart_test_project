package com.example.interactivestandarttest.presentation.chart

import android.graphics.Bitmap
import com.example.interactivestandarttest.domain.model.Point
import com.example.interactivestandarttest.domain.usecase.SaveChartToFileUseCase
import com.example.interactivestandarttest.presentation.utils.FileUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ChartPresenter @Inject constructor(
    private val saveChartToFileUseCase: SaveChartToFileUseCase
) : ChartContract.IPresenter {

    private var view: ChartContract.IView? = null
    private val disposables = CompositeDisposable()

    override fun onAttachView(view: ChartContract.IView) {
        this.view = view
    }

    override fun onDetachView() {
        disposables.clear()
        view = null
    }

    override fun onSaveChartButtonClicked() {
        view?.requestStoragePermission()
    }

    override fun onStoragePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            val bitmap = view?.captureChartAsBitmap()
            if (bitmap != null) {
                saveChart(bitmap)
            }
        } else {
            view?.showPermissionDeniedMessage()
        }
    }

    override fun prepareChartData(points: List<Point>) {
        view?.showLoading(true)
        view?.renderChart(points)
        view?.showLoading(false)
    }

    private fun saveChart(bitmap: Bitmap) {
        val data = FileUtils.bitmapToByteArray(bitmap)
        view?.showLoading(true)
        val disposable = saveChartToFileUseCase.execute(data, "chart_${System.currentTimeMillis()}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { view?.showLoading(false) }
            .subscribe({ success ->
                if (success) {
                    view?.showChartSavedMessage()
                } else {
                    view?.showChartSaveError()
                }
            }, { error ->
                error.printStackTrace()
                view?.showChartSaveError()
            })

        disposables.add(disposable)
    }
}