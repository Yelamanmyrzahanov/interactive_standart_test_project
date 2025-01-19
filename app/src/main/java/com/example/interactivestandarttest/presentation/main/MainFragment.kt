package com.example.interactivestandarttest.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.interactivestandarttest.MyApplication
import com.example.interactivestandarttest.R
import com.example.interactivestandarttest.domain.model.Point
import com.example.interactivestandarttest.presentation.chart.ChartFragment
import javax.inject.Inject

class MainFragment : Fragment(), MainContract.IView {

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var editTextCount: EditText
    private lateinit var buttonFetch: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextCount = view.findViewById(R.id.editTextCount)
        buttonFetch = view.findViewById(R.id.buttonFetch)
        progressBar = view.findViewById(R.id.progressBar)


        (activity?.application as MyApplication).appComponent.inject(this)

        presenter.attachView(this)

        buttonFetch.setOnClickListener {
            val count = editTextCount.text.toString().toIntOrNull()
            if (count != null && count > 0) {
                presenter.onFetchPoints(count)
            } else {
                showError("Введите корректное число!")
            }
        }
    }

    override fun showPoints(points: List<Point>) {
        val chartFragment = ChartFragment.newInstance(points)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, chartFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.isVisible = isLoading
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}