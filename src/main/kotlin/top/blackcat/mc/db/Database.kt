package top.blackcat.mc.db

import taboolib.module.database.Table
import top.blackcat.mc.helper.infoMessageAsLang
import top.blackcat.mc.model.entity.MyPlayer
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
            val database = when (DbType.INSTANCE) {
                DbType.SQLITE -> SQLite()
                DbType.MYSQL -> MySQL()
            }
            infoMessageAsLang("database-load-end")
            database
        }

        private val dataSource = INSTANCEOF.dataSource
        private val playerDb = INSTANCEOF.playerTable

        /***
         * 更新玩家登录 或者离开时间
         */
        fun saveMyPlayer(myPlayer: MyPlayer, login: Boolean): Boolean {
            val player = getMyPlayer(myPlayer.uid)
            val res: Int = if (player.isNotEmpty()) {
                // 玩家存在，执行更新操作
                playerDb.update(dataSource) {
                    if (login) {
                        set("last_login_time", myPlayer.lastLoginTime)
                        set("player_time", myPlayer.playerTime)
                    } else {
                        set("leave_time", myPlayer.leaveTime)
                    }
                    where { ("uid" eq myPlayer.uid) }
                }
            } else {
                // 玩家不存在，执行插入操作
                playerDb.insert(dataSource, "uid", "name", "player_time", "last_login_time", "leave_time") {
                    value(myPlayer.uid, myPlayer.name, myPlayer.playerTime, myPlayer.lastLoginTime, myPlayer.leaveTime)
                }
            }
            return (res > 0)
        }

        /***
         * 获取玩家
         */
        fun getMyPlayer(uid: String): List<MyPlayer> {
            return playerDb.select(dataSource) {
                where { ("uid" eq uid) }
            }.map {
                MyPlayer(
                    getString("uid"),
                    getString("name"),
                    getLong("player_time"),
                    getLong("last_login_time"),
                    getLong("leave_time")
                )
            }
        }
    }
}

