package top.blackcat.mc.db



import taboolib.expansion.AlkaidRedis
import taboolib.expansion.RedisMessage
import taboolib.expansion.SingleRedisConnection
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

/***
 * redis 服务
 */
class Redis {

    companion object {
        @Config("database.yml")
        lateinit var config: Configuration
            private set

        val redisConnection: SingleRedisConnection by lazy {
            AlkaidRedis.createDefault {
                config.getConfigurationSection("redis")?.let { section ->
                    it.host(section.getString("host", "localhost")!!)
                    it.port(section.getInt("port", 6379))
                    it.auth(section.getString("auth"))
                    it.pass(section.getString("pass"))
                    it.connect(section.getInt("connect", 32))
                    it.timeout(section.getInt("timeout", 1000))
                }
            }
        }


        /***
         * 消息订阅
         */
        fun subscribe(channel: String, messageHandler: RedisMessage.() -> Unit) {
            redisConnection.subscribe(channel, patternMode = false, func = messageHandler)
        }

        /***
         * 消息发布
         */
        fun publish(channel: String, data: Any) {
            redisConnection.publish(channel, data)
        }

        /***
         * 设置消息
         */
        fun set(key: String, value: String) {
            redisConnection[key] = value
        }

        fun get(key: String) {
           redisConnection[key].toString()
        }


        // 封装 Redis 的 hset 函数，用于设置哈希表中的字段值
        fun hset(key: String, field: String, value: String): Any? {
            return redisConnection.eval(
                "return redis.call('hset', KEYS[1], ARGV[1], ARGV[2])",
                1,
                listOf(key, field, value)
            )
        }

        // 封装 Redis 的 hdel 函数，用于从哈希表中移除字段
        fun hdel(key: String, field: String): Any? {
            return redisConnection.eval("return redis.call('hdel', KEYS[1], ARGV[1])", 1, listOf(key, field))
        }


        fun hdelAll(key: String): Any? {
            return redisConnection.eval(
                "return redis.call('hdel', KEYS[1], unpack(redis.call('HKEYS', KEYS[1])))",
                1,
                listOf(key)
            )
        }

        fun hget(key: String, field: String): String? {
            return redisConnection.eval(
                "return redis.call('hget', KEYS[1], ARGV[1])",
                1,
                listOf(key, field)
            ) as? String
        }

        fun hgetAll(key: String): List<String>? {
            return redisConnection.eval(
                "return redis.call('hgetall', KEYS[1])",
                1,
                listOf(key)
            ) as? List<String>
        }


        /***
         * 通过值 查询key
         */
        fun hgetByValue(key: String, value: String): String? {
            val result = hgetAll(key)
            if (result != null) {
                return result.chunked(2).associate { it[1] to it[0] }[value]
            }
            return null
        }

        // 封装 Redis 的 lpush 函数，用于将一个或多个值插入到列表头部
        fun lpush(key: String, vararg values: String): Any? {
            return redisConnection.eval("return redis.call('lpush', KEYS[1], unpack(ARGV))", 1, listOf(key, *values))
        }

        // 封装 Redis 的 lpop 函数，用于移除并返回列表的第一个元素
        fun lpop(key: String): Any? {
            return redisConnection.eval("return redis.call('lpop', KEYS[1])", 1, listOf(key))
        }

        // 封装 Redis 的 sadd 函数，用于向集合添加一个或多个成员
        fun sadd(key: String, vararg members: String): Any? {
            return redisConnection.eval("return redis.call('sadd', KEYS[1], unpack(ARGV))", 1, listOf(key, *members))
        }

        // 封装 Redis 的 srem 函数，用于移除集合中的一个或多个成员
        fun srem(key: String, vararg members: String): Any? {
            return redisConnection.eval("return redis.call('srem', KEYS[1], unpack(ARGV))", 1, listOf(key, *members))
        }

        // 封装 Redis 的 zadd 函数，用于将一个或多个成员元素及其分数值加入到有序集合中
        fun zadd(key: String, vararg scoreValuePairs: Pair<Double, String>): Any? {
            val args = scoreValuePairs.flatMap { listOf(it.first.toString(), it.second) }
            return redisConnection.eval(
                "return redis.call('zadd', KEYS[1], unpack(ARGV))",
                1,
                listOf(key, *args.toTypedArray())
            )
        }

        // 封装 Redis 的 zrem 函数，用于移除有序集合中的一个或多个成员
        fun zrem(key: String, vararg members: String): Any? {
            return redisConnection.eval("return redis.call('zrem', KEYS[1], unpack(ARGV))", 1, listOf(key, *members))
        }

    }
}



