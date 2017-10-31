package com.zzapp.lighttranslator.utils

import java.security.MessageDigest

/**
 * Project LightTranslator
 * Date 2017-10-28
 *
 * @author zzzz
 */
class MD5 {
    private val hexDigits = arrayOf( '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' )

    fun md5(input: String): String{
        if(input.isEmpty()){
            return ""
        }
        try{
            var messageDigest: MessageDigest = MessageDigest.getInstance("MD5")
            val inputByteArray = input.toByteArray(charset("utf-8"))
            messageDigest.update(inputByteArray)
            val resultByteArray = messageDigest.digest()
            return byteArrayToHex(resultByteArray)
        }
        catch (e: Exception){
            return ""
        }
    }

    private fun byteArrayToHex(byteArray: ByteArray): String {
        var sb = StringBuffer()
        for (b in byteArray) {
            var i :Int = b.toInt() and 0xff
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                hexString = "0" + hexString
            }
            sb.append(hexString)
        }

        return sb.toString()
    }
}