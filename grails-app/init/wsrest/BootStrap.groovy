package wsrest

class BootStrap {

    def init = { servletContext ->

        Date d = new Date(2010,10,25)
       String newdate= d.format("YYYY-MM-DD HH:mm:ss.Ms")
        Date d2 = new Date(2011,05,22)
        def library1 = new Library(name: "St Jose", address: "11 Bd Lolo Antibes",yearCreated: 2010).save(flush: true)
        def book1=new Book(name: "I love you", isbn: "2154565465", author: "J Kelly", releaseDate:d, library: library1).save(flush:true)
        def book2=new Book(name: "Be with you", isbn: "2545666587", author: "Anne Keny", releaseDate:d2, library: library1).save(flush:true)
        library1.addToBooks(book1).save(flush:true)
        library1.addToBooks(book2).save(flush:true)
        def library2 = new Library(name: "Bibl Albert Camus", address: "11 rue Cannes ",yearCreated: 1993).save(flush: true)
        def book3=new Book(name: "Je te deteste", isbn: "00124544785", author: "Anne Keny", releaseDate:d, library: library1).save(flush:true)
        def book4=new Book(name: "Cry with me", isbn: "36521548746", author: "Bean Quitois", releaseDate:d2, library: library1).save(flush:true)
        library2.addToBooks(book3).save(flush:true)
        library2.addToBooks(book4).save(flush:true)
    }
    def destroy = {
    }
}
