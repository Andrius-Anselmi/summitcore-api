CREATE TABLE event (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    description TEXT,
    identify  VARCHAR (255) NOT NULL UNIQUE,
    capacity INTEGER NOT NULL ,
    location VARCHAR (255) NOT NULL,
    start_event TIMESTAMP NOT NULL,
    end_event TIMESTAMP NOT NULL,
    organizer VARCHAR (255) NOT NULL,
    type_event VARCHAR (255) NOT NULL

);