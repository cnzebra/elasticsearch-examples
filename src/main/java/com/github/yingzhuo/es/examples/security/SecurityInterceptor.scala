/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.security

import java.lang.reflect.Method
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.github.yingzhuo.es.examples.dao.UserDao
import com.github.yingzhuo.es.examples.tool.PasswordHasher
import com.typesafe.scalalogging.LazyLogging
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

class SecurityInterceptor(val userDao: UserDao, val passwordHasher: PasswordHasher) extends HandlerInterceptorAdapter with LazyLogging {

    override def preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: scala.Any): Boolean = request match {
        case BasicAuthentication(username, password) =>
            val hashed = passwordHasher.hash(password)
            val exists = Option(userDao.findByNameAndPassword(username, hashed))

            if (exists.isEmpty) {
                logger.debug("认证或授权错误")
                throw new RefusedOperationException
            } else {
                SecurityContext.holder.set(exists.get)
            }

            true
        case _ =>
            logger.debug("认证或授权缺失")
            throw new RefusedOperationException
    }

    private def getMethod(handler: scala.Any): Option[Method] = handler match {
        case hm: HandlerMethod => Some(hm.getMethod)
        case _ => None
    }

}
