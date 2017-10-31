package com.zzapp.lighttranslator.presenter

import com.zzapp.lighttranslator.MainActivity
import com.zzapp.lighttranslator.model.MainModel

/**
 * Project LightTranslator
 * Date 2017-10-28
 *
 * @author zzzz
 */

class MainPresenter(view: MainActivity) : BasePresenter {

    private val fromsurl = arrayOf("auto", "zh","en","yue","wyw",
            "jp","kor","fra","spa","th",
            "ara","ru","pt","de","it",
            "el","nl","pl","bul","est",
            "dan","fin","cs","rom","slo",
            "swe","hu","cht","vie")
    private val tosurl = fromsurl.copyOfRange(1,fromsurl.size)

    private var view: MainActivity = view
    private var model: MainModel = MainModel()

    override fun translate(query: String, from: Int, to: Int) {
        view.setResult(model.getAnswer(query,fromsurl[from],tosurl[to]))
    }
}