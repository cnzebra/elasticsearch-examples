/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.context.{ApplicationContext, ApplicationContextAware}

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
        override def hash(s: String): String = DigestUtils.md5Hex(s)
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
