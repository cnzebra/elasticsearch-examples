package com.github.yingzhuo.es.examples.dao

import com.github.yingzhuo.es.examples.module.Product
import org.springframework.data.jpa.repository.JpaRepository

trait ProductDao extends JpaRepository[Product, String] with ProductExtDao {
}

trait ProductExtDao

class ProductDaoImpl extends ProductExtDao
