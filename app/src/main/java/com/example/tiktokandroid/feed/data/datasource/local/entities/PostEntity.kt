package com.example.tiktokandroid.feed.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tiktokandroid.core.presentation.model.AudioModel
import com.example.tiktokandroid.core.presentation.model.HasTag
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.presentation.model.VideoStats
import com.example.tiktokandroid.core.presentation.model.ViewerInteraction


@Entity(tableName = "post_table")
data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "post_id")
    val postId: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "video_url")
    val videoUrl: String,

    @Embedded(prefix = "author")
    val authorDetails: User,

    @Embedded
    val videoStats: VideoStats,

    @Embedded
    val currentViewerInteraction: ViewerInteraction,

    @ColumnInfo(name = "audio_model")
    val audioModel: AudioModel?,

    @ColumnInfo(name = "allow_comments")
    val allowComments: Boolean,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "created_at")
    var createdAt: Long,

    @ColumnInfo(name = "hashtags")
    val hasTags: List<HasTag> = emptyList()
)



