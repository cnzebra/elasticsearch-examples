/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.service

import com.github.yingzhuo.es.examples.dao.ProductDao
import com.github.yingzhuo.es.examples.module.Product
import com.github.yingzhuo.es.examples.tool.IdGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Propagation, Transactional}

trait ProductService {

    def saveProduct(product: Product): Product

    def findProductById(id: String): Product

    def changeProductName(id: String, name: String): Product

    def changeProductPrice(id: String, price: Double): Product

    def changeProductDescription(id: String, description: String): Product

    def deleteProductById(id: String): Unit

}

@Service("productService")
class ProductServiceImpl @Autowired()(val productDao: ProductDao, val idGenerator: IdGenerator[String]) extends ProductService {

    @Transactional
    override def saveProduct(product: Product): Product = {
        if (product.id == null) {
            product.id = idGenerator.generate
        }
        productDao.saveAndFlush(product)
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    override def findProductById(id: String): Product = Option(id) match {
        case None => null
        case Some(x) => productDao.findOne(x)
    }

    @Transactional
    override def changeProductName(id: String, name: String): Product = Option(id) match {
        case None => null
        case Some(x) =>
            val product = productDao.findOne(x)
            Option(product) match {
                case None => null
                case Some(p) =>
                    p.name = name
                    productDao.saveAndFlush(p)
                    p
            }
    }

    @Transactional
    override def changeProductPrice(id: String, price: Double): Product = Option(id) match {
        case None => null
        case Some(x) =>
            val product = productDao.findOne(x)
            Option(product) match {
                case None => null
                case Some(p) =>
                    p.price = price
                    productDao.saveAndFlush(p)
                    p
            }
    }

    @Transactional
    override def changeProductDescription(id: String, description: String): Product = Option(id) match {
        case None => null
        case Some(x) =>
            val product = productDao.findOne(x)
            Option(product) match {
                case None => null
                case Some(p) =>
                    p.description = description
                    productDao.saveAndFlush(p)
                    p
            }
    }

    @Transactional
    override def deleteProductById(id: String): Unit = Option(id) match {
        case None => ()
        case Some(x) => productDao.delete(x)
    }

}
