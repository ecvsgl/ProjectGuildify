package com.guildify.guildify.repository;

import com.guildify.guildify.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findRoleByAuthority(String s);
}
