package com.tencent.kuikly.demo.pages.app.lang

import com.tencent.kuikly.core.log.KLog
import com.tencent.kuikly.core.manager.PagerManager
import com.tencent.kuikly.core.module.SharedPreferencesModule
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import com.tencent.kuikly.demo.pages.base.BridgeModule

object LangManager {
    private const val LANG_MANAGER_TAG = "LangManager"
    private const val PREF_LANGUAGE_KEY = "lang"
    const val LANG_CHANGED_EVENT = "langChanged"
    val SUPPORTED_LANGUAGES = mapOf(
        "简体中文" to "zh-Hans",
        "繁體中文" to "zh-Hant",
        "English" to "en"
    )
    val SETTING_HINTS = mapOf(
        "zh-Hans" to "正在设置语言...",
        "zh-Hant" to "正在設置語言...",
        "en" to "Setting..."
    )

    private val defaultLanguage = "en"
    private val defaultResString = SimplifiedChinese
    private var processing = true

    // 当前语言和资源
    private var language: String = defaultLanguage
    private var resString: ResString = defaultResString

    /**
     * 加载资源字符串
     */
    private fun loadResString(lang: String) {
        if (lang.startsWith("zh"))
            resString = if (lang == "zh-Hans") SimplifiedChinese else TraditionalChinese
        else
            loadFromJson(lang)
    }

    /**
     * 从 assets 加载 json 资源
     */
    private fun loadFromJson(lang: String, pageName: String = "common") {
        val jsonPath = "$pageName/lang/$lang.json"
        var resStringJson: JSONObject?
        val bridgeModule = PagerManager.getCurrentPager()
            .getModule<BridgeModule>(BridgeModule.MODULE_NAME)!!

        bridgeModule.readAssetFile(jsonPath) { json ->
            if (json == null || json.optString("error").isNotEmpty()) {
                KLog.e(LANG_MANAGER_TAG, "Failed to read json from assets: $jsonPath")
            } else {
                resStringJson = json.optJSONObject("result")
                resString = resStringJson?.let { ResString.fromJson(it) } ?: defaultResString
            }
        }

        processing = false
    }

    /**
     * 初始化
     */
    fun init() {
        language = PagerManager.getCurrentPager().pageData.params.optString("lang")
        if (language.startsWith("zh"))
            resString = if (language == "zh-Hans") SimplifiedChinese else TraditionalChinese
        else {
            val json = JSONObject(PagerManager.getCurrentPager().pageData.params
                .optString("langJson"))
            resString = ResString.fromJson(json)
        }
    }

    /**
     * 获取当前资源字符串
     */
    fun getCurrentResString(): ResString = resString

    /**
     * 获取当前语言
     */
    fun getCurrentLanguage(): String = language

    /**
     * 切换语言
     */
    fun changeLanguage(lang: String) {
        if (lang !in SUPPORTED_LANGUAGES.values) {
            KLog.e(LANG_MANAGER_TAG, "Unsupported language: $lang")
            return
        }

        val sharedPreferencesModule = PagerManager.getCurrentPager()
            .getModule<SharedPreferencesModule>(SharedPreferencesModule.MODULE_NAME)!!

        try {
            language = lang
            loadResString(lang)
            sharedPreferencesModule.setString(PREF_LANGUAGE_KEY, lang)
            KLog.d(LANG_MANAGER_TAG, "Language changed to: $lang")
        } catch (e: Exception) {
            KLog.e(LANG_MANAGER_TAG, "Failed to change language: ${e.message}")
            // 回退到默认语言
            language = defaultLanguage
            resString = defaultResString
        }
    }
}
