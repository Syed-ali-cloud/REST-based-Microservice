create table roles(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(20) NOT NULL,
   PRIMARY KEY ( id )
);

create table users(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(20) NOT NULL,
   password VARCHAR(20) NOT NULL,
   role INT,
   PRIMARY KEY ( id ),
   FOREIGN KEY (role) REFERENCES roles(id)
);

create table news(
   id INT NOT NULL AUTO_INCREMENT,
   title VARCHAR(100) NOT NULL,
   description VARCHAR(200),
   creationDate date,
   validFrom date,
   validTo date,
   createdBy INT,
   isPublish boolean,
   PRIMARY KEY ( id ),
   FOREIGN KEY (createdBy) REFERENCES users(id)
);

create table news_Pictures(
   newsId INT,
   name VARCHAR(20) NOT NULL,
   imagePath VARCHAR(100) NOT NULL,
   FOREIGN KEY (newsId) REFERENCES news(id)
);

create table news_read(
	newsId INT,
    userId INT,
    status boolean,
    readDate date,
	FOREIGN KEY (newsId) REFERENCES news(id),
    FOREIGN KEY (userId) REFERENCES users(id)
);

