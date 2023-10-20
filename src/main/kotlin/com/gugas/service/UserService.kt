package com.gugas.service

import com.gugas.entity.User
import com.gugas.repository.DeviceRepository
import com.gugas.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val deviceRepository: DeviceRepository
) {
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun assignDeviceToUser(userId: Long, deviceId: Long) {
        val user = userRepository.findById(userId).orElseThrow { RuntimeException("No such user") }
        val device = deviceRepository.findById(deviceId).orElseThrow { RuntimeException("No such device") }
        user!!.devices.add(device!!)
        userRepository.save(user)
    }

    fun getUsersWithDevices(): MutableList<User?> {
        return userRepository.findAll()
    }
}