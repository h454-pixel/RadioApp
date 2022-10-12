package com.example.radioapp.Model

import android.os.Parcel
import android.os.Parcelable


data class ListRecommed(
    val success: Int,
    val message: String,
    val `data`: List<Recommed>,
){

    data class Recommed(
        val st_id: String?,
        val name: String?,
        val image: String?,
        val genre: String?,
        val region: String?,
        val st_link: String?,
        val countryName: String?
    ): Parcelable {
        constructor(parcel: Parcel) : this(
          parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()

        ) {

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
              parcel.writeString(st_id)
              parcel.writeString(name)
              parcel.writeString(image)
              parcel.writeString(genre)
              parcel.writeString(region)
              parcel.writeString(st_link)
              parcel.writeString(countryName)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ListRecommed.Recommed> {
            override fun createFromParcel(parcel: Parcel): ListRecommed.Recommed {
                return Recommed(parcel)
            }

            override fun newArray(size: Int): Array<ListRecommed.Recommed?> {
                return arrayOfNulls(size)
            }
        }
    }
















 }




