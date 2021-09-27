package com.example.securingweb.repo;

import com.example.securingweb.domain.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepo extends CrudRepository<Session, Long> {
    List<Session> findSessionById(Long id);
}
