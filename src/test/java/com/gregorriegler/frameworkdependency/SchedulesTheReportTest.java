package com.gregorriegler.frameworkdependency;

import com.gregorriegler.frameworkdependency.services.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class SchedulesTheReportTest {
 
    @SpyBean
    private LibraryService libraryService;

    @Test
    public void starts_report() {
        await()
          .atMost(Duration.of(1, SECONDS))
          .untilAsserted(() -> verify(libraryService, atLeast(1)).createBookReport());
    }
}