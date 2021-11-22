package com.anom.contactsapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(var name: String, var phoneNumber: String): Parcelable