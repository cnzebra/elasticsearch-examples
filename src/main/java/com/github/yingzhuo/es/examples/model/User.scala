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
@Table(name = "`T_USER`")
class User extends Serializable {

    @Id
    @Column(name = "`ID`", length = 32)
    var id: String = _

    @Column(name = "`NAME`", length = 50)
    var name: String = _

    @Column(name = "`PWD`", length = 32)
    var password: String = _

    @ManyToMany
    @JoinTable(
        name = "T_USER_ROLE",
        joinColumns = Array(new JoinColumn(name = "USER_ID")),
        inverseJoinColumns = Array(new JoinColumn(name = "ROLE_ID"))
    )
    var roles: util.Set[Role] = _

    override def toString: String = s"User(id=$id, name=$name)"

    def canEqual(other: Any): Boolean = other.isInstanceOf[User]

    override def equals(other: Any): Boolean = other match {
        case that: User =>
            (that canEqual this) &&
                id == that.id
        case _ => false
    }

    override def hashCode(): Int = {
        val state = Seq(id)
        state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
    }
}
