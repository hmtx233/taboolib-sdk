package top.blackcat.mc.service

import top.blackcat.mc.db.Database
import top.blackcat.mc.model.entity.MyPlayer

/***
 * MyPlayer 服务
 */
object MyPlayerService {


    fun save(myPlayer: MyPlayer, login: Boolean): Boolean {
        return Database.saveMyPlayer(myPlayer, login)
    }

    fun list(uid: String): List<MyPlayer> {
        return Database.getMyPlayer(uid)
    }

}


