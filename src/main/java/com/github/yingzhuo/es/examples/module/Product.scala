/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.module

import javax.persistence._

import com.github.yingzhuo.es.examples.module.listener._

import scala.beans.BeanProperty

@Entity
@Table(name = "`T_PRODUCT`")
@EntityListeners(Array(classOf[ProductListener]))
class Product() extends AnyRef with Serializable {

    @Id
    @Column(name = "`ID`", length = 32)
    @BeanProperty
    var id: String = _

    @Column(name = "`NAME`", length = 40)
    @BeanProperty
    var name: String = _

    @Column(name = "`PRICE`")
    @BeanProperty
    var price: Double = _

    @Column(name = "`DESCRIPTION`", length = 2000)
    @BeanProperty
    var description: String = _

    override def toString: String = s"Product(id=$id)"
}
