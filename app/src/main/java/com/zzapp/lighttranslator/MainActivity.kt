package com.zzapp.lighttranslator

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.zzapp.lighttranslator.presenter.MainPresenter
import com.zzapp.lighttranslator.view.BaseView
import kotlinx.android.synthetic.main.activity_main. *

/**
 * Project LightTranslator
 * Date 2017-10-28
 *
 * @author zzzz
 */

class MainActivity : Activity(), BaseView {

    private val froms = arrayOf("自动检测", "中文","英语","粤语","文言文",
            "日语","韩语","法语","西班牙语","泰语",
            "阿拉伯语","俄语","葡萄牙语","德语","意大利语",
            "希腊语","荷兰语","波兰语","保加利亚语","爱沙尼亚语",
            "丹麦语","芬兰语","捷克语","罗马尼亚语","斯洛文尼亚语",
            "瑞典语","匈牙利语","繁体中文","越南语")
    private val tos = froms.copyOfRange(1,froms.size)

    private val COPY = "结果已复制到剪切板"

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        presenter = MainPresenter(this)
        from.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,froms)
        to.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,tos)
        translate.setOnClickListener{
            if (!query.text.isEmpty()) {
                presenter.translate(getQuery(),from.selectedItemPosition,to.selectedItemPosition)
            }
        }
        clear.setOnClickListener { query.text = null }
        copy.setOnClickListener{
            if (!result.text.isEmpty()) {
                var cb = this.getSystemService(android.app.Service.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText(null, result.text.toString())
                cb.primaryClip = clipData
                toast(COPY)
            }
        }
    }

    override fun getQuery(): String {
        return query.text.toString()
    }

    override fun setResult(answer: String) {
        result.text = answer
        result.visibility = View.VISIBLE
        copy.visibility = View.VISIBLE
    }

    override fun toast(text: String) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }
}
