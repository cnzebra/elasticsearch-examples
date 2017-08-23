package com.github.yingzhuo.es.examples.service

import com.github.yingzhuo.es.examples.dao.ProductDao
import com.github.yingzhuo.es.examples.module.Product
import com.typesafe.scalalogging.LazyLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Propagation, Transactional}

trait ProductService {

    def findProductById(id: String): Product

    def changeProductName(id: String, name: String): Product

    def changeProductPrice(id: String, price: Double): Product

    def changeProductPrice(id: String, description: String): Product

    def deleteProductById(id: String): Unit

}

@Service("productService")
class ProductServiceImpl @Autowired()(val productDao: ProductDao) extends ProductService with LazyLogging {

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
    override def changeProductPrice(id: String, description: String): Product = Option(id) match {
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
