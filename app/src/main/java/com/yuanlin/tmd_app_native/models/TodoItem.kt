package com.yuanlin.tmd_app_native.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter


enum class TodoStatus(val status: String) {
    EXPIRED("EXPIRED"),
    ONE_DAY_LEFT("ONE_DAY_LEFT"),
    ONE_WEEK_LEFT("ONE_WEEK_LEFT"),
    MORE_THAN_ONE_WEEK("MORE_THAN_ONE_WEEK"),
    ERROR("ERROR"),
}


class TodoItem {
    @SerializedName("id")
    val id: String = ""

    @SerializedName("title")
    val title: String = ""

    @SerializedName("ddl")
    private val deadline: String = ""

    @SerializedName("status")
    private val status: String = ""

    fun deadline(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        return LocalDate.parse(deadline, formatter)
    }

    fun status(): TodoStatus {
        return when (status) {
            "EXPIRED" -> TodoStatus.EXPIRED
            "ONE_DAY_LEFT" -> TodoStatus.ONE_DAY_LEFT
            "ONE_WEEK_LEFT" -> TodoStatus.ONE_WEEK_LEFT
            "MORE_THAN_ONE_WEEK" -> TodoStatus.MORE_THAN_ONE_WEEK
            else -> TodoStatus.ERROR
        }
    }
}