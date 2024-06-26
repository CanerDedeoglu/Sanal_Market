package com.canerdedeoglu.afinal.models

import java.io.Serializable

data class User(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val maidenName: String,
    val age: String,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val birthDate: String,
    val image: String,
    val bloodGroup: String,
    val height: Double,
    val weight: Double,
    val eyeColor: String,
    val hair: Hair,
    val ip: String,
    val address: Address,
    val macAddress: String,
    val university: String,
    val bank: Bank,
    val company: Company,
    val ein: String,
    val ssn: String,
    val userAgent: String,
    val crypto: Crypto,
    val role: String
): Serializable

data class Address (
    val address: String,
    val city: String,
    val state: String,
    val stateCode: String,
    val postalCode: String,
    val coordinates: Coordinates,
    val country: String
): Serializable

data class Coordinates (
    val lat: Double,
    val lng: Double
): Serializable

data class Bank (
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
): Serializable

data class Company (
    val department: String,
    val name: String,
    val title: String,
    val address: Address
): Serializable

data class Crypto (
    val coin: String,
    val wallet: String,
    val network: String
): Serializable

data class Hair (
    val color: String,
    val type: String
): Serializable
