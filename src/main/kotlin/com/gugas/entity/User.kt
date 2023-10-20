package com.gugas.entity

import jakarta.persistence.*
import java.time.LocalDate

@Table(name = "USERS")
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "firstName", nullable = false)
    var firstName: String? = null,

    @Column(name = "lastName", nullable = false)
    var lastName: String? = null,

    @Column(name = "address", nullable = false)
    var address: String? = null,

    @Column(name = "birthday", nullable = false)
    var birthday: LocalDate? = null,

    @OneToMany
    var devices: MutableSet<Device> = mutableSetOf()
)