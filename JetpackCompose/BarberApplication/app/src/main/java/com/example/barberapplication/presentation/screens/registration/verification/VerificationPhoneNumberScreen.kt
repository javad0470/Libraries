package com.example.barberapplication.presentation.screens.registration.verification

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
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
import androidx.compose.ui.platform.LocalContext
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barberapplication.R
import com.example.barberapplication.presentation.component.LottieView
import com.example.barberapplication.presentation.component.MessageBar
import com.example.barberapplication.presentation.screens.destinations.VerificationCodeScreenDestination
import com.example.barberapplication.presentation.screens.registration.RegistrationViewModel
import com.example.barberapplication.ui.theme.*
import com.example.barberapplication.utils.Constant.LONG_DURATION
import com.example.barberapplication.utils.Constant.NOT_EXPIRED_CODE
import com.example.barberapplication.utils.Constant.PHONE_NUMBER_INVALID
import com.example.barberapplication.utils.Constant.PHONE_REQUIRED
import com.example.barberapplication.utils.Constant.REQUEST_VALUES_INVALID
import com.example.barberapplication.utils.Constant.SEND_CODE_FAILED
import com.example.barberapplication.utils.Constant.SIGN_UP_REQUIRED
import com.example.barberapplication.utils.Constant.SUCCESSFULLY
import com.example.barberapplication.utils.Constant.TOO_MANY_REQUESTS
import com.example.barberapplication.utils.Constant.UNEXPECTED_ERROR
import com.example.barberapplication.utils.HandleHttpResponse
import com.example.barberapplication.utils.MessageBarType
import com.example.barberapplication.utils.RequestState
import com.example.barberapplication.viewmodels.SharedViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.ktor.client.features.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Destination
@Composable
fun VerificationPhoneNumberScreen(
    navigator: DestinationsNavigator,
    registrationViewModel: RegistrationViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel,
) = CompositionLocalProvider(
    LocalLayoutDirection provides LayoutDirection.Ltr
) {
    val sendCodeResponse = registrationViewModel.sendCodeResponse.collectAsState().value
    var loadingState by remember { mutableStateOf(false) }
    val activity = LocalContext.current as Activity

    BackHandler {
        activity.finish()
    }

    LaunchedEffect(key1 = sendCodeResponse) {
        when (sendCodeResponse) {
            is RequestState.Idle -> loadingState = false
            is RequestState.Loading -> loadingState = true
            is RequestState.Success -> {
                loadingState = false
                Log.d("Kotlin", "VerificationPhone: ${sendCodeResponse.data}")
                handleSuccessState(
                    navigator = navigator,
                    registrationViewModel = registrationViewModel,
                    sharedViewModel = sharedViewModel,
                    remainingTime = sendCodeResponse.data.result?.remainingTime ?: 0,
                    contentType = sendCodeResponse.data.contentType,
                )
                registrationViewModel.cleanSendCodeResponse()
            }
            is RequestState.Error -> {
                Log.d("Kotlin", sendCodeResponse.message.toString())
                loadingState = false
                registrationViewModel.messageBarState.update { messageBar ->
                    messageBar.copy(
                        messageBarType = MessageBarType.Warning,
                        message = when (sendCodeResponse.message) {
                            is ClientRequestException -> HandleHttpResponse
                                .clientRequestException(sendCodeResponse.message.response)
                            is ServerResponseException -> "خطای غیرمنتظره از سمت سرور"
                            is Exception -> "خطای غیر منتظره"
                            else -> "خطای غیر منتظره"
                        }
                    )
                }
                registrationViewModel.cleanSendCodeResponse()
            }
        }
    }

    VerificationPhoneNumberUi(
        registrationViewModel = registrationViewModel,
        loadingState = loadingState,
        onCloseClicked = {
            sharedViewModel.userPhone = ""
        },
        phone = sharedViewModel.userPhone,
        onTextChange = {
            if (it.length >= 12) {
                sharedViewModel.userPhone.dropLast(11)
            } else {
                sharedViewModel.userPhone = it
            }
        },
    )


}

