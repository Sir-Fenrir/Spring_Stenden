CREATE TABLE HOUSES
(
    ID           int          NOT NULL AUTO_INCREMENT,
    NR_OF_FLOORS NUMBER,
    NR_OF_ROOMS  NUMBER,
    STREET       varchar(255) NOT NULL,
    CITY         varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);