package top.blackcat.mc.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object MainConfig {

    @Config(value = "config.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    //  刷新排行榜秒数
    val refreshDelay: Int by lazy { config.getInt("refresh-delay") }


}
