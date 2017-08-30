/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.typesafe.scalalogging.StrictLogging
import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.core.Ordered
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

package object examples {

    trait IdGenerator[ID] {
        def generate: ID
    }

    object DefaultStringIdGenerator extends IdGenerator[String] {
        override def generate: String = java.util.UUID.randomUUID().toString.replaceAll("-", "")
    }

    /* ------------------------------------------------------------------------------------------------------------- */

    trait PasswordHasher {
        def hash(s: String): String
    }

    object DefaultPasswordHasher extends PasswordHasher {
        override def hash(s: String): String = {
            val m = MessageDigest.getInstance("MD5")
            val b = s.getBytes(StandardCharsets.UTF_8)
            m.update(b, 0, b.length)
            new BigInteger(1, m.digest()).toString(16)
        }
    }

    /* ------------------------------------------------------------------------------------------------------------- */

    object LoggingInterceptor extends HandlerInterceptorAdapter with Ordered with StrictLogging {

        override def preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: scala.Any): Boolean = {

            val handlerMethod = handler.asInstanceOf[HandlerMethod]

            logger.debug("-" * 80)

            logger.debug("[Path]: ")
            logger.debug("\t\t\t{}", request.getRequestURI)

            logger.debug("[Method]: ")
            logger.debug("\t\t\t{}", request.getMethod)

            logger.debug("[Headers]: ")
            val headerNames = request.getHeaderNames
            while (headerNames.hasMoreElements) {
                val name = headerNames.nextElement
                val value = request.getHeader(name)
                logger.debug("\t\t\t{} = {}", name, value)
            }

            logger.debug("[Params]: ")
            val paramNames = request.getParameterNames
            while (paramNames.hasMoreElements) {
                val name = paramNames.nextElement
                val value = request.getParameter(name)
                logger.debug("\t\t\t{} = {}", name, value)
            }

            if (handlerMethod != null) {
                logger.debug("[Controller]: ")
                logger.debug("\t\t\ttype = {}", handlerMethod.getBeanType.getName)
                logger.debug("\t\t\tmethod-name = {}", handlerMethod.getMethod.getName)
            }

            logger.debug("-" * 80)
            true
        }

        override def getOrder: Int = Ordered.HIGHEST_PRECEDENCE
    }

    /* ------------------------------------------------------------------------------------------------------------- */

    object ApplicationContextHolder {

        def apply(): AnyRef = new ApplicationContextHolderBean

        private var applicationContext: ApplicationContext = _

        def get: ApplicationContext = applicationContext

        def getAsOption: Option[ApplicationContext] = Option(get)

        protected class ApplicationContextHolderBean extends ApplicationContextAware {
            override def setApplicationContext(ac: ApplicationContext): Unit = ApplicationContextHolder.applicationContext = ac
        }

    }

}
