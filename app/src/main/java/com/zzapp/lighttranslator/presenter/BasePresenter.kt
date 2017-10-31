package com.zzapp.lighttranslator.presenter

/**
 * Project LightTranslator
 * Date 2017-10-28
 *
 * @author zzzz
 */

interface BasePresenter {
    fun translate(query: String,from: Int,to: Int)
}