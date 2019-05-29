package com.kau.movie_search.search

class MovieDetail (val platform : ArrayList<PlatformInfo>) {

    class PlatformInfo (var monetization_type : String = "", var provider_id : Int = 0, var presentation_type : String ="")
}