package mx.com.epivector.models

class dataModel {

    companion object {

        var lat: Double= 0.00
        var long : Double= 0.00


        fun getLatitud():Double{
            return lat
        }


        fun getLongitud():Double{
            return long
        }



    }

    constructor( latValue:Double,longValue:Double){
        lat = latValue
        long = longValue



    }
}