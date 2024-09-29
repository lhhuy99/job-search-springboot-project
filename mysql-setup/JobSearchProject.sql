CREATE DATABASE  IF NOT EXISTS `job_search`;
USE `job_search`;

DROP TABLE IF EXISTS `applypost`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `cv`;
DROP TABLE IF EXISTS `recruitment`;
DROP TABLE IF EXISTS `company`;
DROP TABLE IF EXISTS `save_job`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `follow_company`;
DROP TABLE IF EXISTS `category`;

CREATE TABLE `company` (
	`id` int NOT NULL AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `logo` varchar(255) DEFAULT NULL,
    `company_name` varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `status` int DEFAULT NULL,
    primary key (`id`)
);

CREATE TABLE `role` (
	`id` int NOT NULL AUTO_INCREMENT,
    `role_name` varchar(255) DEFAULT NULL,
    primary key (`id`)
);

CREATE TABLE `cv` (
	`id` int NOT NULL AUTO_INCREMENT,
    `file_name` varchar(255) DEFAULT NULL,
    -- `user_id` int DEFAULT NULL,
    primary key (`id`)
    -- foreign key (`user_id`) references `user`(`id`)
);

CREATE TABLE `user` (
	`id` int NOT NULL AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `full_name` varchar(255) DEFAULT NULL,
    `image` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `status` int DEFAULT NULL,
    `role_id` int DEFAULT NULL,
    `cv_id` int DEFAULT NULL,
    `company_id` int DEFAULT NULL,
    primary key (`id`),
    foreign key (`role_id`) references `role`(`id`),
    foreign key (`company_id`) references `company`(`id`),
    foreign key (`cv_id`) references `cv`(`id`)
);

CREATE TABLE `save_job` (
	`id` int NOT NULL AUTO_INCREMENT,
    `recruitment_id` int DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    primary key (`id`)
);

CREATE TABLE `follow_company` (
	`id` int NOT NULL AUTO_INCREMENT,
    `company_id` int DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    primary key (`id`)
);

CREATE TABLE `category` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    /*`number_choose` int DEFAULT NULL,*/
    primary key (`id`)
);

CREATE TABLE `recruitment` (
	`id` int NOT NULL AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,
    `created_at` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `experience` varchar(255) DEFAULT NULL,
    `quantity` int DEFAULT NULL,
    `salary` varchar(255) DEFAULT NULL,
    `status` int DEFAULT NULL,
    `title` varchar(255) DEFAULT NULL,
    `type` varchar(255) DEFAULT NULL,
    `view` int DEFAULT NULL,
    `category_id` int DEFAULT NULL,
    `company_id` int DEFAULT NULL,
    `deadline` varchar(255) DEFAULT NULL,
    primary key (`id`),
    foreign key (`company_id`) references `company`(`id`),
    foreign key (`category_id`) references `category`(`id`)
);

CREATE TABLE `applypost` (
	`id` int NOT NULL AUTO_INCREMENT,
    `created_at` varchar(255) DEFAULT NULL,
    `recruitment_id` int DEFAULT NULL,
    `user_id` int DEFAULT NULL,
    `cv_id` int DEFAULT NULL,
    `status` int DEFAULT NULL,
    `text` varchar(255) DEFAULT NULL,
    primary key (`id`),
    foreign key (`recruitment_id`) references `recruitment`(`id`),
    foreign key (`user_id`) references `user`(`id`),
    foreign key (`cv_id`) references `cv`(`id`)
);

INSERT INTO `company` VALUES
	(1, 'TP Hồ Chí Minh', 'Chào mừng đến công ty A', 'congtyA@gmail.com', '/assets/images/company-1.jpg', 'Công ty A', '0975806740', 1),
    (2, 'Hà Nội', 'Chào mừng đến công ty B', 'congtyB@gmail.com', '/assets/images/company-2.jpg', 'Công ty B', '0326957772', 1),
    (3, 'Đà Nẵng', 'Chào mừng đến công ty C', 'congtyC@gmail.com', '/assets/images/company-3.jpg', 'Công ty C', '0342319091', 1);
    
    
INSERT INTO `category` VALUES
	(1, 'JAVA'),
    (2, 'ASP.NET'),
    (3, 'PHP'),
    (4, 'NODEJS'),
    (5, 'C/C++');

