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

    def canEqual(other: Any): Boolean = other.isInstanceOf[ProductDoc]

    override def equals(other: Any): Boolean = other match {
        case that: ProductDoc =>
            (that canEqual this) &&
                id == that.id
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(id)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
}
