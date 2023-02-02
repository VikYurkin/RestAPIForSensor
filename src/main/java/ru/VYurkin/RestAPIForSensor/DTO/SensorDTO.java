package ru.VYurkin.RestAPIForSensor.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotEmpty(message = "Название сенсора не должно быть пустым")
    @Size(min=3, max=30, message = "Название сенсора должно содержать не менее 3 и не более 30 символов" )
    private String name;

    public SensorDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
