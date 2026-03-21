package br.com.AutonomousAPI.services;

import br.com.AutonomousAPI.entities.Log;
import br.com.AutonomousAPI.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public void createLog(Log log) {
        logRepository.save(log);
    }
}
