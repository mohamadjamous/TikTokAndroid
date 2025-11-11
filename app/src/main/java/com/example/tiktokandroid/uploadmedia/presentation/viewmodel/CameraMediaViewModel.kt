package com.example.tiktokandroid.uploadmedia.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject


/**
 * Created by Puskal Khadka on 3/15/2023.
 */
@HiltViewModel
class CameraMediaViewModel @Inject constructor(
    private val userSharedPreferences: UserPreferences,
    private val getTemplateUseCase: GetTemplateUseCase
) : ViewModel() {

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    private val _viewState = MutableStateFlow<ViewState?>(null)
    val viewState = _viewState.asStateFlow()

    fun updateState(viewState:ViewState){
        _viewState.value=viewState
    }
    fun onTriggerEvent(event: CameraMediaEvent) {
        when (event) {
            CameraMediaEvent.EventFetchTemplate -> getTemplates()
        }
    }

    init {
        fetchStoredUser()
        getTemplates()
    }



    private fun getTemplates() {
        viewModelScope.launch {
            getTemplateUseCase().collect {
                updateState((viewState.value ?: ViewState()).copy(templates = it))
            }
        }
    }

    fun fetchStoredUser() {
        _currentUser.value = userSharedPreferences.getUser()
    }

}

class GetTemplateUseCase @Inject constructor(
    private val templateRepository: TemplateDataSource
) {
    operator fun invoke(): Flow<List<TemplateModel>> {
        return templateRepository.fetchTemplates()
    }
}


class TemplateDataSource @Inject constructor() {
    private val templates = listOf(
        TemplateModel(
            id = 2001,
            name = "Urban Mode",
            hint = "Please upload 1-2 photos",
            mediaUrl = "img1.jpg"
        ),
        TemplateModel(
            id = 2002,
            name = "Ocean Filter",
            hint = "Please upload 1-5 photos",
            mediaUrl = "img2.jpg"
        ),
        TemplateModel(
            id = 2003,
            name = "HyperSpeed Galaxy",
            hint = "Upload 1-3 photos",
            mediaUrl = "img3.jpg"
        ),
        TemplateModel(
            id = 2004,
            name = "Day in the Life",
            hint = "Upload 2-6 photos with a subject",
            mediaUrl = "img4.jpg"
        ),

        TemplateModel(
            id = 2005,
            name = "Manga Transition",
            hint = "Upload 2 photos to show the change",
            mediaUrl = "img5.jpg"
        ),
        TemplateModel(
            id = 2006,
            name = "Black & White",
            hint = "Please upload 1-6 photos",
            mediaUrl = "img6.jpg"
        ),
        TemplateModel(
            id = 2007,
            name = "Paint Transition",
            hint = "Upload 4 photos",
            mediaUrl = "img7.jpg"
        ),

        )

    fun fetchTemplates(): Flow<List<TemplateModel>> = flow {
        emit(templates.shuffled())
    }
}

data class TemplateModel(
    val id: Long,
    val name: String,
    val hint: String,
    val mediaUrl: String
){
    fun parseMediaLink(): Uri = Uri.parse("file:///android_asset/templateimages/${mediaUrl}")
}



data class ViewState(
    val templates: List<TemplateModel>? = null
)

sealed class CameraMediaEvent {
    object EventFetchTemplate : CameraMediaEvent()

}

enum class Tabs(@StringRes val rawValue: Int) {
    CAMERA(rawValue = R.string.camera),
    STORY(rawValue = R.string.story),
    TEMPLATES(rawValue = R.string.templates)
}

enum class PermissionType {
    CAMERA,
    MICROPHONE
}

enum class CameraController(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
)
{
    FLIP(title = R.string.flip, icon = R.drawable.ic_flip),
    SPEED(title = R.string.speed, icon = R.drawable.ic_speed),
    BEAUTY(title = R.string.beauty, icon = R.drawable.ic_profile_fill),
    FILTER(title = R.string.filters, icon = R.drawable.ic_filter),
    MIRROR(title = R.string.mirror, icon = R.drawable.ic_mirror),
    TIMER(title = R.string.timer, icon = R.drawable.ic_timer),
    FLASH(title = R.string.flash, icon = R.drawable.ic_flash),
}

enum class CameraCaptureOptions(val value: String) {
    SIXTY_SECOND("60s"),
    FIFTEEN_SECOND("15s"),
    PHOTO("Photo"),
    VIDEO("Video"),
    TEXT("Text"),
}




val decimalFormat = DecimalFormat("#.#")
fun Long.formattedCount(): String {
    return if (this < 10000) {
        this.toString()
    } else if (this < 1000000) {
        "${decimalFormat.format(this.div(1000))}K"
    } else if (this < 1000000000) {
        "${decimalFormat.format(this.div(1000000))}M"
    } else {
        "${decimalFormat.format(this.div(1000000000))}B"
    }
}

fun randomUploadDate(): String = "${(1..24).random()}h"


fun Pair<String, String>.getFormattedInternationalNumber() = "${this.first}-${this.second}".trim()

fun Context.getCurrentBrightness():Float= Settings.System.getInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
    .toFloat().div(255)


class DisableRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction>
        get() = emptyFlow()

    override suspend fun emit(interaction: Interaction) {
    }

    override fun tryEmit(interaction: Interaction): Boolean = true
}

