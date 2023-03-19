package com.ewida.rickmorti.utils

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.visible(){this.visibility=View.VISIBLE}
fun View.inVisible(){this.visibility=View.INVISIBLE}
fun View.gone(){this.visibility=View.GONE}
fun Activity.toast(msg:String,duration:Int=Toast.LENGTH_SHORT){Toast.makeText(this,msg,duration).show()}
fun Fragment.toast(msg:String,duration:Int=Toast.LENGTH_SHORT){Toast.makeText(requireContext(),msg,duration).show()}