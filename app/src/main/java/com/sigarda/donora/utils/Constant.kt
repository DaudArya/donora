package com.sigarda.donora.utils

object Constant {

    val NO_OF_DAYS: Int = 7

    // Days each month has, starting from January
    val mDays = arrayOf(
        31, 28, 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31
    )

    // Long name of the months
    val mMonths = arrayOf(
        "January", "February", "March", "April",
        "May", "June", "July", "August", "September",
        "October", "November", "December"
    )

    // Short days of week names
    val mDaysOfWeek = arrayOf(
        "SUN", "MON", "TUE",
        "WED", "THU", "FRI", "SAT"
    )

    // Week to month mapping, It means weekToMonth[n]'th week (n+1)th month has come.
    val weekToMonth = arrayOf(
        4, 8, 13, 17, 22, 26, 30, 35, 39, 43, 48, 52
    )

    val CREATE_EVENT_DIALOG_MODE = "create"
    val EDIT_EVENT_DIALOG_MODE = "edit"
    val RC_SIGN_IN = 100

    const val COLLECTION_PATH_USER = "users"
    const val COLLECTION_PATH_BUDGET = "budgets"

    const val ID = "id"
    const val E_MAIL = "email"
    const val NICKNAME = "nickname"
    const val PASSWORD = "password"

    const val AMOUNT = "amount"
    const val TITLE = "title"
    const val IS_INCOME = "isIncome"
    const val TAG = "tag"
    const val TYPE = "transactionType"
    const val DATE = "date"
    const val USER_ID = "userId"

    const val CURRENT_DATE_FORMAT = "yyyy/MM/dd"
    const val TAG_DATE_PICKER = "Tag_Date_Picker"
}