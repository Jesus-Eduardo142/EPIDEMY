package com.example.user.vigilante.ciudadano.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import mx.com.epivector.R
import java.util.*

class AltaPacienteFragment : Fragment() {


    lateinit var mapFragment : SupportMapFragment
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    var bandera = true
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    var latitud= 0.00
    var longitud = 0.00
    lateinit var txtDireccion : EditText


    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_alta_paciente,container,false)
        //inflater.inflate(R.layout.fragment_alta_paciente, container, false)






        activity!!.title = "Registro"

        mapFragment = childFragmentManager.findFragmentById(R.id.fragment_registro_map) as SupportMapFragment


        txtDireccion = view.findViewById<EditText>(R.id.edtDireccion)



        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getLocation()
            } else {
                Toast.makeText(context, "No tiene permisos", Toast.LENGTH_LONG).show()
            }
        }catch (ex:Exception){
            Toast.makeText(context, "No tiene permisos" + ex, Toast.LENGTH_LONG).show()

        }




        return view

    }



    @SuppressLint("MissingPermission")
    private fun getLocation() {



        locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {




            if (hasGps) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationGps = location

                            latitud = locationGps!!.latitude
                            longitud = locationGps!!.longitude

                            if(bandera) {

                                println("puto1" + latitud + " " + longitud)
                                refresMap(latitud, longitud)
                                getAddress(latitud,longitud)
                            }

                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if (hasNetwork) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationNetwork = location

                            latitud = locationNetwork!!.latitude
                            longitud = locationNetwork!!.longitude

                            if(bandera) {

                                println("puto2" + latitud + " " + longitud)
                                refresMap(latitud, longitud)
                                getAddress(latitud,longitud)
                            }

                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })

                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }

            if(locationGps!= null && locationNetwork!= null){
                if(locationGps!!.accuracy > locationNetwork!!.accuracy){
                    // tv_result.append("\nNetwork ")
                    // tv_result.append("\nLatitude : " + locationNetwork!!.latitude)
                    // tv_result.append("\nLongitude : " + locationNetwork!!.longitude)

                    latitud = locationNetwork!!.latitude
                    longitud = locationNetwork!!.longitude

                    if(bandera) {

                        println("puto3" + latitud + " " + longitud)
                        refresMap(latitud, longitud)
                        getAddress(latitud,longitud)
                    }


                }else{

                    latitud = locationGps!!.latitude
                    longitud = locationGps!!.longitude

                    if(bandera) {

                        println("puto4" + latitud + " " + longitud)
                        refresMap(latitud, longitud)
                        getAddress(latitud,longitud)

                    }


                }
            }

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }


    }

    @SuppressLint("MissingPermission")
    private fun refresMap(latitud: Double, longitud: Double) {
        mapFragment.getMapAsync { mMap ->
            mMap!!.isMyLocationEnabled = true
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
            mMap.clear() //clear old markers
            val googlePlex = CameraPosition.builder()
                    .target(LatLng(latitud,longitud))
                    .zoom(18F)
                    .bearing(0F)
                    .tilt(40F)
                    .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null)

        }


        bandera=false
    }


    fun getAddress(latitud: Double, longitud: Double) {
    try {


        val geocoder: Geocoder
        val addresses:List<Address>
        geocoder = Geocoder(this.context, Locale.getDefault())
        addresses = geocoder.getFromLocation(latitud, longitud, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        val address = addresses.get(0).getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        val city = addresses.get(0).getLocality()
        val state = addresses.get(0).getAdminArea()
        val country = addresses.get(0).getCountryName()
        val postalCode = addresses.get(0).getPostalCode()
        val knownName = addresses.get(0).getFeatureName() // Only if available else return NULL}

        txtDireccion.setText(address,TextView.BufferType.EDITABLE)
    }catch (ex:Exception){
        println("error get address" + ex)

    }

    }





    fun onMainFragmentInteraction() {


        try {


        }catch (exe:Exception){
            println("error cambio de fragment $exe")

        }

    }






}