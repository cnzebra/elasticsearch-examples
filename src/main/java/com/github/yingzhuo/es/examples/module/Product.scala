package com.github.yingzhuo.es.examples.module

import javax.persistence._
import com.github.yingzhuo.es.examples.module.listener._

@Entity
@Table(name = "`T_PRODUCT`")
@EntityListeners(Array(classOf[ProductListener]))
class Product() extends AnyRef with Serializable {

    @Id
    @Column(name = "`ID`", length = 32)
    var id: String = _

    @Column(name = "`NAME`", length = 40)
    var name: String = _

    @Column(name = "`PRICE`")
    var price: Double = _

    @Column(name = "`DESCRIPTION`", length = 2000)
    var description: String = _

    override def toString: String = s"Product(id=$id)"

}
