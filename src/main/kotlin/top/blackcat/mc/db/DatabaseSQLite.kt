package top.blackcat.mc.db

import top.blackcat.mc.config.DatabaseConfig.tablePrefix
import top.blackcat.mc.helper.infoMessageAsLang
import taboolib.module.database.ColumnTypeSQLite
import taboolib.module.database.Table
import taboolib.module.database.getHost
import taboolib.platform.util.bukkitPlugin
import java.io.File

class DatabaseSQLite : Database {

    private val host = File(bukkitPlugin.dataFolder, "data.db").getHost()


    override val playerTable: Table<*, *> = Table("${tablePrefix}_player", host) {
        //  玩家uid
        add("uid") {
            type(ColumnTypeSQLite.TEXT, 64)
        }
        //  玩家name
        add("name") {
            type(ColumnTypeSQLite.TEXT, 128)
        }
        //  货币类型 主键
        add("player_time") {
            type(ColumnTypeSQLite.TEXT, 64)
        }
        //  最后登录时间
        add("last_login_time") {
            type(ColumnTypeSQLite.TEXT, 64)
        }
    }

    override val dataSource = host.createDataSource()

    init {
        infoMessageAsLang("database-load-start", "SQLite")
        playerTable.workspace(dataSource) { createTable() }.run()
        infoMessageAsLang("database-load-end", "SQLite")
    }
}