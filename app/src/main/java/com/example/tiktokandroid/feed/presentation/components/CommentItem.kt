package com.example.tiktokandroid.feed.presentation.components

//@Composable
//fun CommentItem(item: CommentList.Comment) {
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    ) {
//        val (profileImg, name, comment, createdOn, reply, like, dislike) = createRefs()
//
//        GlideImage(
//            model = ImageRequest.Builder(LocalContext.current)
//            .data(item.commentBy.profilePic)
//            .build(),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .size(36.dp)
//                .clip(CircleShape)
//                .background(GrayMainColor)
//                .constrainAs(profileImg) {
//                    start.linkTo(parent.start)
//                    top.linkTo(parent.top)
//                })
//
//
//        Text(text = item.commentBy.fullName,
//            style = MaterialTheme.typography.labelMedium,
//            modifier = Modifier.constrainAs(name) {
//                start.linkTo(profileImg.end, margin = 12.dp)
//                top.linkTo(profileImg.top)
//                end.linkTo(parent.end)
//                width = Dimension.fillToConstraints
//            })
//        Text(text = item.comment ?: "",
//            style = MaterialTheme.typography.bodySmall,
//            color = DarkBlue,
//            modifier = Modifier.constrainAs(comment) {
//                start.linkTo(name.start)
//                top.linkTo(name.bottom, margin = 5.dp)
//                end.linkTo(parent.end)
//                width = Dimension.fillToConstraints
//            })
//        Text(text = item.createdAt, modifier = Modifier.constrainAs(createdOn) {
//            start.linkTo(name.start)
//            top.linkTo(comment.bottom, margin = 5.dp)
//        })
//
//        Text(text = stringResource(id = R.string.reply),
//            style = MaterialTheme.typography.labelMedium,
//            modifier = Modifier.constrainAs(reply) {
//                start.linkTo(createdOn.end, margin = 16.dp)
//                top.linkTo(createdOn.top)
//                end.linkTo(like.end, margin = 4.dp)
//                width = Dimension.fillToConstraints
//            })
//
//        Row(
//            modifier = Modifier.constrainAs(like) {
//                bottom.linkTo(reply.bottom)
//                end.linkTo(dislike.start, margin = 24.dp)
//            },
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(3.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_like_outline),
//                contentDescription = null,
//                modifier = Modifier.size(18.dp)
//            )
//            item.totalLike.takeIf { it != 0L }?.let {
//                Text(text = it.toString(), fontSize = 13.sp, color = SubTextColor)
//            }
//
//        }
//
//        Row(
//            modifier = Modifier.constrainAs(dislike) {
//                bottom.linkTo(reply.bottom)
//                end.linkTo(parent.end)
//            },
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(3.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_dislike_outline),
//                contentDescription = null,
//                modifier = Modifier.size(18.dp)
//            )
//            // Text(text = "") //dislike not display
//        }
//    }
//    24.dp.Space()
//}