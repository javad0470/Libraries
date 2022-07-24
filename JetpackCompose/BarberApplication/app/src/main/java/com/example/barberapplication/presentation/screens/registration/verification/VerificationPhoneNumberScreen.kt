package com.example.barberapplication.presentation.screens.registration.verification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barberapplication.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Destination
@Composable
fun VerificationPhoneNumberScreen(
    navigator: DestinationsNavigator,
) = CompositionLocalProvider(
    LocalLayoutDirection provides LayoutDirection.Ltr
) {
    var phone by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {

            VerificationPhoneNumberSheetContent(
                onCloseSheetClicked = {
                    scope.launch(Dispatchers.IO) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                },
                onConfirmButtonClicked = {

                },
            )

        },
        sheetGesturesEnabled = false,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        content = {
            VerificationPhoneNumberContent(
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester = focusRequester),
                text = phone,
                onTextChange = {
                    phone = it
                },
                onCloseClicked = {
                    phone = ""
                },
                onDoneClicked = {
                    focusManager.clearFocus()
                    keyboard?.hide()
                },
                onButtonClicked = {
                    scope.launch(Dispatchers.IO) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                        focusManager.clearFocus()
                        keyboard?.hide()
                    }
                }
            )
        }
    )

}

@Composable
fun VerificationPhoneNumberContent(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onDoneClicked: () -> Unit,
    onButtonClicked: () -> Unit,
) {
    var showCloseIcon by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerificationPhoneNumberHeader()

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "لطفا شماره تلفن همراه خود را وارد نمایید",
            fontFamily = iranSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = MaterialTheme.colors.textColor.copy(0.8f)
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            value = text,
            onValueChange = onTextChange,
            textStyle = TextStyle(
                color = MaterialTheme.colors.textFieldsTextColor,
                fontFamily = iranSans,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            label = {
                Text(
                    text = "تلفن همراه",
                    color = MaterialTheme.colors.textFieldsTextColor.copy(0.6f),
                    fontFamily = iranSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Smartphone,
                    contentDescription = "Phone Icon"
                )
            },
            trailingIcon = {
                showCloseIcon = text.isNotEmpty()
                AnimatedVisibility(
                    visible = showCloseIcon,
                    enter = fadeIn(
                        animationSpec = tween(400)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(400)
                    )
                ) {
                    IconButton(
                        onClick = onCloseClicked
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDoneClicked.invoke()
                }
            ),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.textFieldBorderStrokeColor
            ),
            singleLine = true
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
            onClick = onButtonClicked,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.buttonColor,
                disabledBackgroundColor = MaterialTheme.colors.inActiveButtonColor,
                disabledContentColor = if (isSystemInDarkTheme()) Color.White.copy(0.5f) else Color.White
            ),
            shape = CircleShape
        ) {
            Text(
                text = "تایید",
                color = MaterialTheme.colors.buttonContentColor,
                fontFamily = iranSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }

}

@Composable
fun VerificationPhoneNumberHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        color = MaterialTheme.colors.topAppbarColor
    ) {

    }
}


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun VerificationPhoneNumberPreview() {
    VerificationPhoneNumberContent(
        modifier = Modifier
            .fillMaxSize(),
        text = "",
        onTextChange = {},
        onCloseClicked = {},
        onDoneClicked = {},
        onButtonClicked = {}
    )
}