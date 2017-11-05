package wsrest

class UrlMappings {

    static mappings = {
        "/$controller/$myid?/$controller?/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
                id(matches:/\d+/)
                myid(matches:/\d+/)
            }

        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/api/book/"(controller: 'api', action: 'book')
        "/api/book/$id?"(controller: 'api', action: 'book')
        "/api/library/"(controller: 'api', action: 'library')
        "/api/library/$id?"(controller: 'api', action: 'library')
        "/api/books/"(controller: 'api', action: 'books')
        "/api/libraries/"(controller: 'api', action: 'libraries')
        "/api/library/$myid?/book/$id?"(controller: 'api', action: 'library')
        "/api/library/$myid/books/" (controller: 'api', action: 'getBooksFromLibrary')
    }
}
