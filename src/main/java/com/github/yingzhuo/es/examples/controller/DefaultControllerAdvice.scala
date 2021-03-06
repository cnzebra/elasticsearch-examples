/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.controller

import com.github.yingzhuo.es.examples.security.InvalidOperationException
import com.typesafe.scalalogging.LazyLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{ExceptionHandler, ResponseStatus, RestControllerAdvice}

@RestControllerAdvice
class DefaultControllerAdvice extends LazyLogging {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    def handleRefusedException(ex: InvalidOperationException): Json = Json("401", "401")

    @ExceptionHandler
    @ResponseStatus
    def handleException(ex: Exception): Json = {
        logger.error(ex.getMessage, ex)
        Json("500", "500")
    }

}
