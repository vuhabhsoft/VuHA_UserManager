Create Database project;

CREATE TABLE `role` (
  `rolename` varchar(16) NOT NULL,
  `roledescription` varchar(30) DEFAULT NULL,
  `createroleuser` bit(1) DEFAULT NULL,
  `updateuser` bit(1) DEFAULT NULL,
  `deleteuser` bit(1) DEFAULT NULL,
  PRIMARY KEY (`rolename`),
  UNIQUE KEY `rolename_UNIQUE` (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(200) NOT NULL,
  `fullname` varchar(30) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `interest` varchar(30) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `other` varchar(30) DEFAULT NULL,
  `role` varchar(16) DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `rolel_idx` (`role`),
  CONSTRAINT `roleName` FOREIGN KEY (`role`) REFERENCES `role` (`rolename`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci



insert into role value ("admin", "admin" , 1, 1, 1)

insert into role value ("user", "user" , 0, 0, 0)

insert into user value(1, "admin", "$2a$10$67ivygm58bRl90K5YCVm2.13BHpTxIv3Up/cDg7a6XyVTKNgUBgQW", "Hồ Anh Vũ", 21, "none" , "M", "none", "admin") (password:1234)
