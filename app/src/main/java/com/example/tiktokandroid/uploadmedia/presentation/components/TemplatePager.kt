package com.example.tiktokandroid.uploadmedia.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.feed.presentation.components.MediumSpace
import com.example.tiktokandroid.feed.presentation.components.SmallSpace
import com.example.tiktokandroid.feed.presentation.components.Space
import com.example.tiktokandroid.theme.SubTextColor
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.TemplateModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.TemplatePager(templates: List<TemplateModel>) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { templates.size })

    val currentItem by remember {
        derivedStateOf {
            pagerState.settledPage
        }
    }

    Text(text = templates[currentItem].name, style = MaterialTheme.typography.displayMedium)
    6.dp.Space()
    Text(
        text = templates[currentItem].hint,
        style = MaterialTheme.typography.labelLarge,
        color = SubTextColor
    )
    MediumSpace()
    HorizontalPager(
        contentPadding = PaddingValues(horizontal = 64.dp),
        state = pagerState,
        modifier = Modifier.weight(1f)
    ) {
        SingleTemplateCard(page = it, pagerState = pagerState, item = templates[it])
    }
    SmallSpace()
    Text(
        text = "${currentItem.plus(1)}/${templates.size}",
        color = SubTextColor,
        style = MaterialTheme.typography.labelMedium
    )
    SmallSpace()
}
