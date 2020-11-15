CREATE TABLE IF NOT EXISTS boat (
    id          INTEGER      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(128) NOT NULL,
    version INTEGER NOT NULL,
    PRIMARY     KEY (id)
);


INSERT INTO boat (  version, name, description)
  VALUES (  1, 'boat test', 'a boat description test');
