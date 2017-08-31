/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.model

import java.util
import javax.persistence._

@Entity
@Table(name = "`T_ROLE`")
class Role extends Serializable {

    @Id
    @Column(name = "`ID`")
    var id: String = _

    @Column(name = "`NAME`")
    var name: String = _

    @ManyToMany(mappedBy = "roles")
    var users: util.Set[User] = _

    override def toString: String = s"Role(id=$id, name=$name)"

    def canEqual(other: Any): Boolean = other.isInstanceOf[Role]

    override def equals(other: Any): Boolean = other match {
        case that: Role =>
            (that canEqual this) &&
                id == that.id
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(id)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
}
