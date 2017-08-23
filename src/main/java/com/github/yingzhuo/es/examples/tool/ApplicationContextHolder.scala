package com.github.yingzhuo.es.examples.tool

import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.stereotype.Component

object ApplicationContextHolder {

    private var applicationContext: ApplicationContext = _

    def get: ApplicationContext = applicationContext

    def getAsOption: Option[ApplicationContext] = Option(get)

}

@Component
final class ApplicationContextHolder extends ApplicationContextAware {

    override def setApplicationContext(ac: ApplicationContext): Unit = ApplicationContextHolder.applicationContext = ac

}
