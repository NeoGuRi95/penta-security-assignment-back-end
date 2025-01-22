-- `spring-security-study`.user_history definition

CREATE TABLE `user_history` (
    `history_idx` int NOT NULL AUTO_INCREMENT COMMENT '회원 기록 기본키',
    `action_type` enum('C','D','U') NOT NULL COMMENT '회원 기록 유형',
    `reg_dt` datetime(6) NOT NULL COMMENT '최초 등록 일시',
    `reg_ip` varchar(16) NOT NULL COMMENT '최초 등록 아이피',
    `reg_user_idx` int NOT NULL COMMENT '최초 등록 회원 기본키',
    `url` varchar(255) NOT NULL COMMENT 'URL',
    PRIMARY KEY (`history_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `spring-security-study`.`system_user` definition

CREATE TABLE `system_user` (
    `user_idx` int NOT NULL AUTO_INCREMENT COMMENT '회원 기본키',
    `user_auth` varchar(20) NOT NULL COMMENT '회원 권한',
    `user_id` varchar(30) NOT NULL COMMENT '회원 아이디',
    `user_nm` varchar(100) NOT NULL COMMENT '회원 이름',
    `user_pw` varchar(100) NOT NULL COMMENT '회원 비밀번호',
    PRIMARY KEY (`user_idx`),
    UNIQUE KEY `UKqiqa94nygf4mhu5w11hef2fe8` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- employees.departments definition

CREATE TABLE `departments` (
    `dept_no` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `dept_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `emp_count` int DEFAULT NULL,
    PRIMARY KEY (`dept_no`),
    KEY `ux_deptname` (`dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- employees.employees definition

CREATE TABLE `employees` (
    `emp_no` int NOT NULL,
    `birth_date` date NOT NULL,
    `first_name` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `last_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `gender` enum('M','F') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `hire_date` date NOT NULL,
    PRIMARY KEY (`emp_no`),
    KEY `ix_hiredate` (`hire_date`),
    KEY `ix_gender_birthdate` (`gender`,`birth_date`),
    KEY `ix_firstname` (`first_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci STATS_PERSISTENT=0;

-- employees.dept_emp definition

CREATE TABLE `dept_emp` (
    `emp_no` int NOT NULL,
    `dept_no` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `from_date` date NOT NULL,
    `to_date` date NOT NULL,
    PRIMARY KEY (`dept_no`,`emp_no`),
    KEY `ix_fromdate` (`from_date`),
    KEY `ix_empno_fromdate` (`emp_no`,`from_date`),
    CONSTRAINT `dept_emp_departments_fk` FOREIGN KEY (`dept_no`) REFERENCES `departments` (`dept_no`) ON DELETE CASCADE,
    CONSTRAINT `dept_emp_employees_fk` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- employees.dept_manager definition

CREATE TABLE `dept_manager` (
    `dept_no` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `emp_no` int NOT NULL,
    `from_date` date NOT NULL,
    `to_date` date NOT NULL,
    PRIMARY KEY (`dept_no`,`emp_no`),
    KEY `dept_manager_employees_fk` (`emp_no`),
    CONSTRAINT `dept_manager_departments_fk` FOREIGN KEY (`dept_no`) REFERENCES `departments` (`dept_no`) ON DELETE CASCADE,
    CONSTRAINT `dept_manager_employees_fk` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- employees.employee_docs definition

CREATE TABLE `employee_docs` (
    `doc` json DEFAULT NULL,
    `emp_no` int GENERATED ALWAYS AS (json_unquote(json_extract(`doc`,_utf8mb4'$.emp_no'))) STORED NOT NULL,
    PRIMARY KEY (`emp_no`),
    CONSTRAINT `employee_docs_employees_fk` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- employees.employee_name definition

CREATE TABLE `employee_name` (
    `emp_no` int NOT NULL,
    `first_name` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `last_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`emp_no`),
    FULLTEXT KEY `fx_name` (`first_name`,`last_name`) /*!50100 WITH PARSER `ngram` */ ,
    CONSTRAINT `employee_name_employees_fk` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- employees.salaries definition

CREATE TABLE `salaries` (
    `emp_no` int NOT NULL,
    `salary` int NOT NULL,
    `from_date` date NOT NULL,
    `to_date` date NOT NULL,
    PRIMARY KEY (`emp_no`,`from_date`),
    KEY `ix_salary` (`salary`),
    CONSTRAINT `salaries_employees_fk` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- employees.titles definition

CREATE TABLE `titles` (
    `emp_no` int NOT NULL,
    `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `from_date` date NOT NULL,
    `to_date` date DEFAULT NULL,
    PRIMARY KEY (`emp_no`,`from_date`,`title`),
    KEY `ix_todate` (`to_date`),
    CONSTRAINT `titles_employees_fk` FOREIGN KEY (`emp_no`) REFERENCES `employees` (`emp_no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;