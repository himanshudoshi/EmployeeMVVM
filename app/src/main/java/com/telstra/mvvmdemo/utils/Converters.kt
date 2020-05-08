package com.telstra.mvvmdemo.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.telstra.mvvmdemo.data.model.Facts
import com.telstra.mvvmdemo.data.model.FactsItem

/**
 * converter class to store the custom type of objects in Database.
 */
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun convertTagsListToDb(tags: List<FactsItem>?): String? =
        if (tags != null) gson.toJson(tags) else null

    @TypeConverter
    fun convertDbToTags(value: String?): List<FactsItem> {
        if (value != null) {
            return gson.fromJson(value, Facts.messageOptionListType)
        }
        return listOf()
    }
}