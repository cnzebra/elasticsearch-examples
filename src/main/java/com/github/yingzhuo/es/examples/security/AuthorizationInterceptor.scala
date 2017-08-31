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

import org.springframework.core.Ordered

class AuthorizationInterceptor extends AbstractSecurityInterceptor {

    override def prehandle(request: HttpServletRequest, response: HttpServletResponse, m: Method): Unit = {

        logger.trace("授权检查: {}", s"${m.getDeclaringClass.getName}.${m.getName}(..)")

        val roles = SecurityContext.getRoleNames
        val required = m.getAnnotation(classOf[RequiresRoles]) match {
            case null => Set()
            case a => a.value() match {
                case null => Set()
                case x => x.toSet
            }
        }

        required.foreach { r =>
            if (!roles.contains(r)) {
                logger.trace("授权NG 名为'{}'角色缺失", r)
                throw new InvalidOperationException
            }
        }

        logger.trace("授权OK")
    }

    override def getOrder: Int = Ordered.LOWEST_PRECEDENCE

}
