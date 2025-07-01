# SimplePayments - Простые платежи
## Описание

Простые платежи — простой способ принимать онлайн-платежи без лишней сложности.

## Stack

* Kotlin
* Spring
  - Boot
  - Security
  - Cloud
  - Data JPA
  - AOP
* Kafka
* Redis
* Amazon S3 (Cloud.ru) 
* PostgreSQL
* ElasticSearch + Grafana

## Возможности и особенности

* Общение между микросервисами:

  Реализован выбор транспорта (HTTP, KAFKA) между микросервисами через конфигурационной файл при помощи свойства:

  ```yaml
  transport: 
      type: HTTP
  ```
  или
  
  ```yaml
  transport: 
      type: KAFKA
  ```
  
  В случае отказа HTTP или Kafka при отправке уведомлений происходит fallback на альтернативный транспорт
  Взаимодействие по HTTP происходит через OpenFeign
  
* Авторизация и аутентификация с использованием Spring Security и JWT
* Отправка уведомлений на email
* Хранение сканов паспортов пользователей и чеков переводов в S3-хранилище (Cloud.ru)
* Хранение кодов верификации в Redis с автоочисткой
* Логирование с помощью AOP и ElasticSearch + Grafana

## Grafana
<img width="1509" alt="image" src="https://github.com/user-attachments/assets/69f349eb-fffa-4fbe-b3ad-f4b0c9b3ba99" />
<img width="1510" alt="image" src="https://github.com/user-attachments/assets/6dc3a356-4fd4-4aea-9f81-0b1b2b3f12b1" />


