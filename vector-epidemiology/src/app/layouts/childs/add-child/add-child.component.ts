import { ElementRef, NgZone, OnInit, ViewChild, Component } from '@angular/core';
import { User } from 'src/app/shared/models/user';
import { AuthService } from 'src/app/shared/services/auth.service';
import { UserService } from 'src/app/shared/services/user.service';
import { ChildService } from 'src/app/shared/services/child.service';

import { Branch } from 'src/app/shared/models/branch';
import { NgForm } from '@angular/forms';
import { MapsAPILoader, AgmMap } from '@agm/core';
import { FormControl } from '@angular/forms';
import { RestApiService } from "../../../shared/services/rest-api.service";

import { Entidades } from 'src/app/shared/models/entidades';
import { Municipios } from 'src/app/shared/models/municipios';
import { Denues } from 'src/app/shared/models/denues';

import { Observable, throwError } from 'rxjs';

//import { } from 'googlemaps';
// https://brianflove.com/2016/10/18/angular-2-google-maps-places-autocomplete/
declare var google: any;

declare var toastr: any;
declare var require: any;

class Marker {
  public pos: number;
  public lat: number;
  public lng: number;
  public name: string;
  public image_url: boolean;

  constructor(pos: number, lat: number, lng: number, name: string, image_url) {
      this.pos = pos;
      this.lat = lat;
      this.lng = lng;
      this.name = name;
      this.image_url = image_url;
  }
}

class Entidad {
  public num: number;
  public name: string;

  constructor(num: number, name: string) {
    this.num = num;  
    this.name = name;      
  }
}

class Municipio {
  public num: number;
  public name: string;

  constructor(num: number, name: string) {
    this.num = num;  
    this.name = name;      
  }
}

interface Location {
 // lat: number;
 // lng: number;
 // viewport?: Object;
  placename?: string;
  full_address?: string;
  placeid?: string;
  address_level_1?: string;
  address_number?: string;
  address_level_2?: string;
  address_country?: string;
  address_zip?: string;
  address_state?: string;
 // marker?: Marker;
}

const shortId = require('shortid');

@Component({
  selector: 'app-add-child',
  templateUrl: './add-child.component.html',
  styleUrls: ['./add-child.component.scss']
})

export class AddChildComponent implements OnInit {

  
  geocoder: any;
  service: any;
  mymap : any;

  public searchControl: FormControl;

  public location: Location = {};
  public branchoffice= new Branch();

  @ViewChild("search")
  public searchElementRef: ElementRef;
    
    loggedUser: User;

    // Enable Update Button
    //markersOnMap: Marker[] = [];
    markers: Array<any> =[]; //any[];
    pregnat: Array<any> =[]; //any[];

    censos: Array<any> =[]; //any[];
    
    entidadesOnMap: Array<any> =[]; //any[];
    municipiosOnMap: Array<any> =[]; //any[] = [];
    internet : any = {};
    idh : any = {};
    
    myzoom = 10;
    mapType = 'roadmap';

    lat = 18.85101;
    lng = -97.10084;
    showMap = true;

    mylat = 18.85101;
    mylng = -97.10084;
    nss = '';

    selectedMarker;
    selectedEntidad;
    selectedMunicipio;
    selectedCp;
    year;
    semana;

    /*
    markers = [
      // These are all just random coordinates from https://www.random.org/geographic-coordinates/
      { num: 30, name: "Veracruz" },
      { num: 18, name: "Hidalgo" },
      { num: 20, name: "Puebla" },
      { num: 25, name: "Queretaro" },
     
    ];
*/
    /*entidadesOnMap = [
      // These are all just random coordinates from https://www.random.org/geographic-coordinates/
      { num: 30, name: "Veracruz" },
      { num: 18, name: "Hidalgo" },
      { num: 20, name: "Puebla" },
      { num: 25, name: "Queretaro" }
      
    ];*/


    isMap()
    {
      return this.showMap;
    }
    setMap(value) 
    {
      //alert(value);
      this.showMap = value;
    }

