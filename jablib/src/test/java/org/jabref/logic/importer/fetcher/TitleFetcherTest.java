package org.jabref.logic.importer.fetcher;

import java.util.Optional;

import org.jabref.logic.importer.FetcherException;
import org.jabref.logic.importer.ImportFormatPreferences;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.StandardField;
import org.jabref.model.entry.types.StandardEntryType;
import org.jabref.testutils.category.FetcherTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@FetcherTest
class TitleFetcherTest {

    private TitleFetcher fetcher;
    private BibEntry bibEntryBischof2009;

    @BeforeEach
    void setUp() {
        fetcher = new TitleFetcher(mock(ImportFormatPreferences.class, Answers.RETURNS_DEEP_STUBS));

        bibEntryBischof2009 = new BibEntry(StandardEntryType.InProceedings)
                .withCitationKey("Bischof_2009")
                .withField(StandardField.AUTHOR, "Bischof, Marc and Kopp, Oliver and van Lessen, Tammo and Leymann, Frank")
                .withField(StandardField.BOOKTITLE, "2009 35th Euromicro Conference on Software Engineering and Advanced Applications")
                .withField(StandardField.PUBLISHER, "IEEE")
                .withField(StandardField.TITLE, "BPELscript: A Simplified Script Syntax for WS-BPEL 2.0")
                .withField(StandardField.YEAR, "2009")
                .withField(StandardField.MONTH, "#aug#")
                .withField(StandardField.PAGES, "39--46")
                .withField(StandardField.DOI, "10.1109/seaa.2009.21");
    }

    @Test
    void getName() {
        assertEquals("Title", fetcher.getName());
    }

    @Test
    void performSearchKopp2007() throws FetcherException {
        Optional<BibEntry> fetchedEntry = fetcher.performSearchById("BPELscript: A simplified script syntax for WS-BPEL 2.0");
        assertEquals(Optional.of(bibEntryBischof2009), fetchedEntry);
    }

    @Test
    void performSearchEmptyTitle() throws FetcherException {
        Optional<BibEntry> fetchedEntry = fetcher.performSearchById("");
        assertEquals(Optional.empty(), fetchedEntry);
    }

    @Test
    void performSearchInvalidTitle() throws FetcherException {
        Optional<BibEntry> fetchedEntry = fetcher.performSearchById("An unknown title where noi DOI can be determined");
        assertEquals(Optional.empty(), fetchedEntry);
    }
}
