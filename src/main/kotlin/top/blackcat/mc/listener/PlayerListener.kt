package top.blackcat.mc.listener

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import top.blackcat.mc.db.DatabaseRedis
import top.blackcat.mc.enums.RedisKey
import top.blackcat.mc.service.ChannelService

/**
 * Player listener
 * 玩家时间监听器
 *
 */
object PlayerListener {

    /****
     *  加入游戏监听
     */
    @SubscribeEvent
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val player = event.player
        // 保存玩家数据到 Redis
        val uid = player.uniqueId.toString()
        val username = player.name
        DatabaseRedis.hset(RedisKey.PLAYER.key, uid, username)
        ChannelService.subMsg("${RedisKey.PLAYER_CHANNEL.key}:${uid}") {
            player.sendMessage(message)
        }
    }


    /****
     *  退出游戏监听
     */
    @SubscribeEvent
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val player = event.player
        // 从 Redis 中移除玩家数据
        val uid = player.uniqueId.toString()
        DatabaseRedis.hdel(RedisKey.PLAYER.key, uid)
    }



}
