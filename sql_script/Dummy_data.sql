# Please follow sequence while execute queries..

# 1- add users roles-> important
Insert into roles (name) values("admin");
Insert into roles (name) values("publisher");
Insert into roles (name) values("reader");

# 2- add admin data manually -> admin create user and assign role to user ....important
Insert into users (name,password,role) values("admin","admin",1);


# 3- Dummy data for users or you can create it by your own
Insert into users (name,password,role) values("john","john",2);
Insert into users (name,password,role) values("kevin","kevin",2);
Insert into users (name,password,role) values("ali","ali",3);
Insert into users (name,password,role) values("ibrahim","ibrahim",3);

# 4- Dummy data for news or you can create it by your own
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("Titanic Sinks Four Hours After Hitting Iceberg", "This was one of the few accurate headlines printed on the day following the sinking of the Titanic.","1912-04-16","1912-04-16","1912-04-20",1,true);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("Greatest Crash in Wall Street's History", "The Wall Street Crash of 1929, fuelled by uncertainty following an artificial share price boom, was the worst in U.S. history.","1929-10-25","1929-10-25","1929-10-30",2,true);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("Hitler Dead", "On 2nd May 1945, The News Chronicle, which later became the Daily Mail, published this bold headline. At the time, nobody could be sure if this news was true.","1945-05-02","1945-05-02","1945-05-07",2,true);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("VE Day- It's All Over", "This headline appeared on the day World War II Allies accepted Nazi Germany's surrender. It marked the end of the War and Adolf Hitler's Third Reich","1945-05-08","1945-05-08","1945-05-16",1,true);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("Assassin Kills Kennedy: Lyndon Johnson Sworn In", "John F. Kennedy, the 35th President of the United States, was assassinated in Dallas, Texas.","1963-11-22","1963-11-22","1963-11-27",3,true);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("Martin King Shot To Death: Gunned Down in Memphis", "This shocking headline was printed the day after Martin Luther King, Jr. was shot and killed on the second-floor lobby of the Lorraine Motel.","1968-04-05","1968-05-04","1968-04-15",1,false);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("The First Footstep", "Neil Armstrong became the first man to step foot on the moon. As he touched the ground he famously declared: That's one small step for man, one giant leap for mankind.","1969-07-21","1969-07-21","1969-07-28",2,false);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("Nixon Resigns", "President Richard Nixon, fearing impeachment following the Watergate scandal, became the only President to ever resign from office. Gerald Ford later pardoned him, but he was never truly forgiven","1974-08-09","1974-08-09","1974-08-15",2,false);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("King Elvis Dead", "On 16th August 1977, The King of Rock n Roll was found dead on his bathroom floor. As the subheading in the accompanying article reads: He was 42 and alone.","1977-08-17","1977-08-17","1977-08-25",1,false);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("Beatle John Lennon Slain", "At 10.49pm, on the day prior to this headline running, John Lennon was shot in the back four times by Mark David Chapman, a fan who had been stalking him for 3 months","1980-12-19","1980-12-19","1980-12-25",3,false);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("Diana Dead", "Princess Diana died after her Mercedes Benz S280 crashed into a pillar in the Pont de l'Alma tunnel, Paris. She was just 36. Her friend Dodi Al-Fayed was also killed in the collision.","1997-08-31","1997-08-31","1997-09-05",1,false);
Insert into news (title,description,creationDate,validFrom,validTo,createdBy,isPublish) values("War on America", "On 12th September 2001, there was, of course, only one story dominating the headlines. On the previous day, terrorists had hijacked four commercial passenger jet airliners.","2001-09-12","2001-09-12","2001-09-17",2,false);

# 5- dummy data for pictures or you can create it by your own
Insert into news_pictures (newsId,name,imagePath) values(1,"Titanic Sinks", "/images/titanic.jpg");
Insert into news_pictures (newsId,name,imagePath) values(2,"Wall Street", "/images/street.jpg");
Insert into news_pictures (newsId,name,imagePath) values(3,"Hitler Dead", "/images/hitler.jpg");
Insert into news_pictures (newsId,name,imagePath) values(3,"News Chronicle", "/images/Chronicle.jpg");
Insert into news_pictures (newsId,name,imagePath) values(4,"VE Day", "/images/VE.jpg");
Insert into news_pictures (newsId,name,imagePath) values(4,"World War II", "/images/war.jpg");
Insert into news_pictures (newsId,name,imagePath) values(5,"Assassin Kills", "/images/Kills.jpg");
Insert into news_pictures (newsId,name,imagePath) values(6,"Martin King", "/images/martin.jpg");
Insert into news_pictures (newsId,name,imagePath) values(6,"Shot To Death", "/images/death.jpg");
Insert into news_pictures (newsId,name,imagePath) values(7,"The First Footstep", "/images/footstep.jpg");
Insert into news_pictures (newsId,name,imagePath) values(8,"Nixon Resigns", "/images/resigns.jpg");
Insert into news_pictures (newsId,name,imagePath) values(10,"Diana Dead", "/images/diana.jpg");
Insert into news_pictures (newsId,name,imagePath) values(12,"War on America", "/images/america.jpg");

# 6- dummy data for read news or you can set read status by your own
insert into news_read(newsId,userId,status,readDate) values(1,1,1,"2022-10-27");
insert into news_read(newsId,userId,status,readDate) values(1,2,1,"2022-10-27");
insert into news_read(newsId,userId,status,readDate) values(1,4,1,"2022-10-27");
insert into news_read(newsId,userId,status,readDate) values(6,1,1,"2022-10-27");
insert into news_read(newsId,userId,status,readDate) values(7,1,1,"2022-10-27");
insert into news_read(newsId,userId,status,readDate) values(7,2,1,"2022-10-27");
