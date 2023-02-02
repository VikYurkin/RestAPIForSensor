package ru.VYurkin.RestAPIForSensor.DTO;

import java.util.List;

public class MeasurementsDTO {
    private List<MeasurementDTO> measurements;

    public MeasurementsDTO(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
