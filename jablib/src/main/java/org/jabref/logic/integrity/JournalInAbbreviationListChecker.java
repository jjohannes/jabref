package org.jabref.logic.integrity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.jabref.logic.journals.JournalAbbreviationRepository;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.Field;

public class JournalInAbbreviationListChecker implements EntryChecker {

    private final Field field;
    private final JournalAbbreviationRepository abbreviationRepository;

    public JournalInAbbreviationListChecker(Field field, JournalAbbreviationRepository abbreviationRepository) {
        this.field = Objects.requireNonNull(field);
        this.abbreviationRepository = Objects.requireNonNull(abbreviationRepository);
    }

    @Override
    public List<IntegrityMessage> check(BibEntry entry) {
        Optional<String> value = entry.getFieldOrAliasLatexFree(field);
        if (value.isEmpty()) {
            return List.of();
        }

        final String journal = value.get();
        if (!abbreviationRepository.isKnownName(journal)) {
            return List.of(new IntegrityMessage(Localization.lang("journal not found in abbreviation list"), entry, field));
        }

        return List.of();
    }
}
