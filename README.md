# Shifter
---

## Back-end palvelin
Front-end palvelin löytyy osoitteesta : 

## Vuoronsuunnittelutyökalu kaupan työtehtäviin


Shifter on tarkoitettu kokonaisvaltaiseen työvuorojen hallinnointiin sekä suunnitteluun kaupan alalla.




<details>
<summary> Tietokannan DDL- komennot </summary>

## Employee

```sql
CREATE TABLE `employee` (
`employee_id` bigint NOT NULL AUTO_INCREMENT, 
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `qualification` tinyint(1) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
</br>
```

## Shift

```sql
CREATE TABLE `shift` (
  `shiftid` bigint NOT NULL AUTO_INCREMENT,
  `end_time` time(6) NOT NULL,
  `shift_name` varchar(255) DEFAULT NULL,
  `start_time` time(6) NOT NULL,
  `workstation` varchar(255) DEFAULT NULL,
  `covering_shift_id` bigint DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  `workday_id` bigint DEFAULT NULL,
  `cover_employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`shiftid`),
  KEY `FKjrfmr5opgjhasm3k4fbsbit1` (`covering_shift_id`),
  KEY `FKg9ycreft1sv2jjvkno3dn3fqy` (`employee_id`),
  KEY `FKh8pt2aph03ljh1as32yxj6fhn` (`workday_id`),
  KEY `FKppr81iylo5hdb72wxxvm547gg` (`cover_employee_id`),
  CONSTRAINT `FKg9ycreft1sv2jjvkno3dn3fqy` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `FKh8pt2aph03ljh1as32yxj6fhn` FOREIGN KEY (`workday_id`) REFERENCES `workday` (`id`),
  CONSTRAINT `FKjrfmr5opgjhasm3k4fbsbit1` FOREIGN KEY (`covering_shift_id`) REFERENCES `shift` (`shiftid`),
  CONSTRAINT `FKppr81iylo5hdb72wxxvm547gg` FOREIGN KEY (`cover_employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

### Shifts (DTO)

```sql
CREATE TABLE `shifts` (
  `shift_id` bigint NOT NULL AUTO_INCREMENT,
  `workstation` varchar(255) DEFAULT NULL,
  `shift_name` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  `covering_shift_id` bigint DEFAULT NULL,
  PRIMARY KEY (`shift_id`),
  KEY `covering_shift_id` (`covering_shift_id`),
  KEY `shifts_ibfk_1` (`employee_id`),
  CONSTRAINT `shifts_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE SET NULL,
  CONSTRAINT `shifts_ibfk_2` FOREIGN KEY (`covering_shift_id`) REFERENCES `shifts` (`shift_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```



### Shift_Breaks (DTO)

```sql
CREATE TABLE `shift_breaks` (
  `shift_shiftid` bigint NOT NULL,
  `break_end` time(6) NOT NULL,
  `break_start` time(6) NOT NULL,
  `break_type` varchar(255) DEFAULT NULL,
  `cover_employee_id` bigint DEFAULT NULL,
  `break_cover_employee_employee_id` bigint DEFAULT NULL,
  `cover_employee_employee_id` bigint DEFAULT NULL,
  KEY `FK554k1alyci9osn1890b736fd2` (`shift_shiftid`),
  KEY `FKe8xj1ok6xcoo4jwhxqtgybshc` (`break_cover_employee_employee_id`),
  KEY `FKbex8xbun97bjv2dkf7owj2te7` (`cover_employee_employee_id`),
  CONSTRAINT `FK554k1alyci9osn1890b736fd2` FOREIGN KEY (`shift_shiftid`) REFERENCES `shift` (`shiftid`),
  CONSTRAINT `FKbex8xbun97bjv2dkf7owj2te7` FOREIGN KEY (`cover_employee_employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `FKe8xj1ok6xcoo4jwhxqtgybshc` FOREIGN KEY (`break_cover_employee_employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```




## User

```sql
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```


## Workday

```sql
CREATE TABLE `workday` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

</details>



