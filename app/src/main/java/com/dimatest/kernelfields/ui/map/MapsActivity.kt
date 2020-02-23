package com.dimatest.kernelfields.ui.map

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.dimatest.kernelfields.R
import com.dimatest.kernelfields.common.BaseActivity
import com.dimatest.kernelfields.database.entities.FieldDO
import com.dimatest.kernelfields.databinding.ActivityMapsBinding
import com.dimatest.kernelfields.ui.fields.FieldsActivity.Companion.FIELD_EXTRA_KEY

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    override fun getLayoutRes() = R.layout.activity_maps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val field = intent.extras?.get(FIELD_EXTRA_KEY) as FieldDO?
        field?.let {
            val minX = it.minX ?: DEFAULT_DOUBLE_VALUE
            val maxX = it.maxX ?: DEFAULT_DOUBLE_VALUE
            val minY = it.minY ?: DEFAULT_DOUBLE_VALUE
            val maxY = it.maxY ?: DEFAULT_DOUBLE_VALUE
            val centre = LatLng((maxY + minY) / 2, (maxX + minX) / 2)
            googleMap.apply {
                uiSettings.isZoomControlsEnabled = true
                uiSettings.isMapToolbarEnabled = false
                addMarker(MarkerOptions().position(centre).title(it.farmName))
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
                        ContextCompat.getColor(this@MapsActivity, R.color.polygon_stroke_color)
                    fillColor =
                        ContextCompat.getColor(this@MapsActivity, R.color.polygon_fill_color)
                }

                moveCamera(CameraUpdateFactory.newLatLngZoom(centre, getZoom(it.area)))
            }
        }
    }

    private fun getZoom(area: Double?) : Float {
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

    companion object {
        private const val POLYGON_STROKE_WIDTH = 4f
        private const val DEFAULT_DOUBLE_VALUE = 0.0
    }
}
