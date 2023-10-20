package com.gugas.repository

import com.gugas.entity.Device
import org.springframework.data.repository.ListCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DevicesRepository : ListCrudRepository<Device?, Long?>