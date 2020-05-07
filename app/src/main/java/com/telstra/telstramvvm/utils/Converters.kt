package com.telstra.telstramvvm.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.telstra.telstramvvm.data.model.Facts
import com.telstra.telstramvvm.data.model.FactsItem

/**
 * converter class to convert object
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