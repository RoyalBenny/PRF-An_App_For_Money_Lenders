package com.prf.prf



class Users:Comparable<Users> {


    var name:String=""
    var code:String=""
    var phone:String=""
    var amount:Int=0
    var current:Int=0
    var expected:Int=0
    var time:Int=0

    constructor(name:String,code:String,phone:String,amount:Int,current:Int,expected:Int,time:Int){

    }
    constructor(){

    }
    override fun compareTo(other: Users): Int {
        return this.name.compareTo(other.name)

    }

}