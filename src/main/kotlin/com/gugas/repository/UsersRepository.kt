package com.gugas.repository

import com.gugas.entity.User
import org.springframework.data.repository.ListCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : ListCrudRepository<User?, Long?>