    getInternet(entidad, municipio) {
      
      return this.restApi.getInternets(entidad, municipio)
       .subscribe((data: any) => {
        console.log("internets :" + data );
        this.internet = data;
        //alert("entidades " + data);
      }) 

    } 

    getIdh(entidad, municipio) {
      
      return this.restApi.getIdhs(entidad, municipio)
       .subscribe((data: any) => {
        console.log("idhs :" + data );
        this.idh = data;
        //alert("entidades " + data);
      }) 

    } 


    getEntidades() {
      
      return this.restApi.getEntidades()
       .subscribe((data: any) => {
        console.log("entidades :" + data );
        this.entidadesOnMap = data;
        //alert("entidades " + data);
      }) 

    } 


    getMunicipios(entidad) {
      
      return this.restApi.getMunicipios(entidad)
       .subscribe((data: any) => {
        console.log("municipios :" + data );
        this.municipiosOnMap = data;

      }) 

    }
    
    getDenues(entidad, municipio) {
      
      return this.restApi.getDenues(entidad, municipio)
       .subscribe((data: any) => {
        console.log("denues  :" + data );
        this.markers = data;
        //alert("denues " + data);
        this.lat = parseFloat(this.markers[0].latitud);
        this.lng = parseFloat(this.markers[0].longitud);
        
        

      }) 

    }

    searchPregnant() {
      
      //alert("ssn" + this.semana);
      return this.restApi.getEmbarazada(this.nss)
       .subscribe((data: any) => {
        console.log("embarazada  :" + data );
        this.pregnat = data;

        this.selectedEntidad = parseFloat(this.pregnat[0].cve_edo_res);
        this.selectedMunicipio = parseFloat(this.pregnat[0].cve_mpo_res);
        this.selectedCp = parseFloat(this.pregnat[0].ide_cp);

        //alert("embarazada " + data);
        this.year = 2019;
       // this.semana = 40;

        this.lat = parseFloat(this.pregnat[0].latitud);
        this.lng = parseFloat(this.pregnat[0].longitud);
        
        this.getDengueMunSemCp(this.year, this.selectedEntidad, 
          this.selectedMunicipio, this.semana, this.selectedCp); 


      })
    }

    getDengueMunSemCp(year, entidad, municipio, semana, cp) {
      
      return this.restApi.getDengueMunSemCp(year, entidad, municipio, semana, cp)
       .subscribe((data: any) => {
        console.log("dengues by cp  :" + data );
        this.markers = data;
        //alert("denues " + data);
        this.lat = parseFloat(this.markers[0].latitud);
        this.lng = parseFloat(this.markers[0].longitud);
        
        

      }) 

    }


    getDengueMunSem(year, entidad, municipio, semana) {
      
      return this.restApi.getDengueMunSem(year, entidad, municipio, semana)
       .subscribe((data: any) => {
        console.log("dengues  :" + data );
        this.markers = data;
        //alert("denues " + data);
        this.lat = parseFloat(this.markers[0].latitud);
        this.lng = parseFloat(this.markers[0].longitud);
        
        

      }) 

    }




    getCensos(entidad, municipio) {
      
      return this.restApi.getCensos(entidad, municipio)
       .subscribe((data: any) => {
        console.log("censos  :" + data );
        this.censos = data;
        //alert("denues " + data);
        //this.lat = parseFloat(this.markers[0].latitud);
        //this.lng = parseFloat(this.markers[0].longitud);
        
        

      }) 

    }
/*
    municipiosOnMap = [
      // These are all just random coordinates from https://www.random.org/geographic-coordinates/
      { num: 1, name: "Apan" },
      { num: 2, name: "Pachuca" },
      { num: 3, name: "Tulancingo" },
      { num: 4, name: "Tlalayote" }
     
    ];
*/
    addMarker111(lat: number, lng: number) {
      this.markers = [];
      this.markers.push({ lat, lng, alpha: 0.4 });
      this.mylat = lat;
      this.mylng = lng;
  
     // this.findAddressByCoordinates();
     // this.findPlaceById();
    }
  
