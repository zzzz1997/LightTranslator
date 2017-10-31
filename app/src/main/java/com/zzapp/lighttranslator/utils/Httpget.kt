package com.zzapp.lighttranslator.utils

import java.io.BufferedReader
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Project LightTranslator
 * Date 2017-10-29
 *
 * @author zzzz
 */

class Httpget {

    var sendUrl: String = ""

    operator fun get(host: String, params: Map<String, String>): String {
        answer = ""
        val singleThreadPool = ThreadPoolExecutor(1, 1, 0L, TimeUnit.MICROSECONDS, ArrayBlockingQueue(10), ThreadFactory { runnable ->
            val thread = Thread(runnable)
            thread.name = "thread_translate"
            thread
        })
        singleThreadPool.execute {
            try {
                // 设置SSLContext
                val sslcontext = SSLContext.getInstance("TLS")
                sslcontext.init(null, arrayOf<TrustManager>(myX509TrustManager), null)

                sendUrl = getUrlWithQueryString(host, params)

                // System.out.println("URL:" + sendUrl);

                val uri = URL(sendUrl) // 创建URL对象
                val conn = uri.openConnection() as HttpURLConnection
                if (conn is HttpsURLConnection) {
                    conn.sslSocketFactory = sslcontext.socketFactory
                }

                conn.connectTimeout = SOCKET_TIMEOUT // 设置相应超时
                conn.requestMethod = GET
                val statusCode = conn.responseCode
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    println("Http错误码：" + statusCode)
                }

                // 读取服务器的数据
                val `is` = conn.inputStream
                val br = BufferedReader(InputStreamReader(`is`))
                val builder = StringBuilder()
                var line: String? = br.readLine()
                while (line != null) {
                    builder.append(line)
                    line = br.readLine()
                }

                val text = builder.toString()

                close(br) // 关闭数据流
                close(`is`) // 关闭数据流
                conn.disconnect() // 断开连接

                answer = text
            } catch (e: Exception) {
                answer = e.toString()
            }
        }

        try {
            while (answer.length == 0) {
                Thread.sleep(100)
            }
        } catch (e: InterruptedException) {
        }

        return answer
    }

    companion object {
        protected val SOCKET_TIMEOUT = 10000 // 10S
        protected val GET = "GET"

        private var answer = ""

        private val myX509TrustManager = object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<X509Certificate>? {
                return null
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }
        }

        fun getUrlWithQueryString(url: String, params: Map<String, String>?): String {
            if (params == null) {
                return url
            }

            val builder = StringBuilder(url)
            if (url.contains("?")) {
                builder.append("&")
            } else {
                builder.append("?")
            }

            /*builder.append("q=" + params["q"])
            builder.append("&from=" + params["from"])
            builder.append("&to=" + params["to"])
            builder.append("&appid=" + params["appid"])
            builder.append("&salt=" + params["salt"])
            builder.append("&sign=" + params["sign"])*/

            var i = 0
            for (key in params.keys) {
                val value = params[key] ?: // 过滤空的key
                        continue

                if (i != 0) {
                    builder.append('&')
                }

                builder.append(key)
                builder.append('=')
                builder.append(encode(value))

                i++
            }

            return builder.toString()
        }

        protected fun close(closeable: Closeable?) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        /**
         * 对输入的字符串进行URL编码, 即转换为%20这种形式
         *
         * @param input 原文
         * @return URL编码. 如果编码失败, 则返回原文
         */
        fun encode(input: String?): String {
            if (input == null) {
                return ""
            }

            try {
                return URLEncoder.encode(input, "utf-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return input
        }
    }
}

