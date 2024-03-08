package top.blackcat.mc


import taboolib.common.platform.Plugin
import top.blackcat.mc.helper.infoMessageAsLang
import top.blackcat.mc.service.PlayerService

object McPlugin : Plugin() {


    override fun onEnable() {
        infoMessageAsLang("enabling")
        infoMessageAsLang("enabled")
    }


    override fun onLoad() {
        super.onLoad()
        infoMessageAsLang("on-load")
    }

    override fun onActive() {
        super.onActive()
        infoMessageAsLang("on-active")
    }



    /**
     * 关闭
     *
     */
    override fun onDisable() {
        // 移除当前在线玩家
        PlayerService.removePlayer()
        infoMessageAsLang("disabling")
        infoMessageAsLang("disabled")
    }
}