package com.example.tiktokandroid.feed.data.datasource

import com.example.tiktokandroid.core.presentation.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FeedRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun fetchPosts(num: Int, lastVisibleId: String? = null): Result<List<Post>> {

        return try {
            val collectionRef = firestore.collection("posts")
                .orderBy("id")
                .limit(num.toLong())

            val query = if (lastVisibleId != null) {
                val lastDoc = firestore.collection("posts").document(lastVisibleId).get().await()
                if (lastDoc.exists()) collectionRef.startAfter(lastDoc) else collectionRef
            } else {
                collectionRef
            }

            val snapshot = query.get().await()
            val posts = snapshot.documents.mapNotNull { it.toObject(Post::class.java) }

            Result.success(posts)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


}