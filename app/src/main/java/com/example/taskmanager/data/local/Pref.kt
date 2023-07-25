package com.example.taskmanager.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(private val context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME,MODE_PRIVATE)


    fun isOnBoardingShowed():Boolean{
        return pref.getBoolean(SHOWED_KEY,false)
    }

    fun onBoardingShowed(){
        pref.edit().putBoolean(SHOWED_KEY,true).apply()
    }

    fun saveName(name:String){
        pref.edit().putString(NAME_KEY,name).apply()
    }

    fun getName(): String? {
        return pref.getString(NAME_KEY,"")
    }

    fun saveImage(url:String){
        pref.edit().putString(AVATAR_KEY,url).apply()
    }

    fun getImage():String?{
        return pref.getString(AVATAR_KEY,"")
    }


    companion object{
        const val PREF_NAME = "pref.name"
        const val SHOWED_KEY = "seen.key"
        const val NAME_KEY = "name.key"
        const val AVATAR_KEY = "avatar.key"
    }
}