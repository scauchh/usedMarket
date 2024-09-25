## How to Run UsedMarketVue
```
npm i
npm run dev
```
## Change SQL Address、account and password
**application.yml**
```
    spring:
  main:
    banner-mode: off
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/usedmarket
    //change here
    username: xxxx
    password: xxxx
  servlet:
    multipart:
      max-file-size: 5MB
      max-request-size: 5MB

logging:
  level:
    root: info

mybatis:
  configuration:
    map-underscore-to-camel-case: true

```
    
