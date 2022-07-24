package com.example.barberapplication.presentation.screens.registration.verification

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barberapplication.presentation.component.OtpComponent
import com.example.barberapplication.ui.theme.*

@ExperimentalComposeUiApi
@Composable
fun VerificationPhoneNumberSheetContent(
    onCloseSheetClicked: () -> Unit,
    onConfirmButtonClicked: () -> Unit,
) {
    var buttonActivation by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .height(325.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onCloseSheetClicked
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = MaterialTheme.colors.iconTintColor
                        )
                    }

                    Text(
                        text = "کد تایید",
                        color = MaterialTheme.colors.textColor,
                        fontFamily = iranSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    text = "لطفا کد تاییدی که به شماره موبایل 09399552081 ارسال شده است را وارد نمایید",
                    fontFamily = iranSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.textColor.copy(0.8f),
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(16.dp))

                OtpComponent(
                    value = { otpText ->
                        buttonActivation = otpText.length == 5
                    }
                )

                Spacer(modifier = Modifier.size(24.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    text = "2:36",
                    fontFamily = iranSans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.textColor.copy(0.8f),
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(
                            start = 24.dp,
                            end = 24.dp
                        ),
                    onClick = onConfirmButtonClicked,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.buttonColor,
                        disabledBackgroundColor = MaterialTheme.colors.inActiveButtonColor,
                        disabledContentColor = if (isSystemInDarkTheme()) Color.White.copy(
                            0.5f) else Color.White,
                        contentColor = MaterialTheme.colors.buttonContentColor
                    ),
                    shape = CircleShape,
                    enabled = buttonActivation
                ) {
                    Text(
                        text = "تایید",
                        fontFamily = iranSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

}

@ExperimentalComposeUiApi
@Preview
@Composable
fun SheetPreview() {
    VerificationPhoneNumberSheetContent(
        onCloseSheetClicked = {},
        onConfirmButtonClicked = {},
    )
}