package ru.VYurkin.RestAPIForSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.VYurkin.RestAPIForSensor.DTO.SensorDTO;
import ru.VYurkin.RestAPIForSensor.models.Sensor;
import ru.VYurkin.RestAPIForSensor.services.SensorsService;
import ru.VYurkin.RestAPIForSensor.util.CastomErrorResponse;
import ru.VYurkin.RestAPIForSensor.util.forSensor.SensorDTOValidator;
import ru.VYurkin.RestAPIForSensor.util.forSensor.SensorNotCreatedException;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final SensorDTOValidator sensorDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorDTOValidator sensorDTOValidator, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.sensorDTOValidator = sensorDTOValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        sensorDTOValidator.validate(sensorDTO, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors =  bindingResult.getFieldErrors();
            for(FieldError error:errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorsService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<CastomErrorResponse> handleException(SensorNotCreatedException e){
        CastomErrorResponse response = new CastomErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}
