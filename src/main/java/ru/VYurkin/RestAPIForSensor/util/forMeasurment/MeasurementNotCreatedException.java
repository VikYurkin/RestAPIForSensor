package ru.VYurkin.RestAPIForSensor.util.forMeasurment;

public class MeasurementNotCreatedException extends RuntimeException{
    public MeasurementNotCreatedException(String msg){
        super(msg);
    }
}
