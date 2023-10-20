package com.gugas

import com.gugas.entity.Device
import com.gugas.entity.User
import com.gugas.service.DeviceService
import com.gugas.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    private val userService: UserService,
    private val deviceService: DeviceService
) {

    @PostMapping(path = ["/create/device"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createDevice(@RequestBody device: Device): ResponseEntity<out Any> {
        val deviceCreated = deviceService.createDevice(device)
        return ResponseEntity(deviceCreated, HttpStatus.CREATED)
    }

    @PostMapping(path = ["/create/user"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@RequestBody user: User): ResponseEntity<out Any> {
        val userCreated = userService.createUser(user)
        return ResponseEntity(userCreated, HttpStatus.CREATED)
    }

    @PostMapping("/create/mapping")
    fun mapUserAndDevice(userId: Long, deviceId: Long): ResponseEntity<out Any> {
        userService.assignDeviceToUser(userId, deviceId)
        return ResponseEntity("OK", HttpStatus.CREATED)
    }

    @GetMapping("/print/mapping")
    fun getMapping(): ResponseEntity<out Any> {
        val result = userService.getUsersWithDevices()
        return ResponseEntity(result, HttpStatus.OK)
    }

}
