package com.dldmswo1209.portfolio_compose

import android.provider.MediaStore.Audio.Radio
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dldmswo1209.portfolio.Model.Chat
import com.dldmswo1209.portfolio.composeUI.AddChatDialog
import com.dldmswo1209.portfolio.composeUI.DeleteDialogScreen
import com.dldmswo1209.portfolio.composeUI.ModifyChatDialog
import com.dldmswo1209.portfolio.viewModel.MainViewModel

@Composable
fun ChatList(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    uid: String
){
    val chatList by viewModel.getChat(uid).observeAsState() // 모든 채팅 목록
    // state 가 변경되면 해당 state 를 가지고 있는 Composable 함수(뷰)가 모두 Recomposition(화면 재구성) 되면서 UI를 업데이트 한다.
    var deleteDialogVisibility by rememberSaveable { mutableStateOf(false) } // 삭제 확인 다이얼로그 visible state
    var deleteKey by rememberSaveable { mutableStateOf("") } // 삭제하려는 채팅 key state
    var modifyKey by rememberSaveable { mutableStateOf("") } // 수정하려는 채팅 key state
    var addDialogVisibility by rememberSaveable { mutableStateOf(false) } // 채팅 추가 다이얼로그 visible state
    var modifyDialogVisibility by rememberSaveable { mutableStateOf(false) } // 채팅 수정 다이얼로그 visible state

    var inputContent by remember { mutableStateOf("") }
    var selectSubject by remember { mutableStateOf("질문") }

    // 삭제확인 다이얼로그
    DeleteDialogScreen(
        visible = deleteDialogVisibility,
        onDeleteRequest = {
            viewModel.deleteChat(uid, deleteKey)
            deleteDialogVisibility = false
        },
        onDismissRequest = {
            deleteDialogVisibility = false
        }
    )

    // 채팅 추가 다이얼로그
    AddChatDialog(
        visible = addDialogVisibility,
        onDismissRequest = {
            addDialogVisibility = false
            inputContent = ""
            selectSubject = "질문"
        },
        onAddRequest = {
            // 콜백으로 채팅 추가
            var newChat: Chat
            if(selectSubject == "질문"){
                newChat = Chat(inputContent, 0)
            }else{
                newChat = Chat(inputContent, 1)
            }
            viewModel.sendChat(uid, newChat)
            addDialogVisibility = false
            inputContent = ""
            selectSubject = "질문"
        },
        inputContent = inputContent,
        selectSubject = selectSubject,
        onContentChanged = {
            inputContent = it
        },
        onSelectSubject = {
            selectSubject = it
        }
    )
    // 채팅 수정 다이얼로그
    ModifyChatDialog(
        visible = modifyDialogVisibility,
        onDismissRequest = {
            modifyDialogVisibility = false
            inputContent = ""
            selectSubject = "질문"
        },
        onModifyRequest = {
            // 콜백으로 채팅 수정
            var newChat: Chat
            if(selectSubject == "질문"){
                newChat = Chat(inputContent, 0, modifyKey)
            }else{
                newChat = Chat(inputContent, 1, modifyKey)
            }
            viewModel.modifyChat(uid, newChat)
            modifyDialogVisibility = false
            inputContent = ""
            selectSubject = "질문"
        },
        inputContent = inputContent,
        selectSubject = selectSubject,
        onContentChanged = {
            inputContent = it
        },
        onSelectSubject = {
            selectSubject = it
        }
    )

    Box() {
        chatList?.let {
            LazyColumn( // LazyColumn = RecyclerView
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
            ){
                items(chatList!!){
                    if(it.type == 0){
                        OtherChatItem(
                            chat = it,
                            longClick = {
                                // deleteDialogVisibility(state) 를 변경시키면 Composable 이 리컴포지션 되면서 삭제확인 다이얼로그를 보여준다
                                deleteDialogVisibility = true
                                deleteKey = it.key // 삭제할 chat 의 Key 를 저장 (얘도 역시 state)
                            },
                            onClick = {
                                modifyDialogVisibility = true
                                modifyKey = it.key
                                inputContent = it.content
                                selectSubject = if(it.type == 0) "질문" else "답변"
                            }
                        )
                    }else{
                        MyChatItem(
                            chat = it,
                            longClick = {
                                deleteDialogVisibility = true
                                deleteKey = it.key
                            },
                            onClick = {
                                modifyDialogVisibility = true
                                modifyKey = it.key
                                inputContent = it.content
                                selectSubject = if(it.type == 0) "질문" else "답변"
                            }
                        )
                    }
                }
            }
        }
        FloatingActionButton( // 플로팅 버튼
            onClick = { addDialogVisibility = true }, // state 를 변경시켜서 다이얼로그를 보여줌
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            backgroundColor = Color(0xFF40A6D5),
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}



