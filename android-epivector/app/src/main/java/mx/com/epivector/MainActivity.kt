package mx.com.epivector

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.user.vigilante.ciudadano.fragments.Bitacorafragment
import com.example.user.vigilante.ciudadano.fragments.AltaPacienteFragment
import com.example.user.vigilante.ciudadano.fragments.Mapsfragment
import kotlinx.android.synthetic.main.activity_main.*

import mx.com.epivector.models.dataModel

class MainActivity : AppCompatActivity() {

    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    var latitud= 0.00
    var longitud = 0.00
    private val LOCATION_REQUEST_CODE = 1


    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {

        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 1)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    val fragment = Mapsfragment()
                    openFragment(fragment)
                    true
                }
                R.id.navigation_dashboard -> {
                    val fragment = Bitacorafragment.newInstance()
                    openFragment(fragment)

                    true
                }
                R.id.navigation_notifications -> {
                    val fragment = AltaPacienteFragment()
                    openFragment(fragment)

                    true
                }
                else -> false
            }
        }
        navigation.selectedItemId = R.id.navigation_home



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            try{

                getLocation()}
            catch (ex:Exception){

                println("error permisos para acceder ubicacion $ex")
            }

        } else {

           // Toast.makeText(applicationContext,"No tiene permisos",Toast.LENGTH_LONG).show()

        }




    }





    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        println("requestCode" + requestCode)

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    // permission was granted
                    //Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()

                    //reloadActivity()
                } else {
                    //Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            } else {
                //Toast.makeText(this, "grantResults is empty", Toast.LENGTH_LONG).show()
            }
        }
    }



    @SuppressLint("MissingPermission")
    private fun getLocation() {



        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {


            permissions

            if (hasGps) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationGps = location

                            latitud = locationGps!!.latitude
                            longitud = locationGps!!.longitude

                            dataModel.lat = latitud
                            dataModel.long = longitud

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
                            dataModel.lat = latitud
                            dataModel.long = longitud

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

                    latitud = locationNetwork!!.latitude
                    longitud = locationNetwork!!.longitude

                    dataModel.lat = latitud
                    dataModel.long = longitud



                }else{


                    latitud = locationGps!!.latitude
                    longitud = locationGps!!.longitude

                    dataModel.lat = latitud
                    dataModel.long = longitud


                }
            }

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }


    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun reloadActivity(){
        finish()
        startActivity(intent)
    }


}
