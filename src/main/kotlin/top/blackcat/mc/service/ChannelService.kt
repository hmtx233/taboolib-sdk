package top.blackcat.mc.service

import taboolib.expansion.RedisMessage
import top.blackcat.mc.db.Redis

/***
 * 消息订阅服务
 */
object ChannelService {

    fun pubMsg(channel: String, msg: String) {
        Redis.publish(
            channel,
            msg
        )
    }

    fun subMsg(channel: String, messageHandler: RedisMessage.() -> Unit) {
        Redis.redisConnection.subscribe(channel, patternMode = false, func = messageHandler)
    }
}



