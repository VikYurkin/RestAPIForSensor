package ru.VYurkin.RestAPIForSensor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.VYurkin.RestAPIForSensor.DTO.MeasurementDTO;
import ru.VYurkin.RestAPIForSensor.services.SensorsService;

@Component
public class MeasurementDTOValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementDTOValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        if (measurementDTO.getSensor()!= null) {
            if (sensorsService.findOne(measurementDTO.getSensor().getName()).isEmpty())
                errors.rejectValue("sensor", "", "Сенсор с таким именем не зарегистрирован");
        }
    }
}
