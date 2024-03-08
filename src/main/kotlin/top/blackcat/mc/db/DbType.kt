package top.blackcat.mc.db

import top.blackcat.mc.config.DatabaseConfig

/**
 * Database type
 * 数据库类型
 *
 * @constructor Create empty Database type
 */
enum class DbType {
    /**
     * SQLite
     * SQLite - 本地
     *
     * @constructor Create empty SQLite
     */
    SQLITE,

    /**
     * MySQL
     * MySQL - 远程
     *
     * @constructor Create empty MySQL
     */
    MYSQL;

    companion object {
        val INSTANCE: DbType by lazy {
            try {
                valueOf(DatabaseConfig.config.getString("sql.type")!!.uppercase())
            } catch (ignore: Exception) {
                SQLITE
            }
        }
    }
}
