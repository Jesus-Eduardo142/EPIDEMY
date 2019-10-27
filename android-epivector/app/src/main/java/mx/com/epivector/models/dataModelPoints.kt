package mx.com.epivector.models

import java.util.function.DoubleToLongFunction

class dataModelPoints{


    var id: String?=null
    var ide_nom:String?=null
    var ide_ape_pat:String?=null
    var ide_ape_mat:String?=null
    var cve_edo_res:String?=null
    var cve_mpo_res:String?=null
    var des_cal:String?=null
    var ide_cal:String?=null
    var num_ext:String?=null
    var ide_col:String?=null
    var ide_cp:String?=null
    var des_mpo_res:String?=null
    var des_edo_res:String?=null
    var sem:String?=null
    var latitud : String?=null
    var longitud : String?=null


    constructor(


            id: String,
            ide_nom:String,
            ide_ape_pat:String,
            ide_ape_mat:String,
            cve_edo_res:String,
            cve_mpo_res:String,
            des_cal:String,
            ide_cal:String,
            num_ext:String,
            ide_col:String,
            ide_cp:String,
            des_mpo_res:String,
            des_edo_res:String,
            sem:String,
            latitud:String,
            longitud:String



    ){


        this.id = id
        this.ide_nom=ide_nom
        this.ide_ape_pat=ide_ape_pat
        this.ide_ape_mat=ide_ape_mat
        this.cve_edo_res = cve_edo_res
        this.cve_mpo_res = cve_mpo_res
        this.des_cal = des_cal
        this.ide_cal = ide_cal
        this.num_ext = num_ext
        this.ide_col = ide_col
        this.ide_cp = ide_cp
        this.des_mpo_res = des_mpo_res
        this.des_edo_res = des_edo_res
        this.sem = sem
        this.cve_mpo_res = cve_mpo_res
        this.latitud=latitud
        this.longitud =longitud






    }

}