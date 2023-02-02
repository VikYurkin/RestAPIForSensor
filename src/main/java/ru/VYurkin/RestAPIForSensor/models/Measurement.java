package ru.VYurkin.RestAPIForSensor.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Значение температуры не должно быть пустым")
    @Range(min=-100, max=100, message = "Значение температуры должно находиться в диапазоне -100 до 100")
    @Column(name="value")
    private Double value;

    @NotNull(message = "Значение сенсора, регистрирующее дождь, не должно быть пустым")
    @Column(name = "raining")
    private Boolean raining;

    @Column(name = "created_at")
    @NotNull(message = "Ошибка 500, некорректные параметры даты и времени на сервере")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    @NotNull(message = "В измерении не указан сенсор")
    private Sensor sensor;

    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
}
