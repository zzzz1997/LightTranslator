package com.zzapp.lighttranslator.view

/**
 * Project LightTranslator
 * Date 2017-10-28
 *
 * @author zzzz
 */

interface BaseView{
    fun getQuery(): String

    fun setResult(answer: String)

    fun toast(text: String)
}