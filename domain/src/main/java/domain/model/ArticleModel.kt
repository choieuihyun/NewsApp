package domain.model

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

data class SourceModel(

    val id: String?,

    val name: String?

)