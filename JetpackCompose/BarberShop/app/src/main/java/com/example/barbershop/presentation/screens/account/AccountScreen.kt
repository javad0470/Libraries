package com.example.barbershop.presentation.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.barbershop.R
import com.example.barbershop.data.response.account.UserInfoResult
import com.example.barbershop.presentation.component.LoadingScreen
import com.example.barbershop.presentation.component.RemoteErrorScreen
import com.example.barbershop.presentation.screens.destinations.EditUserInformationScreenDestination
import com.example.barbershop.ui.theme.*
import com.example.barbershop.utils.RequestState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.example.barbershop.utils.Constants.SUCCESSFULLY
import com.example.barbershop.utils.Constants.UNEXPECTED_ERROR

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AccountScreen(
    navigator: DestinationsNavigator,
    accountViewModel: AccountViewModel,
) {
    val userInfo = accountViewModel.userInfo.collectAsState().value
    var isRefreshing by remember { mutableStateOf(false) }

    when (userInfo) {
        is RequestState.Idle -> {
            LaunchedEffect(key1 = userInfo) {
                accountViewModel.getUserInfo()
            }
        }
        is RequestState.Loading -> {
            LoadingScreen()
        }
        is RequestState.Success -> {
            when (userInfo.data.contentType) {
                SUCCESSFULLY -> {
                    AccountScreenContent(
                        editInformationClicked = {
                            accountViewModel.addTempData(userInfo.data.result)
                            navigator.navigate(
                                EditUserInformationScreenDestination(userInfo.data.result)
                            )
                        },
                        userInfoResult = userInfo.data.result
                    )
                }
                UNEXPECTED_ERROR -> {
                    RemoteErrorScreen(
                        onRefresh = {
                            isRefreshing = true
                            accountViewModel.getUserInfo()
                            isRefreshing = false

                        },
                        isRefreshing = isRefreshing
                    )
                }
            }
        }
        is RequestState.Error -> {
            RemoteErrorScreen(
                onRefresh = {
                    isRefreshing = true
                    accountViewModel.getUserInfo()
                    isRefreshing = false

                },
                isRefreshing = isRefreshing
            )
        }
    }
}

@Composable
fun AccountScreenContent(
    editInformationClicked: () -> Unit,
    userInfoResult: UserInfoResult,
) {
    Scaffold(
        modifier = Modifier
            .padding(bottom = 56.dp)
            .navigationBarsPadding()
            .fillMaxSize(),
        topBar = { AccountTopAppBar() },
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.backgroundColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UserInformationHeaderCard(
                        editInformationClicked = editInformationClicked,
                        userInfoResult = userInfoResult
                    )

                    SupportSectionCard()

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Version 1.0.0 Beta",
                        fontFamily = FontFamily.Default,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.textColor.copy(ContentAlpha.medium)
                    )
                }
            }
        }
    )
}

@Composable
fun UserInformationHeaderCard(
    editInformationClicked: () -> Unit,
    userInfoResult: UserInfoResult,
) {
    Card(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.itemsSurfaceColor,
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (column, button, box) = createRefs()

            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .wrapContentSize()
                    .constrainAs(column) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                UserInformationItem(
                    icon = R.drawable.ic_user_salon,
                    title = "${userInfoResult.firstName} ${userInfoResult.lastName}",
                )
                UserInformationItem(
                    icon = R.drawable.ic_iphone,
                    title = userInfoResult.phone,
                )
                UserInformationItem(
                    icon = R.drawable.ic_city,
                    title = "${userInfoResult.province} / ${userInfoResult.city}",
                )
                UserInformationItem(
                    icon = R.drawable.ic_email,
                    title = userInfoResult.email,
                )
            }

            TextButton(
                modifier = Modifier
                    .padding(end = 4.dp, top = 4.dp)
                    .width(100.dp)
                    .constrainAs(button) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                onClick = editInformationClicked,
                shape = CircleShape,
                contentPadding = PaddingValues(4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "ویرایش",
                        color = MaterialTheme.colors.buttonColor,
                        fontFamily = iranSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Icon",
                        tint = MaterialTheme.colors.buttonColor
                    )
                }
            }


            Surface(
                modifier = Modifier
                    .padding(end = 4.dp, bottom = 4.dp)
                    .wrapContentSize()
                    .constrainAs(box) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                color = MaterialTheme.colors.textFieldBorderStrokeColor,
                shape = RoundedCornerShape(bottomEnd = 16.dp, topStart = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp, start = 32.dp, end = 32.dp)
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = when (userInfoResult.gender) {
                            "male" -> painterResource(id = R.drawable.ic_male)
                            "female" -> painterResource(id = R.drawable.ic_female)
                            else -> painterResource(id = R.drawable.ic_male)
                        },
                        contentDescription = "Edit Icon",
                        tint = MaterialTheme.colors.buttonContentColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = when (userInfoResult.gender) {
                            "male" -> "آقا"
                            "female" -> "خانم"
                            else -> "آقا"
                        },
                        color = MaterialTheme.colors.buttonContentColor,
                        fontFamily = iranSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

}


@Composable
fun SupportSectionCard() {
    Card(
        modifier = Modifier
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.itemsSurfaceColor,
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SupportSectionItem(
                onItemClicked = {},
                icon = R.drawable.ic_transaction,
                title = "سوابق تراکنش ها",
            )

            SupportSectionItem(
                onItemClicked = {},
                icon = R.drawable.ic_comment2,
                title = "نظرات شما",
            )

            SupportSectionItem(
                onItemClicked = {},
                icon = R.drawable.ic_bug,
                title = "گزارش خطا در برنامه",
            )

            SupportSectionItem(
                onItemClicked = {},
                icon = R.drawable.ic_support,
                title = "درخواست پشتیبانی",
            )

            SupportSectionItem(
                onItemClicked = {},
                icon = R.drawable.ic_book,
                title = "قوانین و مقررات",
            )

            SupportSectionItem(
                onItemClicked = {},
                icon = R.drawable.ic_phone,
                title = "تماس با ما",
            )

            SupportSectionItem(
                onItemClicked = {},
                icon = R.drawable.ic_exit,
                title = "خروج از حساب کاربری",
                contentColor = MaterialTheme.colors.exitIconColor,
                lineVisibility = false,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileInformationPreview() {
    AccountScreenContent(
        editInformationClicked = {},
        userInfoResult = UserInfoResult(
            birthday = "",
            cityId = 0,
            city = "فردوس",
            firstName = "محمد جواد",
            gender = "male",
            id = "",
            lastName = "عربی",
            phone = "09399552081",
            province = "خراسان جنوبی",
            email = ""
        )
    )
}
