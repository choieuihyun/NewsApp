package com.example.domain.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class SavedModel (

    var id: Int? = null, // 요렇게하고 null로 주면 autoGenerate
    var author: String?,
    var content: String?,
    var publishedAt: String?,
    var title: String?,
    var urlToImage: String?,
    var savedButton: Boolean?

)

data class SavedModelParcelize ( // topNews -> topNewsDetail 데이터 전달하려고 만듦
    // 직렬화 시킨 데이터는 내부에 접근할 수가 없어서..
    val id: Int? = null,

    val author: String?,

    val content: String?,

    val publishedAt: String?,

    val title: String?,

    val urlToImage: String?,

    val savedButton: Boolean?

    ) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.Q) // 아이고
    constructor(parcel: Parcel) : this(
        author = parcel.readString(),
        content = parcel.readString(),
        publishedAt = parcel.readString(),
        title = parcel.readString(),
        urlToImage = parcel.readString(),
        savedButton = parcel.readBoolean()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(publishedAt)
        parcel.writeString(urlToImage)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SavedModelParcelize> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): SavedModelParcelize {
            return SavedModelParcelize(parcel)
        }

        override fun newArray(size: Int): Array<SavedModelParcelize?> {
            return arrayOfNulls(size)
        }
    }

}