INSERT INTO `recruitment` VALUES
	(1, 'Hà Nội', '08/29/2024', 'Tuyển lập trình viên .NET > 1 năm', '1 năm', 12, '12 triệu', 0, 'Tuyển lập trình viên .NET', 'Full time', 0, 2, 2, '01/05/2025'),
    (2, 'TP Hồ Chí Minh', '08/25/2024', 'Tuyển lập trình viên Java(Fresher)', 'Không cần kinh nghiệm', 15, '9 triệu', 0, 'Tuyển lập trình viên Java(Fresher)', 'Full time', 0, 1, 1, '01/01/2025'),
    (3, 'Đà Nẵng', '08/20/2024', 'Tuyển lập trình viên PHP > 2 năm', '2 năm', 10, '14 triệu', 0, 'Tuyển lập trình viên PHP', 'Full time', 0, 3, 3, '01/01/2025'),
	(4, 'TP Hồ Chí Minh', '08/27/2024', 'Tuyển lập trình viên Node.js', '3 năm', 5, '17 triệu', 0, 'Tuyển lập trình viên Node.js', 'Freelancer', 0, 4, 1, '02/01/2025'),
	(5, 'Hà Nội', '08/30/2024', 'Tuyển lập trình viên C/C++ > 1 năm', '1 năm', 4, '12 triệu', 0, 'Tuyển lập trình viên C/C++', 'Full time', 0, 5, 2, '01/05/2025'),
	(6, 'Hà Nội', '08/30/2024', 'Tuyển lập trình viên Java > 1 năm', '1 năm', 12, '12 triệu', 0, 'Tuyển lập trình viên Java', 'Part time', 0, 1, 2, '01/05/2025'),
	(7, 'Đà Nẵng', '08/20/2024', 'Tuyển lập trình viên Java (Fresher)', 'Không cần kinh nghiệm', 10, '10 triệu', 0, 'Tuyển lập trình viên Java(Fresher)', 'Full time', 0, 1, 3, '01/01/2025'),
    (8, 'Đà Nẵng', '08/20/2024', 'Tuyển lập trình viên C/C++ > 2 năm', '2 năm', 10, '14 triệu', 0, 'Tuyển lập trình viên C/C++', 'Full time', 0, 5, 3, '03/01/2025');

INSERT INTO `role` VALUES
	(1, 'ROLE_EMPLOYER'),
    (2, 'ROLE_JOBSEEKER');
    
INSERT INTO `user` VALUES
	(1, 'TP Hồ Chí Minh', 'description', 'test1@gmail.com', 'test1', null, '$2a$10$qrhsRQilHvfTr0aoHmE6IOqME/6K/tbPwO67QN0/JUY2sTKO8HIS2','1111111111', 1, 1, null, 1),
	(2, 'Hà Nội', 'description', 'test2@gmail.com', 'test2', null, '$2a$10$KM6yGR1MjjUeuLnaygYIX.qKmIEDX2w9YwfssIMdCRqeuG6n.cPkO','2222222222', 1, 1, null, 2),
	(3, 'Đà Nẵng', 'description', 'test3@gmail.com', 'test3', null, '$2a$10$ADE5ZnGevBL2sSnTi7fl..SXJTkT19p5Q9ImkehAzgoLjViwr9eQK','3333333333', 1, 1, null, 3),
    (4, 'TP Hồ Chí Minh', 'description', 'test4@gmail.com', 'test4', null, '$2a$10$BObEEWXlz2D2R0h.D8PA.uZdMr4iSVCaOVMfL.O.TRJSXS3nJKseW','4444444444', 1, 2, null, null),
	(5, 'Hà Nội', 'description', 'test5@gmail.com', 'test5', null, '$2a$10$12W2Qftvrov1jFe/DoVz0OZ/8kszHmvMAndkklJSflwhNhQDgQJCG','5555555555', 1, 2, null, null),
	(6, 'Đà Nẵng', 'description', 'test6@gmail.com', 'test6', null, '$2a$10$OSR3HlynfTycrqcCcJC7a.wFo3BcvNWkxnzkqwRqoXGWUDLwLzZEq','6666666666', 1, 2, null, null);
    