package ru.VYurkin.RestAPIForSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.VYurkin.RestAPIForSensor.models.Sensor;
import ru.VYurkin.RestAPIForSensor.repositories.SensorsRepository;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Optional<Sensor> findOne(String name){
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor){
        sensorsRepository.save(sensor);
    }

}
