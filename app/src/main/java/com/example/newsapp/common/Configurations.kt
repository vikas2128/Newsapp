package com.example.newsapp.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object Configurations {

    const val NETWORK_TIMEOUT = 30000L
    const val CACHE_TIMEOUT = 2000L
    const val NETWORK_ERROR = "Network error"
    const val NETWORK_ERROR_TIMEOUT = "Network timeout"
    const val CACHE_ERROR_TIMEOUT = "Cache timeout"
    const val UNKNOWN_ERROR = "Unknown error"
    const val GENERIC_AUTH_ERROR = "Error"
    const val PAGE_SIZE = 20


    /*Accepted codes*/
    const val OK = 200
    const val CREATED = 201
    const val ACCEPTED = 202
    const val FOUND = 302

    /*Unaccepted codes*/
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val METHOD_NOT_ALLOWED = 405
    const val NOT_ACCEPTABLE = 406
    const val NOT_MODIFIED = 304
    const val PARTIAL_INSERTED = 191

    const val USER_PREFERENCES_NAME = "user_preferences"
    const val LOCATION_INTERVAL = 1L
    const val FORM_TIME_STAMP_FORMAT = "dd/MM/yyyy"
    const val FORM_TIME_STAMP_FORMAT_SUBMIT = "yyyy-MM-dd"
    fun printDifference(startDate: Date, endDate: Date): String {
        //milliseconds
        var different = endDate.time - startDate.time
        println("startDate : $startDate")
        println("endDate : $endDate")
        println("different : $different")
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = different / daysInMilli
        different = different % daysInMilli
        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli
        val elapsedSeconds = different / secondsInMilli
        System.out.printf(
            "%d days, %d hours, %d minutes, %d seconds%n",
            elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds
        )

        return if (elapsedDays < 1 && elapsedHours < 1 && elapsedMinutes < 1) {
            "now";
        } else if (elapsedDays < 1 && elapsedHours < 1) {
            "$elapsedMinutes m";
        } else if (elapsedDays < 1) {
            "$elapsedHours h";
        } else {
            "$elapsedDays d";
        }
    }


    val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val serverFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val displayFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    val displayDateTimeFormat = SimpleDateFormat("dd-MMM-yyyy, hh:mm aa", Locale.getDefault())
    val displayTimeFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
    fun parseDate(dateParam: String): String {
        var date = dateParam.replace("T", " ")
        try {
            return displayDateTimeFormat.format(serverFormat.parse(date))
        } catch (e: Exception) {

        }

        try {
            return displayFormat.format(serverFormat2.parse(date))
        } catch (e: Exception) {

        }
        throw java.lang.Exception("")
    }

    fun getDateFormat(dateParam: String): Date {
        var date = dateParam.replace("T", " ")
        try {
            return serverFormat.parse(date)!!
        } catch (e: Exception) {

        }

        try {
            return serverFormat2.parse(date)!!
        } catch (e: Exception) {

        }
        throw java.lang.Exception("")
    }


    public enum class HomeModules {
        ARTICLE, INTERVIEW, CIO_SPEAK, EMERGING_PRODUCTS
    }


    fun formatDate(strDate: String): String {
        val date = getDateFormat(strDate)
        val currentDate = Date()
        val diffInMilliseconds = currentDate.time - date.time
        val diffInDays = (diffInMilliseconds / (24 * 60 * 60 * 1000)).toInt()
        val diffInWeeks = diffInDays / 7
        val diffInMonths = calculateMonthsDifference(currentDate, date)
        val diffInYears = calculateYearsDifference(currentDate, date)

        return when {
            diffInDays == 0 -> "today"
            diffInDays == 1 -> "1 day ago"
            diffInDays in 2..6 -> "$diffInDays days ago"
            diffInDays in 7..13 -> "1 week ago"
            diffInWeeks > 1 -> "$diffInWeeks weeks ago"
            diffInMonths == 1 -> "1 month ago"
            diffInMonths > 1 -> "$diffInMonths months ago"
            diffInYears == 1 -> "1 year ago"
            diffInYears > 1 -> "$diffInYears years ago"
            else -> SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date)
        }
    }

    fun calculateMonthsDifference(date1: Date, date2: Date): Int {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        calendar1.time = date1
        calendar2.time = date2

        var months = 0
        var yearDiff = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR)
        var monthDiff = calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH)

        months = yearDiff * 12 + monthDiff
        return Math.abs(months)
    }

    fun calculateYearsDifference(date1: Date, date2: Date): Int {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        calendar1.time = date1
        calendar2.time = date2

        return Math.abs(calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR))
    }


}