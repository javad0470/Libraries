package com.example.barbershop.presentation.screens.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barbershop.R
import com.example.barbershop.presentation.screens.destinations.EditUserInformationScreenDestination
import com.example.barbershop.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalComposeUiApi
@Composable
fun AccountScreen(
    navigator: DestinationsNavigator,
) {
    Scaffold(
        topBar = {
            AccountTopAppBar()
        },
        content = {
            AccountScreenContent(
                editInformationClicked = {
                    navigator.navigate(
                        EditUserInformationScreenDestination()
                    )
                }
            )
        }
    )
}

@Composable
fun AccountScreenContent(
    editInformationClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserInformationHeaderCard(
                editInformationClicked = editInformationClicked
            )

            SupportSectionCard()

            Spacer(modifier = Modifier.height(36.dp))

            Text(text = "Version 1.0.0")
        }
    }
}

@Composable
fun UserInformationHeaderCard(
    editInformationClicked: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.itemsSurfaceColor,
        elevation = 4.dp
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    bottom = 12.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .fillMaxWidth()
        ) {

            LazyColumn(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterStart),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = 4) {
                    UserInformationItem()
                }
            }

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .wrapContentHeight()
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .size(75.dp),
                    color = MaterialTheme.colors.textFieldBorderStrokeColor,
                    shape = CircleShape,
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.ic_male),
                            contentDescription = "Edit Icon",
                            tint = MaterialTheme.colors.buttonContentColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "مرد",
                            color = MaterialTheme.colors.buttonContentColor,
                            fontFamily = iranSans,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            TextButton(
                modifier = Modifier
                    .width(100.dp)
                    .align(Alignment.BottomEnd),
                onClick = editInformationClicked,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.genderItemColor.copy(0.2f)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Icon",
                        tint = MaterialTheme.colors.buttonColor
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ویرایش",
                        color = MaterialTheme.colors.buttonColor,
                        fontFamily = iranSans,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }

        }
    }
}

@Composable
fun UserInformationItem() {
    Column(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Text(
            text = "نام و نام خانوادگی",
            color = MaterialTheme.colors.textColor,
            fontFamily = iranSans,
            fontWeight = FontWeight.Normal,
            fontSize = 9.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "محمد جواد عربی",
            color = MaterialTheme.colors.textColor,
            fontFamily = iranSans,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}


@Composable
fun SupportSectionCard() {
    Card(
        modifier = Modifier
            .padding(
                top = 20.dp,
                bottom = 20.dp,
                end = 12.dp,
                start = 12.dp
            )
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.itemsSurfaceColor,
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                all = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(count = 8) {
                SupportSectionItem(
                    onItemClicked = {}
                )
            }
        }
    }
}

@Composable
fun SupportSectionItem(
    onItemClicked: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClicked.invoke()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Support,
                contentDescription = "Support Icon",
                tint = MaterialTheme.colors.iconTintColor
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "تماس با پشتیبانی",
                color = MaterialTheme.colors.textColor,
                fontFamily = iranSans,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
        }

        Surface(
            modifier = Modifier
                .padding(start = 36.dp, end = 36.dp, top = 10.dp)
                .height(1.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colors.textColor.copy(0.05f)
        ) {}
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileInformationPreview() {
    AccountScreenContent(
        editInformationClicked = {}
    )
}
