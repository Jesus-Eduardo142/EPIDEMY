package com.example.user.vigilante.ciudadano.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mx.com.epivector.R
import mx.com.epivector.models.ApiConfig
import mx.com.epivector.models.ApiConfig.Companion.ConvertStreamToString
import mx.com.epivector.models.dataModelPoints
import mx.com.epivector.models.sessionStorage
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.withAlpha
import java.net.HttpURLConnection
import java.net.URL


class Mapsfragment : Fragment() {


    lateinit var mapFragment : SupportMapFragment
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    var bandera = true
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    var latitud= 0.00
    var longitud = 0.00




    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_maps,container,false)
        //inflater.inflate(R.layout.fragment_alta_paciente, container, false)






        activity!!.title = "Casos de dengue"

        mapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment

        callWS()
        //getCasos()
        try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getLocation()
        } else {

        }
        }catch (ex:Exception){


        }











        return view

    }





    @SuppressLint("MissingPermission")






    fun callWS() {



        val builder = LatLngBounds.Builder()
        val urlws = "https://upheld-castle-251021.appspot.com/denguebymunsemcp?year=2019&entidad=" + sessionStorage.strEntidad+ "&municipio=" +sessionStorage.strMunicipio+ "&semana=40&cp="+sessionStorage.strCp


        doAsync {

            try
            {
                val url= URL(urlws)

                 val urlConnect=url.openConnection() as HttpURLConnection
                 urlConnect.connectTimeout=7000

                var inString= ApiConfig.ConvertStreamToString(urlConnect.inputStream)

                 //println("puto " + inString)

                sessionStorage.casosJson = inString



                uiThread {

                    val gson = Gson()


                    val lstPoints: List<dataModelPoints> = gson.fromJson(inString, object : TypeToken<List<dataModelPoints>>() {}.type)




                    lstPoints.forEach {

                        var latitude = "-"
                        var longitude = "-"

                        try{


                            if (it.latitud!=null)
                                latitude = it.latitud.toString()

                            if (it.longitud!=null)
                                longitude = it.longitud.toString()






                            mapFragment.getMapAsync(object: OnMapReadyCallback {
                                @SuppressLint("MissingPermission")

                                override fun onMapReady(mMap: GoogleMap) {



                                    mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

                                    val googlePlex = CameraPosition.builder()
                                            .target(LatLng(18.8661131,-97.0942103))
                                            .zoom(15F)
                                            .bearing(0F)
                                            .tilt(40F)
                                            .build()


                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null)



                                        mMap.addCircle(CircleOptions()
                                                .center(LatLng(it.latitud!!.toDouble() ,it.longitud!!.toDouble()))
                                                .radius(100.00)
                                                .strokeWidth(3f)
                                                .strokeColor(Color.parseColor("#FA0202"))
                                                .fillColor(Color.parseColor("#FA0202").withAlpha(40))

                                        )


                                }

                            })


                            builder.include(LatLng(latitude.toDouble(),longitude.toDouble()))


                        }
                        catch (ex:Exception){

                            println("$ex error de en mapa")
                        }



                    }

                }





            }
            catch (ex:Exception)
            {

                println("error salud vehicular $ex")

            }
        }
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

                    }


                }else{

                    latitud = locationGps!!.latitude
                    longitud = locationGps!!.longitude

                    if(bandera) {

                        println("puto4" + latitud + " " + longitud)
                        refresMap(latitud, longitud)


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
                    .zoom(15F)
                    .bearing(0F)
                    .tilt(40F)
                    .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null)

        }


        bandera=false
    }



    inner class getCasos : AsyncTask<String, String, String>() {



        private var validacion =false
        override fun onPreExecute() {

            println("preExecutee" )
            // Before doInBackground
        }

        override fun doInBackground(vararg urls: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {

                println("backgroun inicio" )


                val mURL = URL("https://upheld-castle-251021.appspot.com/denguebymunsemcp?year=2019&entidad=30&municipio=118&semana=39&cp=94320")


               // var user = edtUser.text
               // var pass = edtPassword.text
               // val urlParameters = "{\"username\":\"$user\",\"password\":\"$pass\"}"
               // val postData = urlParameters.toByteArray(StandardCharsets.UTF_8) //.getBytes(StandardCharsets.UTF_8)
               // val postDataLength = postData.size

                val urlConnect=mURL.openConnection() as HttpURLConnection

                urlConnect.doOutput = true
                urlConnect.instanceFollowRedirects = false
                urlConnect.requestMethod = "POST"
                urlConnect.setRequestProperty("Content-Type", "application/json")
                urlConnect.setRequestProperty("charset", "utf-8")
               // urlConnect.setRequestProperty("Content-Length", Integer.toString(postDataLength))
                urlConnect.useCaches = false





                    var inString = ConvertStreamToString(urlConnect.inputStream)
                    println("jso $inString ")
                    validacion=true




            } catch (ex: Exception) {



                println("error AsyncTask monitoring $ex ")
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {


                //println("ProgressUpdate"+json)


            } catch (ex: Exception) {

            }
        }

        override fun onPostExecute(result: String?) {



            if(!validacion)
            {

                /*
                val handler = Handler()
                handler.postDelayed({
                    viewDialog.hideDialog()
                    alertNotUSer()

                }, 2000)*/

            }


            println("onPostExecute $result" )


            // Done
        }


    }



}