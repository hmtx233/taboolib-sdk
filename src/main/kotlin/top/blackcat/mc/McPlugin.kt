package top.blackcat.mc

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info

object McPlugin : Plugin() {

    override fun onEnable() {
        info("Successfully running ExamplePlugin!")
    }
}