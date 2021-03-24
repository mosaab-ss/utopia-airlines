# Utopia Airlines
Utopia Airlines is a mock project for an imaginary airlines company.  

## Modifications to DB schema
- `airplane_type`
```mysql
ALTER TABLE `utopia`.`airplane_type` 
ADD COLUMN `business_class` INT UNSIGNED NOT NULL AFTER `max_capacity`,
ADD COLUMN `first_class` INT UNSIGNED NOT NULL AFTER `business_class`;
```
- `flight`
```mysql
ALTER TABLE `utopia`.`flight`
ADD COLUMN `arrival_time` DATETIME NOT NULL AFTER `departure_time`,
ADD COLUMN `reserved_business` INT UNSIGNED NOT NULL AFTER `reserved_seats`,
ADD COLUMN `reserved_first` INT UNSIGNED NOT NULL AFTER `reserved_business`,
ADD COLUMN `business_price` FLOAT NOT NULL AFTER `seat_price`,
ADD COLUMN `first_price` FLOAT NOT NULL AFTER `business_price`;
```
- Changing `flight.id` to auto_increment
```mysql
SET FOREIGN_KEY_CHECKS = 0;
ALTER TABLE `utopia`.`flight`
    CHANGE COLUMN `id` `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ;
SET FOREIGN_KEY_CHECKS = 1;
```
- `booking`
```mysql
ALTER TABLE `utopia`.`booking` 
ADD COLUMN `seat_class` INT UNSIGNED NOT NULL AFTER `confirmation_code`;
```

## Storing Password Hashes
Passwords are stored as SHA-512 hashes using `SHA2('mypassword', 512)` in `INSERT`/`UPDATE` queries.