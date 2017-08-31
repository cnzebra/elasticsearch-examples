/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.controller

import com.github.yingzhuo.es.examples.security.RequiresRoles
import com.github.yingzhuo.es.examples.service.ProductService
import com.typesafe.scalalogging.LazyLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/products"))
class ProductController @Autowired()(val productService: ProductService) extends LazyLogging {

    @PostMapping(Array("/"))
    @RequiresRoles(Array("ROLE_ADMIN"))
    def saveOne(@RequestBody form: ProductForm): Json = {
        logger.debug("新建产品")
        var prod = form.toProduct
        prod = productService.saveProduct(prod)
        Json("product" -> prod)
    }

    @GetMapping(Array("/{id}/"))
    @RequiresRoles(Array("ROLE_ADMIN"))
    def findOne(@PathVariable("id") id: String): Json = {
        logger.debug("通过ID({})查找产品", id)
        val prod = productService.findProductById(id)
        Json("product" -> prod)
    }

    @PutMapping(Array("/{id}/name/{name}/"))
    @RequiresRoles(Array("ROLE_ADMIN"))
    def changeName(@PathVariable("id") id: String, @PathVariable("name") name: String): Json = {
        logger.debug("通过ID({})修改产品名称({})", id, name)
        val prod = productService.changeProductName(id, name)
        Json("product" -> prod)
    }

    @PutMapping(Array("/{id}/price/{price}/"))
    @RequiresRoles(Array("ROLE_ADMIN"))
    def changePrice(@PathVariable("id") id: String, @PathVariable("price") price: Double): Json = {
        logger.debug("通过ID({})修改产品价格({})", id, price)
        val prod = productService.changeProductPrice(id, price)
        Json("product" -> prod)
    }

    @PutMapping(Array("/{id}/description/{desc}"))
    @RequiresRoles(Array("ROLE_ADMIN"))
    def changeDescription(@PathVariable("id") id: String, @PathVariable("desc") description: String): Json = {
        logger.debug("通过ID({})修改产品详情({})", id, description)
        val prod = productService.changeProductDescription(id, description)
        Json("product" -> prod)
    }

}
