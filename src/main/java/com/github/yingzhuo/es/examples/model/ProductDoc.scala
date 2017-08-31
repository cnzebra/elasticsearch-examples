package com.github.yingzhuo.es.examples.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "example", `type` = "product")
class ProductDoc extends Serializable {

    @Id
    var id: String = _

    var name: String = _

    var description: String = _

    var price: Double = _

    override def toString: String = s"ProductDoc(id=$id)"

}
