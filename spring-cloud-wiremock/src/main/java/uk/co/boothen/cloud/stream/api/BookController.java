package uk.co.boothen.cloud.stream.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import uk.co.boothen.cloud.stream.client.BookClient;
import uk.co.boothen.cloud.wiremock.server.api.BookApi;
import uk.co.boothen.cloud.wiremock.server.model.Book;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
public class BookController implements BookApi {

    private final BookClient bookClient;

    public BookController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @Override
    public ResponseEntity<String> addBook(@Valid Book book) {
        return bookClient.addBook(new uk.co.boothen.cloud.wiremock.client.model.Book().author(book.getAuthor()).title(book.getTitle()));
    }

    @Override
    public ResponseEntity<List<Book>> getBooks() {
        ResponseEntity<List<uk.co.boothen.cloud.wiremock.client.model.Book>> books = bookClient.getBooks();
        List<Book> bookList = books.getBody()
                                   .stream()
                                   .map(clientBook -> new Book().title(clientBook.getTitle())
                                                                .author(clientBook.getAuthor()))
                                   .collect(Collectors.toList());
        return ResponseEntity.ok(bookList);
    }
}
