package com.dldmswo1209.portfolio_compose

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateRectAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dldmswo1209.portfolio.Model.Chat
import com.dldmswo1209.portfolio.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OtherChatItem(
    modifier: Modifier = Modifier,
    chat: Chat,
    longClick: ()-> Unit,
    onClick: (Chat) -> Unit
){
    Row() {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = modifier
                .clip(CircleShape)
                .size(40.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = modifier.width(8.dp))
        Column() {
            Text(
                text = "채용 담당자",
            )
            Spacer(modifier = Modifier.height(3.dp))
            Card(
                shape = RoundedCornerShape(topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp),
                backgroundColor = Color(0xFFB8B8B8),
                elevation = 10.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.Start)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        )
                    )
                    .combinedClickable(
                        onClick = {
                            onClick(chat)
                        },
                        onLongClick = longClick
                    )
            ) {
                Text(
                    text = chat.content,
                    modifier = Modifier
                        .padding(5.dp)
                        .widthIn(max = 280.dp),
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyChatItem(
    modifier: Modifier = Modifier,
    chat: Chat,
    longClick:  ()->Unit,
    onClick: (Chat) -> Unit
){
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Row(modifier = modifier
        .fillMaxWidth()
        .wrapContentWidth(align = Alignment.End)
    ) {
        IconButton(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.align(Alignment.Bottom)
        ){
            Icon(
                imageVector = if(isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = null,
            )
        }
        Card(
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp),
            backgroundColor = Color(0xFF90CAF9),
            elevation = 10.dp,
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessVeryLow,
                    )
                )
                .combinedClickable(
                    onClick = {
                        onClick(chat)
                    },
                    onLongClick = longClick
                )
        ) {
            Text(
                text = chat.content,
                modifier = Modifier
                    .padding(5.dp)
                    .widthIn(max = 280.dp),
                maxLines =
                if(isExpanded) {
                    Int.MAX_VALUE
                } else{
                    10
                },
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Preview()
@Composable
private fun OtherChatPreview(){
    MaterialTheme {
//        OtherChatItem(chat = Chat("hihihiih",0,""))
    }
}

@Preview()
@Composable
private fun MyChatPreview(){
    MaterialTheme {
//        MyChatItem(chat = Chat("hihihihi", 1, ""))
    }
}