/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.service

import com.github.yingzhuo.es.examples.dao.UserDao
import com.github.yingzhuo.es.examples.model.User
import com.typesafe.scalalogging.LazyLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Propagation, Transactional}

trait UserService {

    def login(name: String, password: String): User

}

@Service("userService")
class UserServiceImpl @Autowired()(val userDao: UserDao) extends UserService with LazyLogging {

    @Cacheable(cacheNames = Array("userLogingCache"), unless = "#root == null")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    override def login(name: String, password: String): User = userDao.findByNameAndPassword(name, password)

}
