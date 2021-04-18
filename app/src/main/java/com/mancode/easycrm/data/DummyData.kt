package com.mancode.easycrm.data

import com.mancode.easycrm.db.Note
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.temporal.ChronoUnit
import kotlin.random.Random

val customers = listOf(
    Customer(
        0,
        "Polswat",
        Address("Przemysłowa 15", "Radom", "Polska"),
        "https://www.polswat.pl/"
    ).apply {
        contacts.addAll(
            listOf(
                Contact("Andrzej Witczak", "500123456"),
                Contact("Kazik", "500456789"),
                Contact("Dominik Popielski", "600456789", "polswat@polswat.pl")
            )
        )
        notes.addAll(
            listOf(
                Note(
                    0,
                    Instant.now().minus(187, ChronoUnit.HOURS),
                    "Po rozmowie telefonicznej okazało się, że indukcja przyjdzie z Chin. Za 4 miesiące."
                ),
                Note(0, Instant.now(), "Podpisano umowę."),
            )
        )
        dateLastContacted = LocalDate.of(2021, 3, 29)
        dateNextContact = LocalDate.of(2021, 4, 1)
    },
    Customer(
        1,
        "KAN-therm",
        Address("Zdrojowa 51", "Białystok-Kleosin", "Polska"),
        "http://pl.kan-therm.com/"
    ).apply {
        contacts.addAll(
            listOf(
                Contact("Ignacy", "600456789", "polswat@polswat.pl")
            )
        )
        notes.addAll(
            listOf(
                Note(
                    0,
                    Instant.now().minus(181, ChronoUnit.HOURS),
                    "Są zainteresowani odpłatnymi testami z naszym Boxem CNC."
                ),
                Note(0, Instant.now(), "Przeprowadzenie testów zostało umówione na 15-17.08.20."),
            )
        )
    }
)

val notes = listOf(
    Note(
        0,
        randomInstant(),
        "Po rozmowie telefonicznej okazało się, że indukcja przyjdzie z Chin. Za 4 miesiące."
    ),
    Note(1, randomInstant(), "Podpisano umowę."),
    Note(2, randomInstant(), "Są zainteresowani odpłatnymi testami z naszym Boxem CNC."),
    Note(3, randomInstant(), "Przeprowadzenie testów zostało umówione na 15-17.08.20."),
)

private fun randomInstant() =
    Instant.ofEpochSecond(
        LocalDateTime
            .of(
                Random.nextInt(2019, 2021),
                Random.nextInt(1, 12),
                Random.nextInt(1, 28),
                Random.nextInt(0, 23),
                Random.nextInt(0, 60)
            )
            .toEpochSecond(ZoneOffset.UTC)
    )