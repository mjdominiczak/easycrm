package com.mancode.easycrm.data

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

val customers = listOf(
        Customer(
                "Polswat",
                Address("Przemysłowa 15", "Radom", "Polska"),
                "https://www.polswat.pl/"
        ).apply {
            contacts.addAll(listOf(
                    Contact("Andrzej Witczak", "500123456"),
                    Contact("Kazik", "500456789"),
                    Contact("Dominik Popielski", "600456789", "polswat@polswat.pl")
            ))
            notes.addAll(listOf(
                    Note(Instant.now().minus(187, ChronoUnit.HOURS), "Po rozmowie telefonicznej okazało się, że indukcja przyjdzie z Chin. Za 4 miesiące."),
                    Note(Instant.now(), "Podpisano umowę."),
            ))
            dateLastContacted = LocalDate.of(2021, 3, 29)
            dateNextContact = LocalDate.of(2021, 4, 1)
        },
        Customer(
                "KAN-therm",
                Address("Zdrojowa 51", "Białystok-Kleosin", "Polska"),
                "http://pl.kan-therm.com/"
        ).apply {
            contacts.addAll(listOf(
                    Contact("Ignacy", "600456789", "polswat@polswat.pl")
            ))
            notes.addAll(listOf(
                    Note(Instant.now().minus(181, ChronoUnit.HOURS), "Są zainteresowani odpłatnymi testami z naszym Boxem CNC."),
                    Note(Instant.now(), "Przeprowadzenie testów zostało umówione na 15-17.08.20."),
            ))
        }
)