package top.blackcat.mc.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.database.HostSQL
import taboolib.module.database.getHost

/**
 * Database config
 * 数据库配置
 */
object DatabaseConfig {

    @Config("database.yml")
    lateinit var config: Configuration
        private set


    /**
     * db配置
     */
    val host: HostSQL by lazy { config.getHost("sql") }

    /**
     * Table prefix
     * 表前缀
     */
    val tablePrefix: String by lazy { config.getString("sql.table-prefix") ?: "mc" }
}
