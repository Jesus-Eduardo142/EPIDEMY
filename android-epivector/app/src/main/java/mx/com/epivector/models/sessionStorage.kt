package mx.com.epivector.models

class sessionStorage {

    companion object {

        var casosJson: String = ""
        var strMunicipio: String = ""
        var strEntidad: String = ""
        var strCp: String = ""
        var nameUser: String = ""




        fun getJsonCasos() : String{
            return casosJson
        }


        fun getstrMunicipio() : String{
            return strMunicipio
        }

        fun getstrEntidad() : String{
            return strEntidad
        }

        fun getstrCp() : String{
            return strCp
        }


        fun getnameUser() : String{
            return nameUser
        }




    }

    constructor(){
        casosJson = casosJson
        strMunicipio = strMunicipio
        strEntidad = strEntidad
        strCp = strCp
        nameUser = nameUser

    }
}
