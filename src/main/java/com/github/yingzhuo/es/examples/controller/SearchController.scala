/*
*  .        _           _   _                              _                                          _           __ _ _
* /\\   ___| | __ _ ___| |_(_) ___ ___  ___  __ _ _ __ ___| |__         _____  ____ _ _ __ ___  _ __ | | ___  ___ \ \ \ \
*( ( ) / _ \ |/ _` / __| __| |/ __/ __|/ _ \/ _` | '__/ __| '_ \ _____ / _ \ \/ / _` | '_ ` _ \| '_ \| |/ _ \/ __| \ \ \ \
* \\/ |  __/ | (_| \__ \ |_| | (__\__ \  __/ (_| | | | (__| | | |_____|  __/>  < (_| | | | | | | |_) | |  __/\__ \  ) ) ) )
*  '   \___|_|\__,_|___/\__|_|\___|___/\___|\__,_|_|  \___|_| |_|      \___/_/\_\__,_|_| |_| |_| .__/|_|\___||___/ / / / /
* =============================================================================================|_|=============== /_/_/_/
*/
package com.github.yingzhuo.es.examples.controller

import com.github.yingzhuo.es.examples.model.Product
import com.github.yingzhuo.es.examples.service.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{GetMapping, RequestParam, RestController}

@RestController
class SearchController @Autowired()(val searchService: SearchService) {

    @GetMapping(Array("/search/"))
    def search(@RequestParam(value = "q", required = false) query: String): Iterable[Product] = {
        searchService.findAll(query)
    }

}
