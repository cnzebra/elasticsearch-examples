package com.github.yingzhuo.es.examples.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

import scala.beans.BeanProperty

@Document(indexName = "example", `type` = "product")
class ProductDoc extends Serializable {

    @Id
    @BeanProperty
    var id: String = _

    @BeanProperty
    var name: String = _

    @BeanProperty
    var description: String = _

    @BeanProperty
    var price: Double = _

    override def toString: String = s"ProductDoc(id=$id)"

}
