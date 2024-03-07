package top.blackcat.mc.enums

/***
 * redis key
 */
enum class RedisKey(val key: String, desc: String) {
    PLAYER_CHANNEL("mc:channel", "广播"),
    PLAYER("mc:player", "玩家"),
}