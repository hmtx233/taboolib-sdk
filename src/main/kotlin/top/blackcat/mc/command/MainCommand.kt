package top.blackcat.mc.command

import top.blackcat.mc.service.PlayerService
import org.bukkit.command.CommandSender
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.subCommand
import taboolib.module.lang.sendInfo
import taboolib.platform.util.bukkitPlugin
import taboolib.platform.util.sendInfo

@Awake(LifeCycle.ENABLE)
@CommandHeader(
    name = "mc-bc",
    aliases = ["bc", "BlackCat"],
    permission = "BlackCat.command.use",
    permissionDefault = PermissionDefault.TRUE
)
class MainCommand {


    @CommandBody(permission = "BlackCat.command.players", permissionDefault = PermissionDefault.OP)
    val players = subCommand {
        execute<CommandSender> { sender, _, _ ->
            val playerList = PlayerService.getAllPlayers()
            if (playerList != null) {
                if (playerList.isNotEmpty()) {
                    playerList.forEach {
                        sender.sendInfo("player", it.uid, it.name)
                    }
                } else {
                    sender.sendInfo("no-data")
                }
            }
        }
    }


    @CommandBody(permission = "BlackCat.command.reload", permissionDefault = PermissionDefault.OP)
    val reload = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            sender.sendInfo("Reload")
        }
    }

    @CommandBody(permission = "BlackCat.command.version", permissionDefault = PermissionDefault.OP)
    val version = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            sender.sendInfo("plugin-name", bukkitPlugin.description.name)
            sender.sendInfo("plugin-version", bukkitPlugin.description.version)
            bukkitPlugin.description.description?.let { sender.sendInfo("plugin-desc", it) }
            sender.sendInfo("plugin-author", bukkitPlugin.description.authors.joinToString(", "))
        }
    }


}