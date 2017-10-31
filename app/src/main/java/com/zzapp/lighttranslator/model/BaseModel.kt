package com.zzapp.lighttranslator.model

/**
 * Project LightTranslator
 * Date 2017-10-28
 *
 * @author zzzz
 */

interface BaseModel {
    fun getAnswer(query: String,from: String,to: String): String
}