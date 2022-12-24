package com.dldmswo1209.portfolio.composeUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

// 커스텀 다이얼로그
@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
){
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        content()
    }
}

// 채팅 추가 다이얼로그
@Composable
fun AddChatDialog(
    visible: Boolean,
    modifyMode: Boolean,
    onDismissRequest: () -> Unit,
    onAddRequest: () -> Unit,
    inputContent: String,
    selectSubject: String,
    onContentChanged: (String) -> Unit,
    onSelectSubject: (String) -> Unit
){

    if(visible){
        CustomAlertDialog(
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = Color.White),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = if(modifyMode) "채팅 수정하기" else "채팅 추가하기")
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    thickness = 1.dp,
                    color = Color.Black
                )
                OutlinedTextField(
                    value = inputContent,
                    onValueChange = { onContentChanged(it) },
                    label = { Text(text = "내용") },
                    maxLines = 3
                )

                RadioGroup(
                    mItems = listOf("질문","답변"),
                    selected = selectSubject,
                    setSelected = { onSelectSubject(it) })

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick =  onAddRequest,
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFF40A6D5)
                        )
                    ) {
                        Text(text = "완료")
                    }

                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFF40A6D5)
                        )
                    ) {
                        Text(text = "취소")
                    }
                }
                Spacer(modifier = Modifier.size(50.dp))

            }
        }
    }
}

// 라디오 버튼 그룹
@Composable
fun RadioGroup(
    mItems: List<String>,
    selected: String,
    setSelected: (selected: String)-> Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        mItems.forEach { item->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selected == item,
                    onClick = {
                        setSelected(item)
                    },
                    enabled = true,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF40A6D5)
                    )
                )
                Text(text = item, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }

}