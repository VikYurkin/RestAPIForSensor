package ru.VYurkin.RestAPIForSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.VYurkin.RestAPIForSensor.models.Measurement;
import ru.VYurkin.RestAPIForSensor.repositories.MeasurementsRepository;
import ru.VYurkin.RestAPIForSensor.repositories.SensorsRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;

    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public int rainyDaysCount(){
        Set<LocalDate> rainyDaysCount =new HashSet<>();
        for(Measurement measurement:measurementsRepository.findAll()){
            if(measurement.getRaining())
                rainyDaysCount.add(measurement.getCreatedAt().toLocalDate());
        }
        return rainyDaysCount.size();
    }

    @Transactional
    public void save(Measurement measurement){
        measurement.setSensor(sensorsRepository.findByName(measurement.getSensor().getName()).orElse(null));
        measurement.setCreatedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }

}
