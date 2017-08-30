/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.model.auditing

import javax.persistence._

import com.github.yingzhuo.es.examples.ApplicationContextHolder
import com.github.yingzhuo.es.examples.dao.ProductDocDao
import com.github.yingzhuo.es.examples.model.Product
import com.typesafe.scalalogging.LazyLogging

class ProductListener extends LazyLogging {

    @PostPersist
    @PostUpdate
    def callbackPostPersistOrUpdate(product: Product): Unit = {
        logger.debug("post persist or update: {}", product)
        val doc = product.toProductDoc
        productDocDao.save(doc)
    }

    @PostRemove
    def callbackPostRemove(product: Product): Unit = {
        logger.debug("post remove: {}", product)
        productDocDao.delete(product.id)
    }

    private def productDocDao: ProductDocDao = ApplicationContextHolder.get.getBean(classOf[ProductDocDao])
}