    //addMarker($event: any) { 
    mapReady($event: any) { 
    
    // here $event will be of type google.maps.Map 
      // and you can put your logic here to get lat lng for marker. I have just put a sample code. You can refactor it the way you want.
      //alert(" gps" +  $event.coords.lat + ", " + $event.coords.lng)
      //  (mapClick)="addMarker($event.coords.lat, $event.coords.lng)"
      this.mymap = $event;
      this.getLatLong('ChIJN1t_tDeuEmsRUsoyG83frY4', $event, null);
    }
    
    addMarker(lat, lng) { 
     
        // here $event will be of type google.maps.Map 
          // and you can put your logic here to get lat lng for marker. I have just put a sample code. You can refactor it the way you want.
          //alert(" gps" +  $event.coords.lat + ", " + $event.coords.lng)
          //  (mapClick)="addMarker($event.coords.lat, $event.coords.lng)"
         // this.mymap = $event;
        
        this.mylat = lat;
        this.mylng = lng;

        
        this.findAddressByCoordinates();
        }
    max(coordType: 'lat' | 'lng'): number {
      return Math.max(...this.markers.map(marker => marker[coordType]));
    }
  
    min(coordType: 'lat' | 'lng'): number {
      return Math.min(...this.markers.map(marker => marker[coordType]));
    }
  
    selectMarker(event) {
      this.selectedMarker = {
        lat: event.latitude,
        lng: event.longitude
      };
    }

    constructor(private authService: AuthService,
      private restApi: RestApiService,
      private childService: ChildService,
      public mapsApiLoader: MapsAPILoader,
      private ngZone: NgZone


      ) {
/*        this.mapsApiLoader = mapsApiLoader;

        this.searchControl = new FormControl();

        this.mapsApiLoader.load().then(() => {
          this.geocoder = new google.maps.Geocoder();
         // this.service = new google.maps.places.PlacesService();

                //var service = new google.maps.places.PlacesService(map);

        });
*/
      }
  
    ngOnInit() {
      this.loggedUser = this.authService.getLoggedInUser();
      this.branchoffice.name = "oxxo";
       
      this.searchControl = new FormControl();
      this.setCurrentPosition();
    /*
      this.mapsApiLoader = this.mapsApiLoader;

      this.mapsApiLoader.load().then(() => {

        this.geocoder = new google.maps.Geocoder();

        let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
          types: ["address"]
        });

        this.service = new google.maps.places.PlacesService();

        autocomplete.addListener("place_changed", () => {
          this.ngZone.run(() => {
            //get the place result
            let place = autocomplete.getPlace();
  
            //verify result
            if (place.geometry === undefined || place.geometry === null) {
              return;
            }
  

            //set latitude, longitude and zoom
            this.lat = place.geometry.location.lat();
            this.lng = place.geometry.location.lng();
            this.myzoom = 16;
          });
        });
      });
*/
      this.getEntidades();
    }
    
    selectEntidad() {
      //alert("entidad " + this.selectedEntidad);
      this.getMunicipios(this.selectedEntidad);
    }

    selectMunicipio() {
      //alert("semana " + this.semana);
      var year = 2019;
     // var semana = 41;
      //console.log("estado" + this.selectEntidad);
      //console.log("municipio" + this.selectMunicipio);
     // alert(this.selectedMunicipio);
      this.getDengueMunSem(year, this.selectedEntidad, this.selectedMunicipio, 
        this.semana);
   //   this.getInternet(this.selectedEntidad, this.selectedMunicipio);
    //  this.getIdh(this.selectedEntidad, this.selectedMunicipio);
    //  this.getCensos(this.selectedEntidad, this.selectedMunicipio);
      
    }

