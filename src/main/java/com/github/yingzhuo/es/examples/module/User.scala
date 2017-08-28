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

import org.springframework.data.jpa.domain.support.AuditingEntityListener

import scala.beans.BeanProperty

@Entity
@Table(name = "`T_USER`")
@EntityListeners(Array(classOf[AuditingEntityListener]))
class User extends Serializable {

    @Id
    @Column(name = "`ID`", length = 32)
    @BeanProperty
    var id: String = _

    @Column(name = "`NAME`", length = 50)
    @BeanProperty
    var name: String = _

    @Column(name = "`PWD`", length = 32)
    @BeanProperty
    var password: String = _

}
