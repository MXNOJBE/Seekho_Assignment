package com.compose.seekhoassignment.data
import android.media.Rating
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetailsResponse(
    val data: Data
)


@Serializable
data class AnimeListResponse(
    val pagination: Pagination,
    val data: List<Data>
)

@Serializable
data class Data(
    @SerializedName("mal_id") val malId: Int,
    val url: String?,
    val trailer: Trailer,
    val episodes: String?,
    val rating: String?,
    val score: Float?,
    val images: Images,
    val titles: List<Title>,
    @SerializedName("title_english") val titleEnglish:String?,
    val synopsis: String?,
    val genres: List<Genre>,
    val producers: List<Producer>
)

@Serializable
data class Producer(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("type") val producerType: String?,
    @SerializedName("name") val producerName: String?,
    @SerializedName("url") val producerUrl: String?,
)

@Serializable
data class Genre(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("type") val genreType: String?,
    @SerializedName("name") val genreName: String?,
    @SerializedName("url") val genreUrl: String?,
)

@Serializable
data class JPG(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("small_image_url") val smallImageUrl: String?,
    @SerializedName("large_image_url") val largeImageUrl: String?,
)

@Serializable
data class WebP(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("small_image_url") val smallImageUrl: String?,
    @SerializedName("large_image_url") val largeImageUrl: String?,
)

@Serializable
data class ImageTypes(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("small_image_url") val smallImageUrl: String?,
    @SerializedName("medium_image_url") val mediumImageUrl: String?,
    @SerializedName("large_image_url") val largeImageUrl: String?,
    @SerializedName("maximum_image_url") val maximumImageUrl: String?,
)


@Serializable
data class Trailer(
    val url: String?,
    @SerializedName("embed_url") val embedUrl: String?,
    @SerializedName("youtube_id") val youtubeId: String?,
    //val images: List<ImageTypes>

)

@Serializable
data class Images(
    @SerializedName("jpg") val jpgType: JPG,
    @SerializedName("webp") val webPType: WebP
)

@Serializable
data class Item(
    val count: Int?,
    val total: Int?,
    @SerializedName("per_page") val perPage: Int?,
)


@Serializable
data class Pagination(
    @SerializedName("last_visible_page") val lastVisiblePage: Int?,
    @SerializedName("has_next_page") val hasNextPage: Boolean?,
    val currentPage: Int?,
    val items: Item
)


@Serializable
data class Title(
    val type: String?,
    val title: String?
)
