package com.example.tiktokandroid.profile.presentation.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.components.CircularGlideImage
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokLightGray

@Composable
fun ProfileHeaderView(
    modifier: Modifier = Modifier,
    user: User? = null
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {

        CircularGlideImage(imageUrl = user?.profileImageUrl ?: "", size = 115)

        Text(
            text = user?.username ?: "",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )


        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${user?.following ?: 0}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "following",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${user?.followers ?: 0}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "followers",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Column(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${user?.likes ?: 0}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "likes",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }


            }

        }



        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            containerColor = TikTokLightGray,
            text = "Edit Profile",
            height = 35,
            onClick = {

            }
        )


    }


}


@Composable
@Preview(showSystemUi = true)
fun ProfileHeaderViewPreview() {
    ProfileHeaderView()
}