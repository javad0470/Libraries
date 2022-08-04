package com.rocad.barbershop.utils

import io.ktor.client.statement.*

object HandleHttpResponse {


    fun clientRequestException(httpResponse: HttpResponse): String {
        return when (httpResponse.status.value) {
            400 -> "سرور توانایی پردازش درخواست شما را ندارد"
            403 -> "درخواست مورد نظر شما ممونو میباشد"
            404 -> "صفحه مورد نظر یافت نشد"
            405 -> "متد مورد نظر شما غیرمجاز میباشد"
            408 -> "زمان درخواست طولانی شد"
            429 -> "درخواست های پیاپی غیر مجاز"
            else -> ""
        }
    }

}