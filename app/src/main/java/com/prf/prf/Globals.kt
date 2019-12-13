package com.prf.prf

import android.graphics.Bitmap

class Globals {

    companion object Chosen{
       var globalList:ArrayList<Users>? = null

        fun returnGlobals(): ArrayList<Users> {

            return globalList!!

        }
    }
}