CREATE TABLE HOUSES
(
    ID           int          NOT NULL AUTO_INCREMENT,
    NR_OF_FLOORS NUMBER,
    NR_OF_ROOMS  NUMBER,
    STREET       varchar(255) NOT NULL,
    CITY         varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE USERS
(
    ID       int         NOT NULL AUTO_INCREMENT,
    USERNAME varchar(30) NOT NULL UNIQUE,
    PASSWORD varchar(64) NOT NULL,
    ENABLED  bool        NOT NULL,
    ROLE     varchar(20) NOT NULL
)