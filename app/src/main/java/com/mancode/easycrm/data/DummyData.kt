package com.mancode.easycrm.data

import org.threeten.bp.LocalDate

val customers = listOf(
    Customer(
        1,
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
        dateLastContacted = LocalDate.of(2021, 3, 29)
        dateNextContact = LocalDate.of(2021, 4, 1)
    },
    Customer(
        2,
        "KAN-therm",
        Address("Zdrojowa 51", "Białystok-Kleosin", "Polska"),
        "http://pl.kan-therm.com/"
    ).apply {
        contacts.addAll(
            listOf(
                Contact("Ignacy", "600456789", "polswat@polswat.pl")
            )
        )
    }
)