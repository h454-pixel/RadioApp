package com.example.radioapp.Model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ListRadio(
    val curentpage: Int,
    @Expose
    @SerializedName("data")
    val data: List<RadioChannel>,
    val limit: Int,
    val message: String,
    val state_data: List<StateData>,
    val success: Int,
    val totaldata: Int,
    val totalpages: Int
){
    data class StateData(
        @Expose
        @SerializedName("name")
        val name: String
    )

    data class RadioChannel(
        @Expose
        @SerializedName("country_name")
        val country_name: String?,
        @Expose
        @SerializedName("genre")
        val genre: String?,
        @Expose
        @SerializedName("image")
        val image: String?,
        @Expose
        @SerializedName("name")
        val name: String?,
        @Expose
        @SerializedName("region")
        val region: String?,
        @Expose
        @SerializedName("st_id")
        val st_id: String?,
        @Expose
        @SerializedName("st_link")
        val st_link: String?
        ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {


        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(st_link)
          //  parcel.writeString(video_file)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<RadioChannel> {
            override fun createFromParcel(parcel: Parcel): RadioChannel {
                return RadioChannel(parcel)
            }

            override fun newArray(size: Int): Array<RadioChannel?> {
                return arrayOfNulls(size)
            }
        }
    }
}