@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun VerificationPhoneNumberUi(
    registrationViewModel: RegistrationViewModel,
    loadingState: Boolean,
    onCloseClicked: () -> Unit,
    onTextChange: (String) -> Unit,
    phone: String,
) = CompositionLocalProvider(
    LocalLayoutDirection provides LayoutDirection.Ltr
) {

    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize(),
        content = { paddingValues ->
            ConstraintLayout(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                VerificationPhoneNumberContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester = focusRequester),
                    text = phone,
                    onTextChange = onTextChange,
                    onCloseClicked = onCloseClicked,
                    onDoneClicked = {
                        focusManager.clearFocus()
                        keyboard?.hide()

                        registrationViewModel.checkPhoneNumberValidation(phone = phone)
                    },
                    loadingState = loadingState,
                )

                MessageBar(messageBarState = registrationViewModel.messageBarState)
            }
        }
    )
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
fun handleSuccessState(
    navigator: DestinationsNavigator,
    registrationViewModel: RegistrationViewModel,
    sharedViewModel: SharedViewModel,
    contentType: String,
    remainingTime: Int,
) {
    when (contentType) {
        SUCCESSFULLY -> {
            registrationViewModel.messageBarState.update { messageBar ->
                messageBar.copy(
                    messageBarType = MessageBarType.Warning,
                    message = "کد ورود با موفقیت ارسال شد"
                )
            }
            sharedViewModel.remainingTime = remainingTime
            navigator.navigate(VerificationCodeScreenDestination)
        }
        NOT_EXPIRED_CODE -> {
            registrationViewModel.messageBarState.update { messageBar ->
                messageBar.copy(
                    messageBarType = MessageBarType.Warning,
                    message = "کد ورود قبلی هنوز منقضی نشده است"
                )
            }
            sharedViewModel.remainingTime = remainingTime
            navigator.navigate(VerificationCodeScreenDestination)
        }
        SEND_CODE_FAILED -> {
            registrationViewModel.messageBarState.update { messageBar ->
                messageBar.copy(
                    messageBarType = MessageBarType.Warning,
                    message = "خطا در ارسال کد لطفا مجدد تلاش نمایید"
                )
            }
        }
        TOO_MANY_REQUESTS -> {
            registrationViewModel.messageBarState.update { messageBar ->
                messageBar.copy(
                    messageBarType = MessageBarType.Warning,
                    message = "درخواست های پیاپی غیر مجاز"
                )
            }
        }
        REQUEST_VALUES_INVALID -> {
            registrationViewModel.messageBarState.update { messageBar ->
                messageBar.copy(
                    messageBarType = MessageBarType.Warning,
                    message = "شماره موبایل صحیح نمی باشد"
                )
            }
        }
        SIGN_UP_REQUIRED -> {
            registrationViewModel.messageBarState.update { messageBar ->
                messageBar.copy(
                    messageBarType = MessageBarType.Warning,
                    message = "برای ثبت نام به سایت مراجعه نمایید",
                    duration = LONG_DURATION
                )
            }
        }
        UNEXPECTED_ERROR -> {
            registrationViewModel.messageBarState.update { messageBar ->
                messageBar.copy(
                    messageBarType = MessageBarType.Warning,
                    message = "خطای غیر منتظره از سمت سرور"
                )
            }
        }
        else -> {
            registrationViewModel.messageBarState.update { messageBar ->
                messageBar.copy(
                    messageBarType = MessageBarType.Warning,
                    message = "خطای غیر منتظره"
                )
            }
        }
    }
}

@Composable
fun VerificationPhoneNumberHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(BarberShopTheme.dimens.verificationPhoneHeader),
        color = MaterialTheme.colors.topAppbarColor
    ) {
        LottieView(
            modifier = Modifier.fillMaxSize(),
            lottie = R.raw.welcome
        )
    }
}
