/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples

import java.util.UUID
import javax.persistence.EntityManagerFactory

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.github.yingzhuo.es.examples.dao.UserDao
import com.github.yingzhuo.es.examples.module.listener.AuditorProvider
import com.github.yingzhuo.es.examples.security.SecurityInterceptor
import com.github.yingzhuo.es.examples.tool.{IdGenerator, PasswordHasher}
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.{EnableJpaAuditing, EnableJpaRepositories}
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.servlet.config.annotation.{InterceptorRegistry, PathMatchConfigurer, WebMvcConfigurerAdapter}
import org.springframework.web.util.UrlPathHelper

object ApplicationBoot extends App {

    SpringApplication.run(classOf[ApplicationBoot], args: _*)

    @SpringBootApplication
    class ApplicationBoot

    @Configuration
    class ApplicationBootConfigBean {

        @Bean
        def idGenerator(): IdGenerator[String] = new IdGenerator[String] {
            override def generate: String = UUID.randomUUID().toString.replaceAll("-", "")
        }

        @Bean
        def passwordHasher(): PasswordHasher = (s: String) => DigestUtils.md5Hex(s)

    }

    @Configuration
    class ApplicationBootConfigJackson {

        @Autowired(required = false)
        def configObjectMapper(om: ObjectMapper): Unit = Option(om).foreach(_.registerModule(DefaultScalaModule))

    }

    @Configuration
    class ApplicationBootConfigMvc extends WebMvcConfigurerAdapter {
        override def configurePathMatch(configurer: PathMatchConfigurer): Unit = {
            val helper = Option(configurer.getUrlPathHelper) match {
                case Some(x) => x
                case None => new UrlPathHelper()
            }

            helper.setDefaultEncoding("UTF-8")
            helper.setRemoveSemicolonContent(false)
            configurer.setUrlPathHelper(helper)
        }
    }

    @Configuration
    @EnableJpaAuditing
    @EnableJpaRepositories(Array("com.github.yingzhuo.es.examples"))
    @EnableTransactionManagement
    @ComponentScan(Array("com.github.yingzhuo.es.examples"))
    class ApplicationBootConfigJpa {

        @Bean
        def transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager = {
            new JpaTransactionManager(entityManagerFactory)
        }

        @Bean
        def auditorAware(): AuditorAware[String] = AuditorProvider
    }

    @Configuration
    class ApplicationBootConfigSecurity @Autowired()(val userDao: UserDao, val passwordHasher: PasswordHasher) extends WebMvcConfigurerAdapter {

        override def addInterceptors(registry: InterceptorRegistry): Unit = {
            registry.addInterceptor(new SecurityInterceptor(userDao, passwordHasher)).addPathPatterns("/**")
        }

    }

}
