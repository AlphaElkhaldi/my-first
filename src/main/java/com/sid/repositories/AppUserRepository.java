package com.sid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sid.entities.AppUser;
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  AppUser  findByUsername(String username);
}
