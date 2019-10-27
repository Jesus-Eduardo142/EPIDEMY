package mx.com.epivector


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.support.v9.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.facebook.*

import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mx.com.epivector.R.styleable.SignInButton
import mx.com.epivector.models.ApiConfig
import mx.com.epivector.models.dataModelPoints
import mx.com.epivector.models.sessionStorage
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.withAlpha
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * A login screen that offers login via email/password.
 */


class LoginActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private var callbackManager: CallbackManager? = null

    var btnLogin:Button? = null
    var edtNss:EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        try {


        var accessToken = AccessToken.getCurrentAccessToken() //facebook

            btnLogin = findViewById(R.id.email_sign_in_button) as Button
            edtNss = findViewById(R.id.edtNss) as EditText




/*
            val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)  //google
                    .requestEmail()
                    .build()
           var googleSignInClient = GoogleSignIn.getClient(this, gso)

            signInButton.setOnClickListener {
                fun onClick(v: View) {
                    val signInIntent = googleSignInClient.getSignInIntent()
                    startActivityForResult(signInIntent, 101)
                }
            }*/
/*



            }
*/



        var isLoggedIn = accessToken != null && !accessToken.isExpired



/*
        if(!isLoggedIn){





            var btnLoginFacebook = findViewById<LoginButton>(R.id.btnLoginFacebook)

            FacebookSdk.sdkInitialize(getApplicationContext())
            AppEventsLogger.activateApp(this)

            btnLoginFacebook.setOnClickListener {
                // Login
                callbackManager = CallbackManager.Factory.create()
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))


                LoginManager.getInstance().registerCallback(callbackManager,
                        object : FacebookCallback<LoginResult> {
                            override fun onSuccess(loginResult: LoginResult) {


                                val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                                    try {


                                        //here is the data that you want
                                        println("FBLOGIN_JSON_RES" + `object`.toString())


                                        if (`object`.has("id")) {
                                            //  handleSignInResultFacebook(`object`)
                                        } else {
                                            println("FBLOGIN_FAILD" + `object`.toString())

                                        }

                                    } catch (e: Exception) {
                                        e.printStackTrace()

                                    }
                                }


                                val parameters = Bundle()
                                parameters.putString("fields", "name,email,id")
                                request.parameters = parameters
                                request.executeAsync()

                                Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                                //startActivity(Intent(applicationContext, MainActivityProfile::class.java))

                                val intent = Intent(this@LoginActivity, MainActivityProfile::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                finish()

                            }

                            override fun onCancel() {
                                Log.d("MainActivity", "Facebook onCancel.")

                            }

                            override fun onError(error: FacebookException) {
                                Log.d("MainActivity", "Facebook onError.")

                            }
                        })
            }



        }*/






                /*
                    val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
                    if (googleSignInAccount != null) {
                        var count = GoogleSignIn.getLastSignedInAccount(this) != null
                    }

                */
        //else {


           // startActivity(Intent(applicationContext, MainActivityProfile::class.java))

       // }

        }catch (ex:Exception){


            Log.d("LoginActivity Oncreate",""+ex)

        }


        btnLogin!!.setOnClickListener {
            var strNss = edtNss!!.getText().toString()

            if(!strNss.isNullOrEmpty()) {
                callWS(strNss)
            }else{
                Toast.makeText(this,"Ingresa numero de seguro social",Toast.LENGTH_LONG).show()
            }


            //var intent= Intent(baseContext, MainActivityProfile::class.java)
            //startActivity(intent)

        }



    }


    fun callWS(nss:String) {


        val urlws = "https://upheld-castle-251021.appspot.com/embarazadabyssn?ssn="+nss


        doAsync {

            try
            {
                val url= URL(urlws)

                val urlConnect=url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout=7000

                var inString= ApiConfig.ConvertStreamToString(urlConnect.inputStream)

                println("puto loco " + inString)


                    uiThread {

                        val gson = Gson()

                        val user: List<dataModelPoints> = gson.fromJson(inString, object : TypeToken<List<dataModelPoints>>() {}.type)

                        try {

                            sessionStorage.strMunicipio = user.first().cve_mpo_res!!
                            sessionStorage.strEntidad = user.first().cve_edo_res!!
                            sessionStorage.strCp = user.first().ide_cp!!
                            sessionStorage.nameUser = user.first().ide_nom!!+" "+user.first().ide_ape_mat!!+" "+user.first().ide_ape_pat!!

                            var intent= Intent(baseContext, MainActivityProfile::class.java)
                            startActivity(intent)


                        }

                        catch (ex:Exception){
                            messageAlert()
                        }

                    }





            }
            catch (ex:Exception)
            {

                println("error salud vehicular $ex")

            }
        }
    }

    private fun messageAlert() {
        Toast.makeText(this,"NSS incorrecto, intentalo de nuevo!",Toast.LENGTH_LONG).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==101){
            try
            {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                onLoggedIn(account!!)
            }
            catch (e:ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                println("signInResult:failed code=" + e.getStatusCode())

            }
        }
        else{
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun onLoggedIn(googleSignInAccount:GoogleSignInAccount) {
        val intent = Intent(this, MainActivityProfile::class.java)
       // intent.putExtra(ProfileActivity.GOOGLE_ACCOUNT, googleSignInAccount)
        startActivity(intent)
        finish()
    }




}
