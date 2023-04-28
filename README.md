# RestAPIForSensor
Rest API  for storing, recording and transferring information for sensor.

Technology: Maven, Spring REST, Spring Core, Hibernate and Spring Data JPA PostgreSQL.

REST API service that receives data fromиsensor in JSON format,
transfers and writes to the database, determines and gives the user the number of rainy days.

A ClientForJavaAPISensor was implemented to test the RestAPIForSensor.
Для запуска через docker compose:
1) Создать файл  docker compose.yml:


```sh
version: '3.7'

services:
  sensor:
    depends_on:
      - postgres
    image: vyurkin/sensor
    environment:
      DB_USERNAME: postgres
      DB_PASSWORD: 9876543219
      DB_URL: jdbc:postgresql://postgres:5432/sensor
    restart: always
    ports:
      - 8083:8070

  postgres:
    image: postgres:15.2
    restart: always
    environment:
      POSTGRES_PASSWORD: 9876543219
      POSTGRES_USER: postgres
      POSTGRES_DB: sensor
    ports:
      - 5433:5432
```

2) Запустить сборку образа командой:

 docker compose -p "project-sensor" -f "docker-compose.yml" up -d.
 
если образы vyurkin/sensor и postgres:15.2 отсутствуют, они будут скачаны с DockerHub.

API:

1) Request: POST  http://localhost:8083/sensors/registration
Exaple request: JSON: { "name": "sensor" }
Response: "OK"

2) Request: POST  http://localhost:8083/measurements/add
Exaple request: JSON: {
                        "value": 24.7,
                        "raining":false,
                        "sensor": {
                                    "name":"sensor"
                                  }
                       }
Response: "OK"

3) Request: GET http://localhost:8083/measurements
Exaple response: JSON: {
                          "measurements": [
                                            {
                                              "value": 25.2,
                                              "raining": true,
                                              "sensor": {
                                                          "name": "sensor"
                                                        }
                                             },
                                             {
                                               "value": 24.7,
                                               "raining": false,
                                                "sensor": {
                                                             "name": "sensor"
                                                          }
                                              }
                                            ]
                          }

4) Request: GET http://localhost:8083/measurements/rainyDaysCount
Exaple response: "1"

Apps generated and saved in DB unique series for get request and continues series for get request.

Функционал:
1) По запросу 1) происходит регистрация сенсора.
2) По запросу 2) можно записать данные с сенсора (температуру, был дождь или нет), если такого сенсора не зарегистрировано, то данные записаны не будут.
3) По запросу 3) можно получить весь список записей по всем сенсорам.
4) По запросу 4) возвращается количество дождливых дней в соответствии с записями по всем сенсорам.
