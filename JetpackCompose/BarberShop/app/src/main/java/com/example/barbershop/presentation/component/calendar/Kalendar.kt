package com.example.barbershop.presentation.component.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aminography.primecalendar.common.toPersian
import com.aminography.primecalendar.persian.PersianCalendar
import com.example.barbershop.ui.theme.iranSans
import com.example.barbershop.ui.theme.itemsSurfaceColor
import com.example.barbershop.ui.theme.textColor
import java.util.*

@Composable
fun Kalendar(
    modifier: Modifier = Modifier,
    kalendarViewModel: KalendarViewModel = hiltViewModel(),
    cardRoundedCornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    completeSelectedDate: (String) -> Unit,
    selectedDate: (String) -> Unit,
) {
    KalendarContent(
        modifier = modifier,
        completeSelectedDate = completeSelectedDate,
        cardRoundedCornerShape = cardRoundedCornerShape,
        kalendarViewModel = kalendarViewModel,
        selectedDate = selectedDate
    )
}

@Composable
fun KalendarContent(
    modifier: Modifier,
    cardRoundedCornerShape: RoundedCornerShape,
    kalendarViewModel: KalendarViewModel,
    completeSelectedDate: (String) -> Unit,
    selectedDate: (String) -> Unit,
) {
    val calendar = Calendar.getInstance().toPersian()
    var year by remember { mutableStateOf(calendar.year) }
    var month by remember { mutableStateOf(calendar.month) }
    var today by remember { mutableStateOf(calendar.dayOfMonth) }
    var prevButtonEnableState by remember { mutableStateOf(true) }

    if (year == calendar.year && month == calendar.month) {
        today = calendar.dayOfMonth
        prevButtonEnableState = false
    } else {
        prevButtonEnableState = true
    }

    calendar.set(year = year, month = month, dayOfMonth = 20, 0, 0, 0)

    Card(
        modifier = modifier,
        shape = cardRoundedCornerShape,
        backgroundColor = MaterialTheme.colors.itemsSurfaceColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            KalendarHeader(
                onPrevMonthClicked = {
                    if (month == 0) {
                        month = PersianCalendar.ESFAND
                        year--
                    } else {
                        month--
                        today = -1
                    }
                },
                onNextMonthClicked = {
                    if (month == 11) {
                        month = PersianCalendar.FARVARDIN
                        year++
                    } else {
                        month++
                        today = -1
                    }
                },
                prevButtonEnableState = prevButtonEnableState,
                year = year.toString(),
                month = calendar.monthName
            )

            Spacer(modifier = Modifier.height(8.dp))

            KalendarWeek()

            Spacer(modifier = Modifier.height(8.dp))

            KalendarDays(
                selectedDate = { selectedDate ->
                    calendar.set(
                        year = year,
                        month = month,
                        dayOfMonth = selectedDate.toInt(),
                        0,
                        0,
                        0
                    )
                    completeSelectedDate(calendar.longDateString)
                    selectedDate("$year/$month/$selectedDate")
                },
                startDayOfWeek = kalendarViewModel
                    .getFirstDayOfMonth(
                        month = month,
                        year = year
                    ),
                monthLength = calendar.monthLength,
                today = today.toString()
            )
        }
    }
}


@Preview
@Composable
fun KalendarPreview() {
    KalendarContent(
        completeSelectedDate = {},
        selectedDate = {},
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        cardRoundedCornerShape = RoundedCornerShape(0.dp),
        kalendarViewModel = hiltViewModel()
    )
}

