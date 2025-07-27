package com.example.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepository extends JpaRepository<DemoModel, Integer> {

	 Optional<DemoModel> findByName(String Name);
}
