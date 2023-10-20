package com.gugas.service

import com.gugas.entity.Device
import com.gugas.repository.DeviceRepository
import org.springframework.stereotype.Service

@Service
class DeviceService(private val deviceRepository: DeviceRepository) {
    fun createDevice(device: Device): Device {
        return deviceRepository.save(device)
    }
}