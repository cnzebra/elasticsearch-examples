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

import com.github.yingzhuo.es.examples.PasswordHasher
import com.github.yingzhuo.es.examples.service.UserService
import org.springframework.core.Ordered

class AuthenticationInterceptor(val userService: UserService, val passwordHasher: PasswordHasher) extends AbstractSecurityInterceptor {

    override def prehandle(request: HttpServletRequest, response: HttpServletResponse, m: Method): Unit = {
        logger.trace("认证检查: {}", s"${m.getDeclaringClass.getName}.${m.getName}(..)")

        request match {
            case BasicAuthentication(username, password) =>
                val hashed = passwordHasher.hash(password)
                val exists = Option(userService.login(username, hashed))

                if (exists.isEmpty) {
                    logger.trace("认证NG")
                    throw new InvalidOperationException
                } else {
                    logger.trace("认证OK")
                    SecurityContext.userHolder.set(exists.get)
                    SecurityContext.roleNamesHolder.set(exists.get.roleNames)
                }
            case _ =>
                logger.debug("认证条件缺失")
                throw new InvalidOperationException
        }
    }

    override def getOrder: Int = Ordered.LOWEST_PRECEDENCE - 1
}
