package com.gugas

import com.gugas.entity.Device
import com.gugas.entity.User
import com.gugas.repository.DeviceRepository
import com.gugas.repository.UserRepository
import com.gugas.service.DeviceService
import com.gugas.service.UserService
import jakarta.transaction.Transactional
import org.aspectj.lang.annotation.After
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate
import kotlin.test.assertEquals

@Transactional
@DataJpaTest
class ServicesTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    private val deviceRepository: DeviceRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null

    private val user = User(firstName = "Jane", lastName = "Do", birthday = LocalDate.now(), address = "address1")
    private val device = Device(serialNumber = "00001", uuid = 1111, model = "some_model", phoneNumber = "911")

    @AfterEach
    fun clear() {
        entityManager.clear()
    }

    @Test
    fun assignNonExistentDeviceToUserTest() {
        val userService = UserService(userRepository!!, deviceRepository!!)
        val userCreated = userService.createUser(user)
        entityManager.flush()

        assertThrows<RuntimeException> {
            userService.assignDeviceToUser(userCreated.id!!, 1)
        }
    }

    @Test
    fun assignDeviceToNonExistentUserTest() {
        val userService = UserService(userRepository!!, deviceRepository!!)
        val deviceService = DeviceService(deviceRepository!!)
        val deviceCreated = deviceService.createDevice(device)
        entityManager.flush()

        assertThrows<RuntimeException> {
            userService.assignDeviceToUser(1, deviceCreated.id!!)
        }
    }

    @Test
    fun assignAndGetResultTest() {
        val userService = UserService(userRepository!!, deviceRepository!!)
        val deviceService = DeviceService(deviceRepository)
        val userCreated = userService.createUser(user)
        val deviceCreated = deviceService.createDevice(device)

        entityManager.flush()
        assertDoesNotThrow {
            userService.assignDeviceToUser(userCreated.id!!, deviceCreated.id!!)
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