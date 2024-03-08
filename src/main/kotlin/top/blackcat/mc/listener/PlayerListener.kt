package top.blackcat.mc.listener

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import top.blackcat.mc.db.Redis
import top.blackcat.mc.enums.RedisKey
import top.blackcat.mc.model.entity.MyPlayer
import top.blackcat.mc.service.ChannelService
import top.blackcat.mc.service.MyPlayerService

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
        val uid = player.uniqueId.toString()
        val name = player.name
        // 在线玩家 入库 Redis
        Redis.hset(RedisKey.PLAYER.key, uid, name)
        // 在线玩家 更新mysql 最后上线时间
        MyPlayerService.save(MyPlayer(uid, name, player.playerTime, System.currentTimeMillis(), 0), true)
        // 玩家订阅频道
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
        val uid = player.uniqueId.toString()
        // 从 Redis 中移除玩家数据
        Redis.hdel(RedisKey.PLAYER.key, uid)
        // 在线玩家 更新mysql 离线时间
        MyPlayerService.save(
            MyPlayer(
                uid,
                player.name,
                player.playerTime,
                System.currentTimeMillis(),
                System.currentTimeMillis()
            ), false
        )
    }


}
