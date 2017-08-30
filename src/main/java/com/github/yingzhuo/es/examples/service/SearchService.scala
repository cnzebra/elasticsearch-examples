/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.service

import com.github.yingzhuo.es.examples.dao.ProductDocDao
import com.github.yingzhuo.es.examples.model.{Product, ProductDoc}
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

trait SearchService {

    def findAll(q: String, pageable: Pageable): Iterable[Product]
}

@Service("searchService")
class SearchServiceImpl @Autowired()(val productDocDao: ProductDocDao) extends SearchService {

    override def findAll(q: String, pageable: Pageable): Iterable[Product] = {
        val docPage: Page[ProductDoc] =
            if (q == null || q.isEmpty) {
                productDocDao.findAll(pageable)
            } else {
                val query = QueryBuilders.queryStringQuery(q)
                productDocDao.search(query, pageable)
            }

        docPage.getContent.asScala.map(_.toProduct)
    }
}
