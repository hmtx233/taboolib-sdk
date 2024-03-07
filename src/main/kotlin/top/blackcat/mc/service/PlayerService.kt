package top.blackcat.mc.service

import top.blackcat.mc.db.DatabaseRedis
import top.blackcat.mc.enums.RedisKey
import org.bukkit.Bukkit
import taboolib.common.platform.function.console

/***
 * 玩家服务
 */
object PlayerService {

    /***
     * @param uid 玩家 uid
     * 跨服(CrossServer:使用当前服务器插件的不同服务器)
     * 判断用户 是否在线
     */
    fun isOnlineByUidCs(uid: String): Boolean {
        val player = DatabaseRedis.hget(RedisKey.PLAYER.key, uid)
        return player != null
    }


    /***
     * @param name 玩家用户名
     * 跨服(CrossServer:使用当前服务器插件的不同服务器)
     * 判断用户 是否在线
     */
    fun isOnlineByNameCs(name: String): Boolean {
        val player = DatabaseRedis.hget(RedisKey.PLAYER.key, name)
        return player != null
    }


    /***
     * @param uid 玩家 uid
     * 根据玩家名 判断用户 当前服是否在线
     */
    fun isOnlineByUid(uid: String): Boolean {
        val player = Bukkit.getPlayer(uid)
        return player != null && player.isOnline
    }


    /***
     * @param name 玩家用户名
     * 根据玩家名 判断用户 当前服是否在线
     */
    fun isOnlineByName(name: String): Boolean {
        val player = Bukkit.getPlayer(name)
        return player != null && player.isOnline
    }


    /***
     * @param name 玩家用户名
     * 根据玩家名 判断用户 当前服是否存在
     */
    fun isPlayerExists(name: String): Boolean {
        val player = Bukkit.getPlayerExact(name)
        return player != null
    }


    /**
     * 跨服获取 uid
     * 通过用户name从Redis中获取用户uid
     * @param name 用户name
     * @return uid，如果未找到则返回"-1"
     */
    fun getUidByNameCs(name: String): String {
        val uid = DatabaseRedis.hgetByValue(RedisKey.PLAYER.key, name)
        return uid ?: "-1"
    }

    /**
     * 本服务器 获取 uid
     * 通过用户name 获取用户uid
     * @param name 用户name
     * @return uid，如果未找到则返回"-1"
     */
    fun getUidByName(name: String): String {
        val player = Bukkit.getPlayerExact(name)
        if (player != null) {
            return player.uniqueId.toString()
        }
        return "-1"
    }

    fun getAllPlayers(): Map<String, String>? {
        val result = DatabaseRedis.hgetAll(RedisKey.PLAYER.key)
        if (result != null) {
            return result.chunked(2).associate { it[0] to it[1] }
        }
        return null
    }

    /***
     * 移除当前服务器的在线玩家
     */
    fun removePlayer() {
        val onlinePlayers: List<String> = Bukkit.getOnlinePlayers().map { it.uniqueId.toString() }
        onlinePlayers.forEach {
            console().sendMessage(it)
        }
        onlinePlayers.forEach { DatabaseRedis.hdel(RedisKey.PLAYER.key, it) }
    }

    /***
     * 移除所有服务器 在线玩家
     */
    fun removeAllPlayers() {
        DatabaseRedis.hdelAll(RedisKey.PLAYER.key)
    }
}


