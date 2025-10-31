package com.example.tiktokandroid.feed.data.datasource

import com.example.tiktokandroid.core.presentation.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FeedRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun fetchPosts(num: Int = 5, lastVisibleId: String? = null): Result<List<Post>> {

        return try {
            val firestore = firestore
            var query = firestore.collection("posts")
                .orderBy("id") // or "timestamp" if you have one
                .limit(num.toLong())

            // If this is a pagination request, start after the last post ID
            if (lastVisibleId != null) {
                val lastDocSnapshot = firestore.collection("posts")
                    .document(lastVisibleId)
                    .get()
                    .await()

                if (lastDocSnapshot.exists()) {
                    query = query.startAfter(lastDocSnapshot)
                }
            }

            val snapshot = query.get().await()
            val posts = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Post::class.java)
            }

            Result.success(posts)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


}