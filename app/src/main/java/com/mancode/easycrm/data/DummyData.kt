package com.mancode.easycrm.data

import com.mancode.easycrm.db.Address
import com.mancode.easycrm.db.Contact
import com.mancode.easycrm.db.CustomerRaw

val customersRaw = listOf(
    CustomerRaw(
        1,
        "Polswat",
        "https://www.polswat.pl/",
    ),
    CustomerRaw(
        2,
        "KAN-therm",
        "http://pl.kan-therm.com/"
    )
)

val addresses = listOf(
    Address(1, 1, "Przemysłowa 15", "Radom", "Polska"),
    Address(2, 2, "Zdrojowa 51", "Białystok-Kleosin", "Polska")
)

val contacts = listOf(
    Contact("1", 1, "Andrzej Witczak", "500123456", hasPhoneNumber = true),
    Contact("2", 1, "Kazik", "500456789", hasPhoneNumber = true),
    Contact("3", 1, "Dominik Popielski", "600456789", "polswat@polswat.pl", hasPhoneNumber = true),
    Contact("4", 2, "Ignacy", "600456789", "ignacy@kan-therm.pl", hasPhoneNumber = true)
)
