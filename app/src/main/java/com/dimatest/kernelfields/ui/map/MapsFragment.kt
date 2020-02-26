package com.dimatest.kernelfields.ui.map

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.dimatest.kernelfields.R
import com.dimatest.kernelfields.common.BaseFragment
import com.dimatest.kernelfields.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsFragment : BaseFragment<MapsViewModel, FragmentMapsBinding>(), OnMapReadyCallback {

    override fun getLayoutRes() = R.layout.fragment_maps
    override val viewModel: MapsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.onCreate(savedInstanceState)
        map.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        arguments?.let {
            val field = MapsFragmentArgs.fromBundle(it).field
            val minX = field.minX ?: DEFAULT_DOUBLE_VALUE
            val maxX = field.maxX ?: DEFAULT_DOUBLE_VALUE
            val minY = field.minY ?: DEFAULT_DOUBLE_VALUE
            val maxY = field.maxY ?: DEFAULT_DOUBLE_VALUE
            val centre = LatLng((maxY + minY) / 2, (maxX + minX) / 2)

            googleMap.apply {
                uiSettings.isZoomControlsEnabled = true
                uiSettings.isMapToolbarEnabled = false
                addMarker(MarkerOptions().position(centre).title(field.farmName))
                addPolygon(
                    PolygonOptions()
                        .add(
                            LatLng(minY, minX),
                            LatLng(minY, maxX),
                            LatLng(maxY, maxX),
                            LatLng(maxY, minX)
                        )
                ).apply {
                    isClickable = true
                    strokeWidth =
                        POLYGON_STROKE_WIDTH
                    strokeColor =
                        ContextCompat.getColor(requireContext(), R.color.polygon_stroke_color)
                    fillColor =
                        ContextCompat.getColor(requireContext(), R.color.polygon_fill_color)
                }

                moveCamera(CameraUpdateFactory.newLatLngZoom(centre, getZoom(field.area)))
            }

        }
    }

    private fun getZoom(area: Double?): Float {
        return if (area != null) {
            when {
                area < 20.0 -> 15f
                area < 50.0 -> 14f
                area < 75.0 -> 13f
                area < 110.0 -> 12f
                else -> 10f
            }
        } else 0f
    }

    override fun onResume() {
        map.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map?.onDestroy()
    }

    companion object {
        private const val POLYGON_STROKE_WIDTH = 4f
        private const val DEFAULT_DOUBLE_VALUE = 0.0
    }

}
