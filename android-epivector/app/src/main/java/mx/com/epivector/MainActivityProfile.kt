package mx.com.epivector

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.user.vigilante.ciudadano.fragments.AltaPacienteFragment
import com.example.user.vigilante.ciudadano.fragments.Bitacorafragment
import com.example.user.vigilante.ciudadano.fragments.Mapsfragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.widget.ProfilePictureView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_profile.*
import kotlinx.android.synthetic.main.app_bar_main_activity_profile.*
import mx.com.epivector.models.dataModel
import mx.com.epivector.models.sessionStorage

class MainActivityProfile : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var viewDialog:ViewDialog
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
        setContentView(R.layout.activity_main_profile)
        setSupportActionBar(toolbar)


        viewDialog = ViewDialog(this)

        var profileImage = findViewById<ProfilePictureView>(R.id.profilePicture)




        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val hView = navigationView.getHeaderView(0)
        val txtName = hView.findViewById(R.id.txtNameProfile) as TextView
        val imgProfile = hView.findViewById<ProfilePictureView>(R.id.profilePicture)
        //nav_user.setText(user)




        try {

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                getLocation()
            }else{

            }


            txtName.text = sessionStorage.nameUser

            val params = Bundle()
            params.putString("fields", "id,name,email,picture.type(large)")
            GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                    object: GraphRequest.Callback {
                        override fun onCompleted(response:GraphResponse) {
                            if (response != null)
                            {
                                try
                                {
                                    val data = response.getJSONObject()

                                    println("ahorita vemos" + data.toString())


                                    if (data.has("name"))
                                    {
                                        val profileName = data.getString("name")

                                        println("name " + profileName)

                                        txtName.text = profileName

                                    }


                                    if (data.has("id"))
                                    {
                                        val profilePicUrl = data.getString("id")

                                        println("id " + profilePicUrl)
                                        imgProfile.isDrawingCacheEnabled = true
                                        imgProfile.profileId = profilePicUrl

                                    }
/*
                                    if (data.has("picture"))
                                    {
                                        val profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url")

                                        println("ahorita vemos" + profilePicUrl)



                                        val profilePic = BitmapFactory.decodeStream(profilePicUrl.openConnection().getInputStream())
                                        // set profilePic bitmap to imageview
                                    }
                                    */

                                }
                                catch (e:Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }).executeAsync()





           /* val profile = Profile.getCurrentProfile()
            if (profile != null)
            {
                profileImage.profileId = profile.id

                txtName.text = profile.firstName + " " + profile.lastName


                println("get image name" + profile.getFirstName() + " " + profile.getLastName() )

                //   Log.v(TAG, "Logged, user name=" + profile.getFirstName() + " " + profile.getLastName())
            }
*/
        }catch (ex:Exception){

            println("error get image name" + ex)
        }






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


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)




    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

                viewDialog.showDialog()


                //disconnectFromFacebook()
                closeSession()

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun closeSession() {


        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            viewDialog.hideDialog()


        }, 3000)



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


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        println("requestCode" + requestCode)

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    // permission was granted
                    //Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()

                    //reloadActivity()
                } else {
                   // Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "grantResults is empty", Toast.LENGTH_LONG).show()
            }
        }
    }



    fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null)
        {
            return // already logged out
        }
        GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, object: GraphRequest.Callback {
            override fun onCompleted(graphResponse: GraphResponse) {
                LoginManager.getInstance().logOut()

                startActivity(Intent(applicationContext, LoginActivity::class.java))
                viewDialog.hideDialog()
            }
        }).executeAsync()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
