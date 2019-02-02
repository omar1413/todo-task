package com.example.omar.taskmanager.data.shared_pref

import android.content.Context
import android.content.SharedPreferences
import com.example.omar.taskmanager.dagger.qualifier.ApplicationContext
import com.example.omar.taskmanager.dagger.scope.AppScope
import javax.inject.Inject


class AppSharedPref @Inject constructor(var sharedPreferences: SharedPreferences){


    companion object {
        private val USER_ID_KEY = "USER_ID_KEY"
        private val TASK_ID_KEY = "TASK_ID_KEY"
    }

    private fun setString(key: String, value: String){
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun getString(key: String, def: String): String{
        return sharedPreferences.getString(key,def)
    }

    private fun setInt(key: String, value: Int){
        sharedPreferences.edit().putInt(key, value).apply()
    }

    private fun getInt(key: String, def: Int): Int{
        return sharedPreferences.getInt(key,def)
    }


    fun setCurrentUser(userId: Int){
        setInt(USER_ID_KEY, userId)
    }

    fun getCurrentUser(): Int?{
        val v = getInt(USER_ID_KEY, -1)
        if (v == -1) return null
        return v
    }

    fun saveCurrentTask(id: Int) {
        setInt(TASK_ID_KEY, id)
    }

    fun getCurrentTask(): Int?{
        val v = getInt(TASK_ID_KEY, -1)
        if(v == -1) return null
        return v
    }

}