# Park facility API

## Table of contents
* [Preamble](#preamble)
* [API Services  ](#api-services)
* [Error and warning handling](#error-and-warning-handling)
* [Status Codes](#status-codes)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Contact](#contact)

## Preamble  
>The Park facility REST API is a realtime solution that allows clients to search and reuse "Rennes/France" car parks locations, prices,
 availabilities ...  
>This API is generally RESTFUL and returns results in JSON.  
The API data are retrieved from this API `https://data.rennesmetropole.fr/explore/dataset/export-api-parking-citedia/api/`

## API Services  
This API provides two types of services:  
* #### Get the list of available car parks sorted by the number of available places (DESC order):  
>What is an available car park ?  

It's a car park who has `status = OUVERT` and `free > 0`. The `free` key represents the number of available places.

>How to consume this service ?  

```http
GET /api/v1/car_parks/
```

>Response example:  

```javascript
{
    "nhits": 3,
    "records": [
        {
            "recordid": "33404c11fecae7fc0899889308aaff3e7808916f",
            "fields": {
                "id": "310",
                "key": "Colombier",
                "status": "OUVERT",
                "max": 1115,
                "free": 1004,
                "tarif_15": 0.4,
                "tarif_30": 0.8,
                "tarif_1h": 1.6,
                "tarif_1h30": 2.2,
                "tarif_2h": 2.8,
                "tarif_3h": 4.0,
                "tarif_4h": 5.2,
                "geo": [
                    48.10476252,
                    -1.678624547
                ],
                "orgahoraires": "24h/24 et 7j/7. Bureau ouvert, 7h30 à 20h30 sauf dimanche et jours fériés."
            },
            "record_timestamp": "2019-11-04T18:35:08.000000Z"
        },
        {
            "recordid": "1742fd07e1b39f74334d7176f30e525486bc8d48",
            "fields": {
                "id": "477",
                "key": "Gare-Sud",
                "status": "OUVERT",
                "max": 964,
                "free": 556,
                "tarif_15": 0.5,
                "tarif_30": 1.0,
                "tarif_1h": 2.0,
                "tarif_1h30": 3.0,
                "tarif_2h": 4.0,
                "tarif_3h": 5.6,
                "tarif_4h": 7.2,
                "geo": [
                    48.1025360816,
                    -1.672518275
                ],
                "orgahoraires": "24h/24 et 7j/7 Bureau ouvert : lundi et mardi, 5h30 à 22h. Mercredi, 5h30 à 23h. Jeudi et vendredi, 5h30 à minuit. Samedi, 7h à 22h. Dimanche et jours fériés, 8h à 1h."
            },
            "record_timestamp": "2019-11-04T18:35:08.000000Z"
        },
        {
            "recordid": "e78588989772124ffb9211308f2515fedb2e1c8f",
            "fields": {
                "id": "1078",
                "key": "Arsenal",
                "status": "OUVERT",
                "max": 605,
                "free": 521,
                "tarif_15": 0.2,
                "tarif_30": 0.4,
                "tarif_1h": 0.8,
                "tarif_1h30": 1.2,
                "tarif_2h": 1.6,
                "tarif_3h": 2.4,
                "tarif_4h": 3.2,
                "geo": [
                    48.1043058108,
                    -1.6847819587
                ],
                "orgahoraires": "Parking ouvert 7j/7. Lundi au jeudi, 6h30 à 22h et du vendredi au dimanche, 6h30 à 6h.~~~Bureau du Colombier ouvert : 7h30 à 20h30 sauf dimanche et jours fériés.~~~"
            },
            "record_timestamp": "2019-11-04T18:35:08.000000Z"
        }
            ]
}
```  
This JSON response contains 3 available car parks sorted by the number of available places (`free` key)  

* #### Get the list of available car parks by zone, sorted by distance (ASC order):  
>What is a zone ?  

We define a zone by 3 query parameters:  
`latitude` : user location latitude.  
`longitude` : user location longitude.  
`distance` : maximum distance area, specified in meters.  

A zone can be represented as a circle, where the point (latitude, longitude) is the center and the distance is the radius of this circle.

>How to consume this service ?  

```http
GET /api/v1/car_parks/zone?latitude=[user latitude]&longitude=[user longitude]&distance=[maximum distance]
```

>Response example:  
```javascript
{
    "nhits": 3,
    "records": [
        {
            "recordid": "300d96abe0b8f09cd78678fe2d9dbb70d24cb278",
            "fields": {
                "id": "477",
                "key": "Gare-Sud",
                "status": "OUVERT",
                "max": 964,
                "free": 555,
                "tarif_15": 0.5,
                "tarif_30": 1.0,
                "tarif_1h": 2.0,
                "tarif_1h30": 3.0,
                "tarif_2h": 4.0,
                "tarif_3h": 5.6,
                "tarif_4h": 7.2,
                "geo": [
                    48.1025360816,
                    -1.672518275
                ],
                "orgahoraires": "24h/24 et 7j/7 Bureau ouvert : lundi et mardi, 5h30 à 22h. Mercredi, 5h30 à 23h. Jeudi et vendredi, 5h30 à minuit. Samedi, 7h à 22h. Dimanche et jours fériés, 8h à 1h."
            },
            "record_timestamp": "2019-11-04T19:07:26.000000Z",
            "distance": 183.0
        },
        {
            "recordid": "220de20be147ee66e5f13c27982de3486dcb3c5d",
            "fields": {
                "id": "1815",
                "key": "Charles-de-gaulle",
                "status": "OUVERT",
                "max": 756,
                "free": 456,
                "tarif_15": 0.4,
                "tarif_30": 0.8,
                "tarif_1h": 1.6,
                "tarif_1h30": 2.2,
                "tarif_2h": 2.8,
                "tarif_3h": 4.0,
                "tarif_4h": 5.2,
                "geo": [
                    48.10623522,
                    -1.676050081
                ],
                "orgahoraires": "7j/7. Lundi au dimanche, 5h à 2h.~~~Bureau ouvert : lundi, 8h à 1h, mardi au samedi, 9h à 1h, dimanche et les jours fériés, 14h à 1h."
            },
            "record_timestamp": "2019-11-04T19:07:26.000000Z",
            "distance": 374.0
        },
        {
            "recordid": "7a6a374439b47cac95fbde9b6bb8ade5e0829f6f",
            "fields": {
                "id": "310",
                "key": "Colombier",
                "status": "OUVERT",
                "max": 1115,
                "free": 1018,
                "tarif_15": 0.4,
                "tarif_30": 0.8,
                "tarif_1h": 1.6,
                "tarif_1h30": 2.2,
                "tarif_2h": 2.8,
                "tarif_3h": 4.0,
                "tarif_4h": 5.2,
                "geo": [
                    48.10476252,
                    -1.678624547
                ],
                "orgahoraires": "24h/24 et 7j/7. Bureau ouvert, 7h30 à 20h30 sauf dimanche et jours fériés."
            },
            "record_timestamp": "2019-11-04T19:07:26.000000Z",
            "distance": 491.0
        }
    ]
}
```  
This JSON response contains 3 available car parks sorted by distance (`distance` key).  
`distance` represents the distance between the user and the car park.  

* Keys description:  


| Key | Description |
| :--- | :--- |
| nhits | `Number of available car parks` |
| records | `List of car parks` |
| key | `Car park name` |
| max | `Capacity of the car park` |
| free | `Number of available spaces in the car park` |
| tarif_* | `Price by time` |
| geo | `Car park location` |
| record_timestamp | `The API last update date` |
| distance | `The distance between the user and the car park` |



## Error and warning handling
In case of an error or a warning, the API will send two tables describing the error/warning with a code and message.  


#### Error example (Bad URL parameter):  
```javascript
{
    "errors": [
        {
            "code": "BAD_PARAMETER",
            "message": "latitude, longitude and distance parameters must be numbers"
        }
    ]
}
```  

#### Warning example (Response empty data):  
```javascript
{
    "warnings": [
        {
            "code": "EMPTY_DATA",
            "message": "There are no parks near you, please update your zone distance"
        }
    ],
    "nhits": 0,
    "records": []
}
```  

## Status Codes
Park facility returns the following status codes:

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 400 | `BAD REQUEST` |
| 404 | `NOT FOUND` |
| 500 | `INTERNAL SERVER ERROR` |

## Technologies
The application is built with the following dependencies:
* Spring boot 2.2.0 for the quick start and the embedded tomcat,
and `spring-boot-starter-test` for the integration tests.
* Spring MVC 5.2.0 to follow the MVC Design Pattern and build web controllers
 using the annotations`@RestController` `@RequestMapping` ...
* Junit 4.1.2 and hamcrest 2.1 for the Unit tests.
* Lombok 1.18.10 to auto generate Getters, Setters, equals ... methods.  
* Maven 3.6.2 to manage build, tests and dependencies
* Git for versioning    
* Java 11

## Setup
1 - Clone the repository in your workspace `git clone https://github.com/imad-berkati/park-facility.git`  
2 - Run this maven command in your project root directory `mvn clean install`  
3 - Start the application with the command `java -jar target/park-facility-0.0.1-SNAPSHOT.jar`  
Note: 
Make sure that your `8080` port is not already in use.  
4 - You can start to consume the API services.  
Requests examples:   
`http://localhost:8080/api/v1/car_parks/`   
`http://localhost:8080/api/v1/car_parks/zone?latitude=48.104160&longitude=-1.672072&distance=900`  


## Features   
* Get the list of available car parks, sorted by the number of available places in descending order.
* Get the list of available car parks by zone, sorted by distance in ascending order.

TODO list by priority:  
* Update the keys in the JSON response.
* Create custom exceptions.
* Add Logs.  
* Secure the API by using HTTPS.

## Contact
Contact: berkati.imad@gmail.com