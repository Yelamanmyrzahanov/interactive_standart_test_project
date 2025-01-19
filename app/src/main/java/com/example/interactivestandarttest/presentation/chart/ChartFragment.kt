package com.example.interactivestandarttest.presentation.chart

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.MarkerType
import com.example.interactivestandarttest.MyApplication
import com.example.interactivestandarttest.R
import com.example.interactivestandarttest.domain.model.Point
import com.example.interactivestandarttest.presentation.utils.PermissionUtils
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class ChartFragment : Fragment(), ChartContract.IView {

    companion object {
        private const val MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 10146

        fun newInstance(points: List<Point>): ChartFragment {
            val fragment = ChartFragment()
            val args = Bundle()
            args.putParcelableArrayList("points", ArrayList(points))
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var presenter: ChartPresenter
    private val disposables = CompositeDisposable() // Manage RxJava subscriptions

    private lateinit var anyChartView: AnyChartView
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonSaveChart: Button

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            presenter.onStoragePermissionResult(isGranted)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        anyChartView = view.findViewById(R.id.anyChartView)
        buttonSaveChart = view.findViewById(R.id.buttonSaveChart)
        progressBar = view.findViewById(R.id.progressBar)

        APIlib.getInstance().setActiveAnyChartView(anyChartView)
        val points: List<Point>? = arguments?.getParcelableArrayList<Point>("points")

        (requireActivity().application as MyApplication).appComponent.inject(this)

        presenter.onAttachView(this)

        buttonSaveChart.setOnClickListener {
            presenter.onSaveChartButtonClicked()
        }

        // Отображение графика
        points?.let {
            presenter.prepareChartData(it)
        }
    }

    override fun renderChart(points: List<Point>) {
        val cartesian = AnyChart.line()

        cartesian.title("График точек")
        cartesian.xAxis(0).title("X")
        cartesian.yAxis(0).title("Y")

        val data: MutableList<DataEntry> = points.sortedBy { it.x }.map {
            ValueDataEntry(it.x, it.y)
        }.toMutableList()

        cartesian.data(data)
        cartesian.xScroller(true)
        cartesian.xZoom(true)

        // Configure markers
        val line = cartesian.line(data)
        line.markers()
            .enabled(true)               // Enable markers
            .type(MarkerType.CIRCLE)     // Marker shape (CIRCLE, SQUARE, etc.)
            .size(5.0)                   // Marker size
            .fill("#FF0000")             // Marker fill color
            .stroke("#000000")           // Marker border color

        anyChartView.setChart(cartesian)
    }

    override fun requestStoragePermission() {
        if (PermissionUtils.isStoragePermissionGranted(requireContext())) {
            presenter.onStoragePermissionResult(true)
        } else {
            PermissionUtils.requestStoragePermission(
                context = requireContext(),
                requestPermissionLauncher = requestPermissionLauncher,
                onShowRationale = {
                    Toast.makeText(
                        requireContext(),
                        "Storage permission is required to save the chart.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    override fun captureChartAsBitmap(): Bitmap {
        val bitmap =
            Bitmap.createBitmap(anyChartView.width, anyChartView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        anyChartView.draw(canvas)
        return bitmap
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.isVisible = isLoading
    }

    override fun showChartSavedMessage() {
        Toast.makeText(requireContext(), "Chart saved successfully!", Toast.LENGTH_SHORT).show()
    }

    override fun showChartSaveError() {
        Toast.makeText(requireContext(), "Failed to save chart.", Toast.LENGTH_SHORT).show()
    }

    override fun showPermissionDeniedMessage() {
        Toast.makeText(requireContext(), "Permission denied.", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        presenter.onDetachView()
        disposables.clear()
        super.onDestroyView()
    }
}
