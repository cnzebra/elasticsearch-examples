/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples

import java.nio.charset.{Charset, StandardCharsets}
import java.util.Base64
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest

package object security {

    class RefusedOperationException extends Exception

    object BasicAuthentication {

        def apply(username: String, password: String): String = {
            def base64UrlEncode(string: String, charset: Charset = StandardCharsets.UTF_8): String =
                new String(Base64.getUrlEncoder.encode(string.getBytes(charset)))

            "Basic " + base64UrlEncode(s"$username:$password")
        }

        def unapply(request: ServletRequest): Option[(String, String)] = request match {
            case null => None
            case x: HttpServletRequest => unapply(x.getHeader("Authorization"))
            case _ => None
        }

        def unapply(headerValue: String): Option[(String, String)] = {
            val REGEX = "Basic (.+)".r

            def base64UrlDecode(string: String, charset: Charset = StandardCharsets.UTF_8): String =
                new String(Base64.getUrlDecoder.decode(string.getBytes(charset)))

            headerValue match {
                case null => None
                case REGEX(encodedString) =>
                    try {
                        val up = base64UrlDecode(encodedString)
                        val parts = up.split(":")
                        if (parts.length == 2) Option((parts(0), parts(1))) else None
                    } catch {
                        case _: Exception => None
                    }
                case _ =>
                    None
            }
        }
    }

}
