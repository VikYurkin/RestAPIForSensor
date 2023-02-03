package ru.VYurkin.RestAPIForSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.VYurkin.RestAPIForSensor.DTO.MeasurementDTO;
import ru.VYurkin.RestAPIForSensor.DTO.MeasurementsDTO;
import ru.VYurkin.RestAPIForSensor.models.Measurement;
import ru.VYurkin.RestAPIForSensor.services.MeasurementsService;
import ru.VYurkin.RestAPIForSensor.util.CustomErrorResponse;
import ru.VYurkin.RestAPIForSensor.util.CustomNotCreatedException;
import ru.VYurkin.RestAPIForSensor.util.MeasurementDTOValidator;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;

    private final MeasurementDTOValidator measurementDTOValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementDTOValidator measurementDTOValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementDTOValidator = measurementDTOValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public MeasurementsDTO getMeasurement(){
       return new MeasurementsDTO(measurementsService.findAll().stream().map(this::convertToMeasurementDTO).toList());
    }

    @GetMapping("/rainyDaysCount")
    public int rainyDaysCount(){
        return measurementsService.rainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        measurementDTOValidator.validate(measurementDTO, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors =  bindingResult.getFieldErrors();
            for(FieldError error:errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CustomNotCreatedException(errorMsg.toString());
        }
        measurementsService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(CustomNotCreatedException e){
        CustomErrorResponse response = new CustomErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
