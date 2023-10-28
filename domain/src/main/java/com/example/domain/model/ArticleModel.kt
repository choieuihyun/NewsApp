package com.example.domain.model

import android.os.Parcel
import android.os.Parcelable

data class TopNewsResponseModel(

    val articles: List<Any?>?, // 매퍼에서 에러떠서 List<ArticleModel?>?로 안했음.

    val status: String?,

    val totalResults: Int?
)

data class ArticleModel (

    var author: String?,

    val content: String?,

    val description: String?,

    val publishedAt: String?,

    val source: SourceModel?,

    val title: String?,

    val url: String?,

    val urlToImage: String?,

    )

data class ArticleModelParcelize ( // topNews -> topNewsDetail 데이터 전달하려고 만듦
                                    // 직렬화 시킨 데이터는 내부에 접근할 수가 없어서..
    val author: String?,

    val content: String?,

    val publishedAt: String?,

    val title: String?,

    val urlToImage: String?,

    ) : Parcelable {

    constructor(parcel: Parcel) : this(
        author = parcel.readString(),
        content = parcel.readString(),
        publishedAt = parcel.readString(),
        title = parcel.readString(),
        urlToImage = parcel.readString()
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

    companion object CREATOR : Parcelable.Creator<ArticleModelParcelize> {
        override fun createFromParcel(parcel: Parcel): ArticleModelParcelize {
            return ArticleModelParcelize(parcel)
        }

        override fun newArray(size: Int): Array<ArticleModelParcelize?> {
            return arrayOfNulls(size)
        }
    }

    }

//        fun getTitle(): String? {
//            return title
//        }
//
//        fun getAuthor(): String? {
//            return author
//        }
//
//        fun getContent(): String? {
//            return content
//        }
//
//        fun getPublishedAt(): String? {
//            return publishedAt
//        }
//
//        fun getImageUrl(): String? {
//            return urlToImage
//        }

data class SourceModel(

    val id: String?,

    val name: String?

)