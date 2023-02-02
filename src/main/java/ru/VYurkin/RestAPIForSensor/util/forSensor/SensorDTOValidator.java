package ru.VYurkin.RestAPIForSensor.util.forSensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.VYurkin.RestAPIForSensor.DTO.SensorDTO;
import ru.VYurkin.RestAPIForSensor.services.SensorsService;

@Component
public class SensorDTOValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorDTOValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        if(sensorsService.findOne(sensorDTO.getName()).isPresent())
            errors.rejectValue("name", "", "Сенсор с таким именем уже зарегистрирован");
    }
}
