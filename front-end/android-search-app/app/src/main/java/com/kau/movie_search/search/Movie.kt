package com.kau.movie_search.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie (var title : String = "", var link : String = "", var image : String = "", var subTitle : String = "", var pubDate : String = "", var director : String = "", var actor : String = "", var userRating : String = "") : Parcelable