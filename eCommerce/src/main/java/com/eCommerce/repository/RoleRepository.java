package com.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
