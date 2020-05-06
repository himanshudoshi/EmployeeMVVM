package com.telstra.telstramvvm.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.telstra.telstramvvm.data.model.Facts
import com.telstra.telstramvvm.data.model.RowsItem

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun convertTagsListToDb(tags: List<RowsItem>?): String? =
        if (tags != null) gson.toJson(tags) else null

    @TypeConverter
    fun convertDbToTags(value: String?): List<RowsItem> {
        if (value != null) {
            return gson.fromJson(value, Facts.messageOptionListType)
        }
        return listOf()
    }
}