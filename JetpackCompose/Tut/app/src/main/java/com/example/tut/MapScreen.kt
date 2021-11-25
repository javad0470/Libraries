package com.example.tut

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.osmdroid.config.Configuration.*
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import org.osmdroid.views.MapView

class MapScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION
        ).build().send {
            if (it.allGranted()) {
                Toast.makeText(requireContext(), "All Granted", Toast.LENGTH_SHORT).show()
            }
        }

        getInstance().load(
            requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //val context = container!!.context
        val view = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.map, container, false)


        val map = view.findViewById<MapView>(R.id.map)

        return view
    }
}