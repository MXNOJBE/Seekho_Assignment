package com.compose.seekhoassignment.data

data class AnimeListUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val result: AnimeListResponse = AnimeListResponse(
        data = emptyList(),
        pagination =  Pagination(
            0,
            true,
            1,
            Item(
                25,
                0,
                0
            ))
    ),
)

data class AnimeDetailUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val result: AnimeDetailsResponse = AnimeDetailsResponse(
        Data(
            malId = 12345,
            url = "https://myanimelist.net/anime/12345/example_anime",
            episodes = "12",
            rating = "PG-13",
            score = 8.5f,
            images = Images(
                JPG("", "", ""),
                WebP("", "", "")
            ),
            trailer  = Trailer("","", ""),
            titles = emptyList(),
            titleEnglish = "",
            synopsis = "",
            genres = emptyList(),
            producers = emptyList()
        )
    )
)


