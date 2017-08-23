package com.github.yingzhuo.es.examples

package object controller {

    final case class Json(code: String = "200", errorMessage: String =  null, payload: Map[String, Any] = Map()) {
        def this(p: Map[String, Any]) = this(payload = p)

        def size: Int = payload match {
            case null => 0
            case _ => payload.size
        }

        def empty: Boolean = payload match {
            case null => true
            case _ => payload.isEmpty
        }
    }

    object Json {

        def apply(p: Map[String, Any]): Json = new Json(p)

        def apply(t: (String, Any)): Json = Json(Map(t._1 -> t._2))
    }

}
