package com.ewida.rickmorti.utils.shared_pref_utils

import android.content.Context
import android.util.Log
import com.ewida.rickmorti.common.Common.TAG
import java.lang.ref.WeakReference

object PrefUtils {

    fun saveToPref(context: Context, key:String, value:Any){
        val contextWeakReference=WeakReference(context)
        if(contextWeakReference.get()!=null){
            val prefs=androidx.preference.PreferenceManager.getDefaultSharedPreferences(
                contextWeakReference.get()!!
            )
            val editor=prefs.edit()
            when(value){
                is Int->{
                    editor.putInt(key,value.toInt())
                }
                is Float->{
                    editor.putFloat(key,value.toFloat())
                }
                is String->{
                    editor.putString(key,value.toString())
                }
                is Boolean->{
                    editor.putBoolean(key,value.toString().toBoolean())
                }
                is Long->{
                    editor.putLong(key,value.toLong())
                }
            }
            editor.apply()
        }
    }

    fun getFromPref(context: Context,key: String,defValue:Any):Any{
        val contextWeakReference=WeakReference(context)
        if(contextWeakReference.get()!=null){
            val prefs=androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            try {
                when(defValue){
                    is Int->{
                        return prefs.getInt(key,defValue.toInt())
                    }
                    is Float->{
                        return prefs.getFloat(key,defValue.toFloat())
                    }
                    is Long->{
                        return prefs.getLong(key,defValue.toLong())
                    }
                    is String->{
                        return prefs.getString(key,defValue.toString()).toString()
                    }
                    is Boolean->{
                        return prefs.getBoolean(key,defValue.toString().toBoolean())
                    }
                }
            }catch (e:Exception){
                Log.d(TAG, "getFromPref: ${e.message}")
                return defValue
            }
        }
        return defValue
    }
}