    addBranch(branchForm: NgForm) {
      
      
      branchForm.value['childCode'] = 'suc_' + shortId.generate();
      // branchForm.value['userid'] = this.loggedUser.$key;
      branchForm.value['warehouseid'] = 'xxx';
      branchForm.value['description'] = '-';

      branchForm.value['lat'] = this.mylat;
      branchForm.value['lng'] = this.mylng;
      /*productForm.value['ratings'] = Math.floor(Math.random() * 5 + 1);
      if (productForm.value['productImageUrl'] === undefined) {
        productForm.value['productImageUrl'] = 'http://via.placeholder.com/640x360/007bff/ffffff';
      }
  
      productForm.value['favourite'] = false;
  
      const date = productForm.value['productAdded'];
  */
      console.log(" form " + branchForm.value['name']);

      this.childService.createBranchOffice(branchForm.value);
  
      //this.product = new Product();
  
      //$('#exampleModalLong').modal('hide');
      // https://alligator.io/angular/angular-google-maps/
  
      toastr.success('branch office ' + branchForm.value['name'] + 'is added successfully', 'Branch office Creation');
    }
  
    findPlaceById() {
     
      //var service = new google.maps.places.PlacesService(map);
      var request = {
        placeId: this.location.placeid,
        fields: ['name', 'formatted_address', 'place_id', 'geometry']
      };

      /*
      this.service.getDetails(request, function(place, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
         
          alert( " place:" + place.name + " - " + 
           place.formatted_address);
         
         
        }
      });
*/
    }
  

    findAddressByCoordinates() {
      this.geocoder.geocode({
        'location': {
          lat: this.mylat,
          lng: this.mylng
        }
      }, (results, status) => {
       // alert("results:" + results);
        this.decomposeAddressComponents(results);
      
      /*  alert("dir : " + this.location.placeid + " : " +
        this.location.address_level_1 + " " +
        this.location.address_number 
        + ' - ' + this.location.address_level_2
        + ' - ' + this.location.address_state
        + ' - ' + this.location.address_country
        + ' - ' + this.location.address_zip
        ); 
*/
       // alert("gps " + this.mylat + "-" + this.mylng + "-" + this.location.placeid);
       
        this.getLatLong(this.location.placeid, this.mymap, null);
       
      });
    }
  
    decomposeAddressComponents(addressArray) {
      if (addressArray.length == 0) { return false; }

      let address = addressArray[0].address_components;
      this.location.address_level_1 = '';
      this.location.placeid = addressArray[0].place_id;

      for (let element of address) {
        if (element.length == 0 && !element['types']) { continue; }
  
        if (element['types'].indexOf('route') > -1) {
          this.location.address_level_1 = '' + element['long_name'];
          continue;
        }
        if (element['types'].indexOf('street_number') > -1) {
          this.location.address_number = ' ' + element['long_name'];
          continue;
        }
        
        if (element['types'].indexOf('locality') > -1) {
          this.location.address_level_2 = element['long_name'];
          continue;
        }
        if (element['types'].indexOf('administrative_area_level_1') > -1) {
          this.location.address_state = element['long_name'];
          continue;
        }
        if (element['types'].indexOf('country') > -1) {
          this.location.address_country = element['long_name'];
          continue;
        }
        if (element['types'].indexOf('postal_code') > -1) {
          this.location.address_zip = element['long_name'];
          continue;
        }
      }
      this.branchoffice.address = this.location.address_level_1 + " " +
      this.location.address_number 
      + ' - ' + this.location.address_level_2
      + ' - ' + this.location.address_state
      + ' - ' + this.location.address_country
      + ' - ' + this.location.address_zip;
      
    }
  
    getLatLong(placeid: string, map: any, fn) {
      let placeService = new google.maps.places.PlacesService(map);
      placeService.getDetails({
        placeId: placeid
//        }, function (result, status) {
        }, (results, status) => {

       // this.location.name = result.name;
       //  alert(" ok name : " + 
       //  result.name );
         this.decomposePlace(results);
         // console.log(result.geometry.location.lat());
         // console.log(result.geometry.location.lng());
        });
    } 

    
    decomposePlace(placeArray) {
      this.branchoffice.name = placeArray.name;
    }

    private setCurrentPosition() {
      if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition((position) => {
          this.lat = position.coords.latitude;
          this.lng = position.coords.longitude;
          this.myzoom = 15;
        });
      }
    }
  }
  