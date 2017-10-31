package com.zzapp.lighttranslator.model

import com.zzapp.lighttranslator.utils.Httpget
import com.zzapp.lighttranslator.utils.MD5

import java.util.HashMap

/**
 * Project LightTranslator
 * Date 2017-10-28
 *
 * @author zzzz
 */

class TransApi(val appid: String,val securityKey: String) {

    fun getTransResult(query: String, from: String, to: String): String? {
        val params = buildParams(query, from, to)

        return Httpget()[TRANS_API_BAIDU, params]
    }

    private fun buildParams(query: String, from: String, to: String): Map<String, String> {
        val params = HashMap<String, String>()
        params.put("q", query)
        params.put("from", from)
        params.put("to", to)

        params.put("appid", appid)

        // 随机数
        val salt = System.currentTimeMillis().toString()
        params.put("salt", salt)

        // 签名
        val src = appid + query + salt + securityKey // 加密前的原文

        params.put("sign", MD5().md5(src))

        return params
    }

    companion object {
        private val TRANS_API_BAIDU = "http://api.fanyi.baidu.com/api/trans/vip/translate"
    }

}

