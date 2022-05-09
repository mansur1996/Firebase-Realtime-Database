package com.example.firbaseauthwithgmail.model

class User {
    var uid: String? = null
    var photoUrl: String? = null
    var displayName: String? = null
    var email: String? = null
    var phoneNumber: String? = null

    constructor(
        uid: String?,
        photoUrl: String?,
        displayName: String?,
        email: String?,
        phoneNumber: String?
    ) {
        this.uid = uid
        this.photoUrl = photoUrl
        this.displayName = displayName
        this.email = email
        this.phoneNumber = phoneNumber
    }

    constructor()
}
