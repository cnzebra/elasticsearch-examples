/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.yingzhuo.es.examples.model.Product
import org.springframework.data.domain.{PageRequest, Pageable}

import scala.beans.BeanProperty

package object controller {

    /* Json */

    object Json {

        def apply(p: Map[String, Any]): Json = new Json(p)

        def apply(pairs: (String, Any)*): Json = Json(Map(pairs: _*))
    }

    @JsonIgnoreProperties(Array("empty"))
    final case class Json(code: String = "200", errorMessage: String = null, payload: Map[String, Any] = Map()) {

        def this(p: Map[String, Any]) = this(payload = p)

        def size: Int = payload match {
            case null => 0
            case _ => payload.size
        }

        def isEmpty: Boolean = payload match {
            case null => true
            case _ => payload.isEmpty
        }
    }

    /* Form */

    final class ProductForm {
        @BeanProperty
        var id: String = _
        @BeanProperty
        var name: String = _
        @BeanProperty
        var price: Double = _
        @BeanProperty
        var description: String = _
    }

    implicit final class RichProductForm(val form: ProductForm) {
        require(form != null, null)

        def toProduct: Product = {
            val prod = new Product()
            prod.id = form.id
            prod.name = form.name
            prod.price = form.price
            prod.description = form.description
            prod
        }
    }

    /* Pageable */

    implicit final class RichPageNumberPageSizePair(val pair: (Int, Int)) {

        def asPageable: (Pageable, Int, Int) = {
            def cu(p: (Int, Int)): (Int, Int) = {
                val (page, size) = p
                (if (page <= 0) 1 else page, if (size <= 0) 20 else size)
            }

            val pp = cu(pair)
            (new PageRequest(pp._1 - 1, pp._2), pp._1, pp._2)
        }
    }

}
