package com.fcadev.movieapp.model.trending

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Result(

    @ColumnInfo
    val adult: Boolean?,

    @ColumnInfo
    val backdrop_path: String?,

    @ColumnInfo
    val first_air_date: String?,

    @ColumnInfo
    val genre_ids: List<Int?>?,

    @ColumnInfo
    val id: Int?,

    @ColumnInfo
    val media_type: String?,

    @ColumnInfo
    val name: String?,

    @ColumnInfo
    val origin_country: List<String?>?,

    @ColumnInfo
    val original_language: String?,

    @ColumnInfo
    val original_name: String?,

    @ColumnInfo
    val original_title: String?,

    @ColumnInfo
    val overview: String?,

    @ColumnInfo
    val popularity: Double?,

    @ColumnInfo
    val poster_path: String?,

    @ColumnInfo
    val release_date: String?,

    @ColumnInfo
    val title: String?,

    @ColumnInfo
    val video: Boolean?,

    @ColumnInfo
    val vote_average: Double?,

    @ColumnInfo
    val vote_count: Int?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}