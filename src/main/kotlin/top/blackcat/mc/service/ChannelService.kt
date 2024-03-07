package top.blackcat.mc.service

import top.blackcat.mc.db.DatabaseRedis
import taboolib.expansion.RedisMessage

/***
 * 消息订阅服务
 */
object ChannelService {

    fun pubMsg(channel: String, msg: String) {
        DatabaseRedis.publish(
            channel,
            msg
        )
    }

    fun subMsg(channel: String, messageHandler: RedisMessage.() -> Unit) {
        DatabaseRedis.redisConnection.subscribe(channel, patternMode = false, func = messageHandler)
    }

}



