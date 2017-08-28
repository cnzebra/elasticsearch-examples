package com.github.yingzhuo.es.examples.module.listener

import org.springframework.data.domain.AuditorAware

object AuditorProvider extends AuditorAware[String] {

    override def getCurrentAuditor: String = "应卓"

}
