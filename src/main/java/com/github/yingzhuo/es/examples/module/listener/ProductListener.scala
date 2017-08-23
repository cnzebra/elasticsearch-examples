package com.github.yingzhuo.es.examples.module.listener

import javax.persistence._

import com.github.yingzhuo.es.examples.module.Product
import com.typesafe.scalalogging.StrictLogging

class ProductListener extends StrictLogging {

    @PostPersist
    def callbackPostPersist(product: Product): Unit = {
        logger.debug("post persist: {}", product)
    }

    @PostRemove
    def callbackPostRemove(product: Product): Unit = {
        logger.debug("post remove: {}", product)
    }

    @PostUpdate
    def callbackPostUpdate(product: Product): Unit = {
        logger.debug("post update: {}", product)
    }

}
