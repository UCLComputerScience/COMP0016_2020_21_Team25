CREATE TABLE service
  (
     service_id INT NOT NULL auto_increment,
     name       VARCHAR(30) NOT NULL,
     category   VARCHAR(20) NOT NULL,
     icon       MEDIUMBLOB,
     PRIMARY KEY (service_id)
  );

CREATE TABLE service_log
  (
     service_id INT NOT NULL,
     log_date   DATE NOT NULL,
     log_time   TIME NOT NULL,
     FOREIGN KEY (service_id) REFERENCES service(service_id)
  );

CREATE TABLE profile_picture
  (
     picture_id TINYINT NOT NULL auto_increment,
     picture    MEDIUMBLOB NOT NULL,
     PRIMARY KEY (picture_id)
  );

CREATE TABLE admin
  (
     username     VARCHAR(32) NOT NULL,
     first_name   VARCHAR(35) NOT NULL,
     last_name    VARCHAR(35) NOT NULL,
     phone_number CHAR(11) NOT NULL,
     email        VARCHAR(320) NOT NULL,
     picture_id   TINYINT,
     PRIMARY KEY (username),
     FOREIGN KEY (picture_id) REFERENCES profile_picture(picture_id)
  );

CREATE TABLE user
  (
     user_id      INT NOT NULL auto_increment,
     first_name   VARCHAR(35) NOT NULL,
     last_name    VARCHAR(35) NOT NULL,
     phone_number CHAR(11) NOT NULL,
     prefix       VARCHAR(5) NOT NULL,
     picture_id   TINYINT,
     PRIMARY KEY (user_id),
     FOREIGN KEY (picture_id) REFERENCES profile_picture(picture_id)
  );

CREATE TABLE admin_circle
 # Stores all users an admin manages
  (
     username VARCHAR(32) NOT NULL,
     user_id  INT NOT NULL,
     PRIMARY KEY (username, user_id),
     FOREIGN KEY (username) REFERENCES admin(username),
     FOREIGN KEY (user_id) REFERENCES user(user_id)
  );

CREATE TABLE user_service
  # Stores all services a user has on their account (assigned by admins)
  (
     service_id INT NOT NULL,
     user_id    INT NOT NULL,
     PRIMARY KEY (service_id, user_id),
     FOREIGN KEY (service_id) REFERENCES service(service_id),
     FOREIGN KEY (user_id) REFERENCES user(user_id)
  );

CREATE TABLE bank_service
  (
     user_id    INT NOT NULL,
     account_no TINYINT(8),
     sort_code  TINYINT(6),
     PRIMARY KEY (user_id),
     FOREIGN KEY (user_id) REFERENCES user(user_id)
  );

CREATE TABLE payment_service # Card details separate to bank details
  (
     user_id     INT NOT NULL,
     card_no     TINYINT(16),
     cvv         TINYINT(3),
     expiry_date DATE, # Store day component as 00
     PRIMARY KEY (user_id),
     FOREIGN KEY (user_id) REFERENCES user(user_id)
  );