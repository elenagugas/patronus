package com.gugas.service

import com.gugas.entity.User
import com.gugas.repository.DevicesRepository
import com.gugas.repository.UsersRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UsersRepository,
    private val deviceRepository: DevicesRepository
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