package com.gugas.service

import com.gugas.entity.Device
import com.gugas.repository.DevicesRepository
import org.springframework.stereotype.Service

@Service
class DeviceService(private val devicesRepository: DevicesRepository) {
    fun createDevice(device: Device): Device {
        return devicesRepository.save(device)
    }
}