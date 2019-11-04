package com.example.linksaver.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "link_table")
data class LinkModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var address: String? = null,
    var description: String? = "",
    var imageUri: String? = null,
    var priority: Int,
    val type: List<String> = mutableListOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        mutableListOf<String>().apply {
            parcel.readList(this as List<*>, String::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id!!)
        parcel.writeString(address)
        parcel.writeString(description)
        parcel.writeString(imageUri)
        parcel.writeInt(priority)
        parcel.writeList(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LinkModel> {
        override fun createFromParcel(parcel: Parcel): LinkModel {
            return LinkModel(parcel)
        }

        override fun newArray(size: Int): Array<LinkModel?> {
            return arrayOfNulls(size)
        }
    }
}