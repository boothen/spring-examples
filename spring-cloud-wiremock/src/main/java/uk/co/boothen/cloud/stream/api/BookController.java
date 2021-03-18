package uk.co.boothen.cloud.stream.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import uk.co.boothen.cloud.wiremock.server.api.BookApi;
import uk.co.boothen.cloud.wiremock.server.model.Book;

import java.util.List;

import javax.validation.Valid;

@RestController
public class BookController implements BookApi {

    @Override
    public ResponseEntity<String> addBook(@Valid Book book) throws Exception {
        return ResponseEntity.ok("added");
    }

    @Override
    public ResponseEntity<List<Book>> getBooks() throws Exception {
        return ResponseEntity.ok(List.of(new Book().title("some title")));
    }
}
