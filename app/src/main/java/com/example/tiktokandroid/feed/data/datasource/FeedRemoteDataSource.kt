package com.example.tiktokandroid.feed.data.datasource

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.feed.data.model.CommentList
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

    suspend fun updateLikeState(videoId: String, liked: Boolean): Result<Unit> {
        return try {
            val collectionRef = firestore.collection("posts").document(videoId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(collectionRef)
                val currentLikes = snapshot.getLong("videoStats.likes") ?: 0L

                val newLikes = if (liked) currentLikes + 1 else maxOf(currentLikes - 1, 0L)

                transaction.update(collectionRef, "videoStats.likes", newLikes)
            }.await()

            Result.success(Unit)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getCommentList(videoId: String): Result<CommentList> {
        return try {
            val postRef = firestore.collection("posts").document(videoId)

            // Get all comment documents under "comments" subcollection
            val commentsSnapshot = postRef.collection("comments").get().await()

            // Map Firestore documents into Comment objects
            val comments = commentsSnapshot.documents.mapNotNull { doc ->
                try {
                    val userMap = doc.get("commentBy") as? Map<*, *>
                    val user = userMap?.let {
                        User(
                            id = it["id"] as? String ?: "",
                            username = it["username"] as? String ?: "",
                            email = it["email"] as? String ?: "",
                            phone = it["phone"] as? String ?: "",
                            profileImageUrl = it["profileImageUrl"] as? String ?: "",
                            fullName = it["fullName"] as? String ?: "",
                            bio = it["bio"] as? String ?: "",
                            dob = it["dob"]?.toString() ?: "",
                            followers = ((it["followers"] as? Long ?: 0L).toInt()),
                            following = ((it["following"] as? Long ?: 0L).toInt()),
                            likes = ((it["likes"] as? Long ?: 0L).toInt())
                        )
                    }

                    CommentList.Comment(
                        commentBy = user ?: User(),
                        comment = doc.getString("comment"),
                        createdAt = doc.getString("createdAt") ?: "",
                        totalLike = doc.getLong("totalLike") ?: 0L,
                        totalDisLike = doc.getLong("totalDisLike") ?: 0L,
                        threadCount = doc.getLong("threadCount")?.toInt() ?: 0,
                        thread = emptyList()
                    )
                } catch (e: Exception) {
                    null
                }
            }

            // Build CommentList object
            val result = CommentList(
                videoId = videoId,
                totalComment = comments.size,
                comments = comments,
                isCommentPrivate = false
            )

            Result.success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }




}