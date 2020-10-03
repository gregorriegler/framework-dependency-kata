package com.gregorriegler.frameworkdependency;

import com.gregorriegler.frameworkdependency.model.Book;
import com.gregorriegler.frameworkdependency.model.LibraryService;
import com.gregorriegler.frameworkdependency.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookReportTest {

    @Autowired
    private LibraryService libraryService;

    @MockBean
    private BookRepository repository;

    @Test
    public void writes_proper_report() throws IOException {
        Book book = new Book();
        book.isbn = "123";
        book.title = "title";
        when(repository.findAll()).thenReturn(List.of(book));

        libraryService.createBookReport();

        assertThat(latestReportFromDisk()).isEqualTo("123,title");
    }

    private String latestReportFromDisk() throws IOException {
        List<File> reports = Files.list(Path.of(System.getProperty("java.io.tmpdir")))
            .filter(path -> path.toString().matches(".*book_report.*"))
            .map(Path::toFile)
            .sorted(Comparator.comparingLong(File::lastModified))
            .collect(Collectors.toList());
        File latestReport = reports.get(reports.size() - 1);
        String report = Files.readString(Path.of(latestReport.toURI()));
        return report;
    }
}
