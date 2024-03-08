package top.blackcat.mc.cache


import top.blackcat.mc.config.MainConfig.refreshDelay

import com.github.benmanes.caffeine.cache.Caffeine
import top.blackcat.mc.model.vo.OnlinePlayerVO
import top.blackcat.mc.service.PlayerService


import java.util.concurrent.TimeUnit





//缓存玩家
internal val cacheOnlinePlayer = Caffeine.newBuilder()
    .maximumSize(refreshDelay.toLong())
    .refreshAfterWrite(5, TimeUnit.MINUTES)
    .build<String, List<OnlinePlayerVO>?> {
        PlayerService.getAllPlayers()
    }
