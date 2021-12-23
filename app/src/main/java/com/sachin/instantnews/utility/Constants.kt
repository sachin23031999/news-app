package com.sachin.instantnews.utility

class Constants {
    companion object {
        val API_KEY = ""
        val BASE_URL = "https://newsapi.org/v2/top-headlines"
        val SOURCE_URL = "https://newsapi.org/v2/top-headlines/sources?apiKey=$API_KEY"
        val AUTH_KEY = ""
    }

    interface  SharedPrefKeys {
        companion object {
            val TOP_HEADLINES = "top_headlines"
        }
    }

    interface TopHeadKeys {
        companion object {
            val STATUS = "status"
            val TOTAL_RESULTS = "totalResults"
            val ARTICLES = "articles"
            val AUTHOR = "author"
            val SOURCE = "source"
            val SOURCE_NAME = "name"
            val TITLE = "title"
            val DESCRIPTION = "description"
            val URL = "url"
            val IMAGE_URL = "urlToImage"
            val TIME_STAMP = "publishedAt"
            val CONTENT = "content"
        }
    }

    interface SourceDetailKeys {
        companion object {
            val LOCATION = ""
        }
    }

    interface Country {
        //"Nepal", "USA", "India", "Sri Lanka", "England", "Sweden", "Pacific Islands"
        companion object {
            val NEPAL = "np"
            val USA = "us"
            val INDIA = "in"
            val SRI_LANKA = "lk"
            val ENGLAND = "en"
            val SWEDEN = "se"
            val PACIFIC_ISLANDS = "pc"
        }
    }
}