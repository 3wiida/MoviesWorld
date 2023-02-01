package com.ewida.rickmorti.utils.bundle_utils

import android.os.Bundle
import java.lang.ref.WeakReference

object BundleUtils {

    private val weakBundleReference = WeakReference(Bundle())

    fun getFromBundle(key: String, defValue: Any):Any {
        weakBundleReference.get()?.let {
            return when(defValue){
                is Int-> it.getInt(key,defValue)
                is Long-> it.getLong(key,defValue)
                is Double-> it.getFloat(key,defValue.toFloat())
                is String-> it.getString(key,defValue)
                is Boolean-> it.getBoolean(key,defValue)
                else -> defValue
            }
        }
        return defValue
    }

    fun saveInBundle(key:String,value: Any){
        weakBundleReference.get()?.let {
            when(value){
                is Int->it.putInt(key,value)
                is Long->it.putLong(key,value)
                is Double-> it.putDouble(key,value)
                is String-> it.putString(key,value)
                is Boolean-> it.putBoolean(key,value)
            }
        }
    }
}