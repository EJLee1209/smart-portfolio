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

// 채팅 추가/수정 다이얼로그
@Composable
fun AddChatDialog(
    visible: Boolean, // dialog visibility state
    modifyMode: Boolean, // 수정할건지 추가할건지 state
    onDismissRequest: () -> Unit, // 취소를 눌렀을 때 로직
    onAddRequest: () -> Unit, // 추가/수정을 눌렀을 때 로직
    inputContent: String, // TextField content state
    selectSubject: String, // Radio Button State
    onContentChanged: (String) -> Unit, // TextFiled state 변경시 로직
    onSelectSubject: (String) -> Unit // Radio Button state 변경시 로직
){
    if(visible){ // visibility 가 true 면 다이얼로그를 보여줌
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
                    maxLines = 5,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF40A6D5),
                        cursorColor = Color(0xFF40A6D5),
                        focusedLabelColor = Color(0xFF40A6D5)
                    )
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