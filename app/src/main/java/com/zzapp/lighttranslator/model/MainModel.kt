package com.zzapp.lighttranslator.model

import org.json.JSONObject

/**
 * Project LightTranslator
 * Date 2017-10-28
 *
 * @author zzzz
 */

class MainModel : BaseModel {

    val APP_ID = "20171028000091337"
    val SECURITY_KEY = "LJUlcL9SCtryiDQaaNWq"

    override fun getAnswer(query: String,from: String,to: String): String {
        var transApi = TransApi(APP_ID,SECURITY_KEY)

        var answer = transApi.getTransResult(query, from, to)!!

        try {
            var json = JSONObject(answer)
            var trans_result = json.getJSONArray("trans_result")
            var result: JSONObject = trans_result.getJSONObject(0)
            answer = result.getString("dst")
        }catch (e: Exception){
        }

        return answer
    }
}