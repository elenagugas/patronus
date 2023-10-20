package com.gugas.entity

import jakarta.persistence.*

@Table(name = "DEVICES")
@Entity
class Device(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "serialNumber", nullable = false)
    var serialNumber: String? = null,

    @Column(name = "uuid", nullable = false, unique = true)
    var uuid: Long? = null,

    @Column(name = "phoneNumber", nullable = false)
    var phoneNumber: String? = null,

    @Column(name = "model", nullable = false)
    var model: String? = null,
)