package com.example.tiktokandroid.feed.data.datasource

import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.feed.data.model.CommentList
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
            val commentsSnapshot = postRef.collection("comments")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

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
                            followers = (it["followers"] as? Long ?: 0L).toInt(),
                            following = (it["following"] as? Long ?: 0L).toInt(),
                            likes = (it["likes"] as? Long ?: 0L).toInt()
                        )
                    }

                    CommentList.Comment(
                        commentBy = user ?: User(),
                        comment = doc.getString("comment") ?: "",
                        createdAt = doc.getString("createdAt") ?: "",
                        totalLike = doc.getLong("totalLike") ?: 0L,
                        totalDisLike = doc.getLong("totalDisLike") ?: 0L,
                        threadCount = doc.getLong("threadCount")?.toInt() ?: 0,
                        thread = emptyList()
                    )
                } catch (_: Exception) {
                    null
                }
            }

            Result.success(
                CommentList(
                    videoId = videoId,
                    totalComment = comments.size,
                    comments = comments,
                    isCommentPrivate = false
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    suspend fun createComment(comment: CommentList.Comment): Result<CommentList.Comment> {
        return try {
            val postRef = firestore.collection("posts").document(comment.videoId)
            val commentsRef = postRef.collection("comments")

            val commentMap = hashMapOf(
                "videoId" to comment.videoId,
                "commentBy" to hashMapOf(
                    "id" to comment.commentBy.id,
                    "username" to comment.commentBy.username,
                    "profileImageUrl" to comment.commentBy.profileImageUrl,
                    "email" to comment.commentBy.email,
                    "phone" to comment.commentBy.phone
                ),
                "comment" to comment.comment,
                "createdAt" to comment.createdAt,
                "totalLike" to comment.totalLike,
                "totalDisLike" to comment.totalDisLike,
                "threadCount" to comment.threadCount,
                "thread" to comment.thread
            )

            // ONLY add to subcollection
            commentsRef.add(commentMap).await()

            // INCREMENT comment count INSIDE THE POST DOCUMENT
            postRef.update("videoStats.comments", FieldValue.increment(1)).await()


            Result.success(comment)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

   suspend fun updateSavedState(videoId: String, saved: Boolean): Result<Unit> {
        return try {
            val collectionRef = firestore.collection("posts").document(videoId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(collectionRef)
                val currentLikes = snapshot.getLong("videoStats.favourites") ?: 0L

                val newLikes = if (saved) currentLikes + 1 else maxOf(currentLikes - 1, 0L)

                transaction.update(collectionRef, "videoStats.favourites", newLikes)
            }.await()

            Result.success(Unit)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


}