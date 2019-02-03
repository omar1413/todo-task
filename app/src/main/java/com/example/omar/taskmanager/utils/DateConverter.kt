package com.example.omar.taskmanager.utils

import androidx.room.TypeConverter
import java.util.*

class DateConverter{

        @TypeConverter
        fun toDate(dateLong: Long): Date?{
            if (dateLong == -1L){
                return null
            }
            return Date(dateLong)
        }

        @TypeConverter
        fun fromDate(date: Date?):Long{
            if(date == null){
                return -1
            }
            return date.time
        }

}