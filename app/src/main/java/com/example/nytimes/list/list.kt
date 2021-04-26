package com.example.nytimes.list

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


data class list(
    val status: String,
    val copyright: String,
    val section: String,
    val last_updated : String,
    val num_results: String,
    val results: List<resultsList>

)


@Parcelize
data class resultsList(
    val section: String,
    val subsection: String,
    val title: String,
    val abstract: String,
    val url: String,
    val uri: String,
    val byline: String,
    val item_type: String,
    val updated_date: String,
    val created_date: String,
    val published_date: String,
    val material_type_facet: String,
    val kicker: String,

    @SerializedName("des_facet")
    val desFacet: List<String>,
    @SerializedName("org_facet")
    val orgFacet: List<String>,
    @SerializedName("per_facet")
    val perFacet: List<String>,
    @SerializedName("geo_facet")
    val geoFacet: List<String>,

    val multimedia: @RawValue  List<multimediaList>,
    val short_url: String,

): Parcelable{
    fun getListImage(): multimediaList {

        val ao = this.multimedia.filter {
            it.format == "superJumbo"
        }.first()

       return ao
    }
}

data class multimediaList(
    var url: String,
    var format: String,
    var height: Int,
    var width: Int,
    var type: String,
    var subtype: String,
    var caption: String,
    var copyright: String,

)
