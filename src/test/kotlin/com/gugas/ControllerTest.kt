package com.gugas

import com.gugas.entity.Device
import com.gugas.entity.User
import com.gugas.repository.DevicesRepository
import com.gugas.repository.UsersRepository
import com.gugas.service.DeviceService
import com.gugas.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate
import kotlin.test.assertEquals

@DataJpaTest
class ServicesTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    private val devicesRepository: DevicesRepository? = null

    @Autowired
    private val usersRepository: UsersRepository? = null

    private val user = User(firstName = "Jane", lastName = "Do", birthday = LocalDate.now(), address = "address1")
    private val device = Device(serialNumber = "00001", uuid = 1111, model = "some_model", phoneNumber = "911")

    @Test
    fun assignNonExistentDeviceToUserTest() {
        val userService = UserService(usersRepository!!, devicesRepository!!)
        userService.createUser(user)
        assertThrows<RuntimeException> {
            userService.assignDeviceToUser(1, 1)
        }
    }

    @Test
    fun assignDeviceToNonExistentUserTest() {
        val userService = UserService(usersRepository!!, devicesRepository!!)
        val deviceService = DeviceService(devicesRepository!!)
        deviceService.createDevice(device)
        assertThrows<RuntimeException> {
            userService.assignDeviceToUser(1, 1)
        }
    }

    @Test
    fun assignAndGetResultTest() {
        val userService = UserService(usersRepository!!, devicesRepository!!)
        val deviceService = DeviceService(devicesRepository)
        userService.createUser(user)
        deviceService.createDevice(device)

        entityManager.flush()

        assertDoesNotThrow {
            userService.assignDeviceToUser(1, 1)
        }

        val result = userService.getUsersWithDevices()
        val expectedUser: User? = User(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            birthday = user.birthday,
            address = user.address,
            devices = mutableSetOf(device)
        )
        val expectedResult = mutableListOf(
            expectedUser
        )
        assertEquals(expectedResult.size, result.size)
        assertEquals(expectedResult.first()?.id, result.first()?.id)
        assertEquals(expectedResult.first()?.devices?.size, result.first()?.devices?.size)
        assertEquals(expectedResult.first()?.devices?.first()?.id, result.first()?.devices?.first()?.id)
    }

}