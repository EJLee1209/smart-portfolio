package com.dldmswo1209.portfolio.composeUI

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// 채팅 삭제시 삭제 확인 다이얼로그
@Composable
fun DeleteDialogScreen(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteRequest: () -> Unit
){
    if(visible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = "삭제하시겠습니까?")
            },
            text = {
                Text(text = "채팅이 삭제됩니다.")
            },
            confirmButton = {
                TextButton(
                    onClick = onDeleteRequest
                ) {
                    Text(
                        text = "확인",
                        color = Color(0xFF40A6D5)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "취소",
                        color = Color(0xFF40A6D5)
                    )
                }
            },
            shape = RoundedCornerShape(18.dp)
        )
    }
}
