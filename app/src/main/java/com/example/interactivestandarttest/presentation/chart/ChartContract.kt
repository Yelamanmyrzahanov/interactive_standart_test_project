package com.example.interactivestandarttest.presentation.chart

import android.graphics.Bitmap
import com.example.interactivestandarttest.domain.model.Point

class ChartContract {
    interface IView {
        fun requestStoragePermission()
        fun showPermissionDeniedMessage()
        fun captureChartAsBitmap(): Bitmap
        fun showChartSavedMessage()
        fun showChartSaveError()
        fun renderChart(points: List<Point>)
        fun showLoading(isLoading: Boolean)
    }

    interface IPresenter {
        fun onAttachView(view: IView)
        fun onDetachView()
        fun onSaveChartButtonClicked()
        fun onStoragePermissionResult(isGranted: Boolean)
        fun prepareChartData(points: List<Point>)
    }
}