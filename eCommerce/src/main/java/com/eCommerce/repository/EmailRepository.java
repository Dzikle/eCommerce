package com.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eCommerce.email.Message;


@Repository
public interface EmailRepository extends JpaRepository<Message, Integer> {

}
