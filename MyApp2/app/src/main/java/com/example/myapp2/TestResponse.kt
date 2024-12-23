package com.example.myapp2

data class TestResponse(val result: String)

data class Brand(
    val id: Int,
    val brand_name: String,
    val country: String
)
data class Model(
    val id: Int,
    val brand_id: Int,
    val model_name: String
)

data class Car(
    val id: Int,
    val vin_num: String,
    val license_plate: String,
    val model_id: Int,
    val colour: String
)

data class Client(
    val id: Int,
    val name: String,
    val surname: String,
    val telephone: String
)

data class Violation(
    val id: Int,
    val description: String,
    val date: String,
    val fine: Int,
    val client_id: Int
)

data class Tariff(
    val id: Int,
    val description: String,
    val cost: Int
)

data class Rent(
    val id: Int,
    val start_date: String,
    val finish_date: String,
    val tariff: Int,
    val car_id: Int,
    val client_id: Int
)

data class Status(
    val id: Int,
    val status: String
)

data class Method(
    val id: Int,
    val method: String
)

data class Payment(
    val id: Int,
    val date: String,
    val rent_id: Int,
    val method_id: Int,
    val status_id: Int
)

data class RentViolation(
    val rent_id: Int,
    val violation_id: Int
)