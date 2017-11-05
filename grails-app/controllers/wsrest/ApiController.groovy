package wsrest

import grails.converters.JSON
import grails.converters.XML
import org.springframework.dao.DataIntegrityViolationException
class ApiController {

    def index() { }
    def book(){
        switch (request.getMethod()){
            case "POST":
                def bookInstance = new Book(params)
                if(bookInstance.save(flush:true)){
                    render(status: 201, text: "Book Created")
                }
                else {
                    render(status: 400, text: "Unable to create the book")
                }/**/
                break
           case "GET":
               def bookInstance = Book.get(params.id)
                if(bookInstance){
                    switch (request.getHeader("Accept")){
                        case "json":
                            render bookInstance as JSON
                            break
                        case "xml":
                            render bookInstance as XML
                            break
                    }
                    response.status=200
                }
                else
                    render(status: 404, text: "Book Not Found")
                break
            case "PUT":
                def bookInstance = Book.get(params.id);
                if(bookInstance){
                    bookInstance.properties=params
                    println(bookInstance.properties)
                    if(bookInstance.save(flush:true)){
                        render(status: 200, text: "Book updated")
                    }
                    else{
                        render(status: 400, text: "Unable to update the book")
                    }
                }
                else{
                    render(status: 404, text: "Book Not Found")
                }
                break
            case "DELETE":
                def bookInstance = Book.get(params.id)
                if(bookInstance){
                    try {
                        for(int i=0;i<Library.list().size();i++) {
                            Library libraryInstance=Library.list().get(i)
                            if(libraryInstance.books.remove(bookInstance)){
                                int lid=i+1
                                println("book deleted from library "+lid)
                            }
                        }
                        bookInstance.delete(flush: true)
                        render(status: 200, text: "Book Deleted Successfully")
                    } catch (DataIntegrityViolationException e){
                        render(status: 500, text: "Book Can't be Deleted!!")
                        e.printStackTrace()
                    }

                } else {
                    render(status: 404, text: "Book Not Found")
                }
                break
        }
    }
    def getBooksFromLibrary(){
        println(params)
        switch (request.getMethod()){
            case "GET":
                def libraryInstance = Library.get(params.myid)
                if(libraryInstance){
                    if(!libraryInstance.books.isEmpty()){
                        switch (request.getHeader("Accept")){
                            case "json":
                                render libraryInstance.books as JSON
                                break
                            case "xml":
                                render libraryInstance.books as XML
                                break
                        }
                        response.status=200
                    }else {
                        render(status: 404, text: "No books Found")
                    }
                }
                else {
                    render(status: 404, text: "Library Not Found")
                }
                break
        }
    }

    def library(){
        if(params.get('myid')){
            Library libraryInstance = Library.get(params.myid)
            if(libraryInstance){
                switch (request.getMethod()){
                    case "GET" :
                        if(libraryInstance.books.contains(Book.get(params.id))){
                            response.status=301
                            redirect action : 'book', controller : 'api', params:params , permanent: true
                        }
                        else{
                            render(status: 404, text: "Library does not contain this Book")
                        }
                        break
                    case "POST":
                        if(Book.list().contains(Book.get(params.id))){

                            render(status: 405, text: "Book exists already, You can not create something that exists it's violating.")
                        }
                        else{
                            response.status=301
                            println(params)
                            //quant à la redirection avec un methode POST on doit utiliser FORWARD à la place d'un REDIRECT
                            forward action : 'book', controller : 'api', params: [library:params.get('myid')] //, permanent: true
                        }
                        break
                    case "PUT":
                        if(libraryInstance.books.contains(Book.get(params.id))){
                            response.status=301
                            redirect action : 'book', controller : 'api', params: params, permanent: true
                        }
                        else{
                            render(status: 404, text: "Library does not containt this book. You can not modify it..")
                        }
                        break
                    case "DELETE":
                        if(!Book.list().contains(Book.get(params.id))){
                            render(status: 404, text: "Book does not exist")
                        }
                        else if(libraryInstance.books.contains(Book.get(params.id))){
                            response.status=301
                            redirect action : 'book', controller : 'api', params:params , permanent: true
                        }
                        else{
                            render(status: 404, text: "You can not delete this book because the requested library does not contain it.")
                        }
                        break
                }
            }else {
                render(status: 404, text: "Library Not Found")
            }
        }else {
            switch (request.getMethod()){
                case "GET":
                    def libraryInstance = Library.get(params.id)
                    if(libraryInstance){
                        switch (request.getHeader("Accept")){
                            case "json":
                                render libraryInstance as JSON
                                break
                            case "xml":
                                render libraryInstance as XML
                                break
                        }
                        response.status=200
                    }
                    else{
                        render(status: 404, text: "Library Not Found")
                    }
                    break
                case "POST":
                    def libraryInstance = new Library(params)
                    println(libraryInstance as JSON)
                    if(libraryInstance.save(flush:true)){
                        render(status: 201, text: "Library Created")
                    }
                    else {
                        render(status: 400, text: "Unable to create the library")
                    }
                    break
                case "PUT":
                    def libraryInstance = Library.get(params.id)
                    if(libraryInstance){
                        println(params)
                        println(libraryInstance.properties) // =params
                        libraryInstance.properties=params
                        println(libraryInstance.properties)
                        if(libraryInstance.save(flush:true)){
                            render(status: 200, text: "Library updated")
                        }
                        else{
                            render(status: 400, text: "Unable to update the library")
                        }
                    }
                    else{
                        render(status: 404, text: "Library Not Found")
                    }
                    break
                case "DELETE":
                    def libraryInstance = Library.get(params.id)
                    if(libraryInstance){
                        try {
                            libraryInstance.delete(flush: true)
                            render(status: 200, text: "Library Deleted Successfully")
                        } catch (DataIntegrityViolationException e){
                            render(status: 500, text: "Library Can't be Deleted!!")
                            e.printStackTrace()
                        }

                    } else {
                        render(status: 404, text: "Library Not Found")
                    }
                    break
            }

        }


    }
    def books(){
        switch (request.getMethod()){
            case "GET":
                def booksInstance = Book.list()
                if(booksInstance){
                    switch (request.getHeader("Accept")){
                        case "json":
                            render booksInstance as JSON
                            break
                        case "xml":
                            render booksInstance as XML
                            break
                    }
                    response.status=200
                }
                else {
                    render(status: 404, text: "Books not found")
                }
                break

        }

    }
    def libraries(){
        switch (request.getMethod()){
            case "GET":
                def librariesInstance = Library.list()
                if(librariesInstance){
                    switch (request.getHeader("Accept")){
                        case "json":
                            render librariesInstance as JSON
                            break
                        case "xml":
                            render librariesInstance as XML
                            break
                    }
                    response.status=200
                }
                else {
                    render(status: 404, text: "Libraries not found")
                }
                break
        }
    }
}
