package top.blackcat.mc.db

import top.blackcat.mc.config.DatabaseConfig.host
import top.blackcat.mc.config.DatabaseConfig.tablePrefix
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table
import top.blackcat.mc.helper.infoMessageAsLang


/****
 * mysql 数据库适配
 */
class MySQL : Database {


    override val playerTable: Table<*, *> = Table("${tablePrefix}_player", host) {
        //   用户uid
        add("uid") {
            type(ColumnTypeSQL.VARCHAR, 64) {
                options(ColumnOptionSQL.KEY)
            }
        }
        //   用户名称
        add("name") {
            type(ColumnTypeSQL.VARCHAR, 128) {
                options(ColumnOptionSQL.KEY)
            }
        }
        //
        add("player_time") {
            type(ColumnTypeSQL.VARCHAR, 64) {
                options(ColumnOptionSQL.KEY)
            }
        }
        // 登录时间
        add("last_login_time") {
            type(ColumnTypeSQL.BIGINT, 64)
        }
        // 离开时间
        add("leave_time") {
            type(ColumnTypeSQL.BIGINT, 64)
        }
    }

    override val dataSource = host.createDataSource()

    init {
        infoMessageAsLang("database-load-start", "MySql")
        playerTable.workspace(dataSource) { createTable() }.run()
        infoMessageAsLang("database-load-end", "MySql")
    }


}
