/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.model

import java.util.Date
import javax.persistence._

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.yingzhuo.es.examples.model.auditing.ProductListener
import org.springframework.data.annotation.{CreatedBy, CreatedDate, LastModifiedDate}
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import scala.beans.BeanProperty

@Entity
@Table(name = "`T_PRODUCT`")
@EntityListeners(Array(classOf[AuditingEntityListener], classOf[ProductListener]))
@JsonIgnoreProperties(Array("lastModified", "created", "auditorId"))
class Product extends Serializable {

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

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "`LAST_MODIFIED_DATE`")
    @BeanProperty
    var lastModified: Date = _

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "`CREATED_DATE`")
    @BeanProperty
    var created: Date = _

    @CreatedBy
    @Column(name = "`AUDITOR_ID`", length = 32)
    @BeanProperty
    var auditorId: String = _

    override def toString: String = s"Product(id=$id)"
}
