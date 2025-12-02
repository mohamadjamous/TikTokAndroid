package com.example.tiktokandroid.feed.domain.utils

import androidx.room.TypeConverter
import com.example.tiktokandroid.core.presentation.model.AudioModel
import com.example.tiktokandroid.core.presentation.model.HasTag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jakarta.inject.Inject

class Converter {

    @TypeConverter
    fun fromHashtagList(list: List<HasTag>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toHasTagList(hasTagsString: String?): List<HasTag> {
        if (hasTagsString.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<HasTag>>() {}.type
        return Gson().fromJson(hasTagsString, type)
    }

    @TypeConverter
    fun fromAudioModel(model: AudioModel?): String {
        return Gson().toJson(model)
    }

    @TypeConverter
    fun toAudioModel(json: String?): AudioModel? {
        if (json.isNullOrEmpty()) return null
        return Gson().fromJson(json, AudioModel::class.java)
    }

}
