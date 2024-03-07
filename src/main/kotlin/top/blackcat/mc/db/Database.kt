package top.blackcat.mc.db

import top.blackcat.mc.enums.RedisKey
import taboolib.module.database.Table
import top.blackcat.mc.helper.infoMessageAsLang
import javax.sql.DataSource

interface Database {

    val playerTable: Table<*, *>

    /****
     * 数据库 数据源
     */
    val dataSource: DataSource

    companion object {
        private val INSTANCEOF by lazy {
            infoMessageAsLang("database-load-start")
            val database = when (DatabaseType.INSTANCE) {
                DatabaseType.SQLITE -> DatabaseSQLite()
                DatabaseType.MYSQL -> DatabaseMySQL()
            }
            infoMessageAsLang("database-load-end")
            database
        }
        private val dataSource = INSTANCEOF.dataSource
        private val playerTable = INSTANCEOF.playerTable





    }
}

