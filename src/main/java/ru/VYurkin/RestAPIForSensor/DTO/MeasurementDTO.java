package ru.VYurkin.RestAPIForSensor.DTO;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class MeasurementDTO {

    @NotNull(message = "Значение температуры не должно быть пустым")
    @Range(min=-100, max=100, message = "Значение температуры должно находиться в диапазоне -100 до 100")
    private Double value;

    @NotNull(message = "Значение сенсора, регистрирующее дождь, не должно быть пустым")
    private Boolean raining;

    @NotNull(message = "В измерении не указан сенсор")
    private SensorDTO sensor;

    public MeasurementDTO() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

}
