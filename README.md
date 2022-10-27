# REST-based-Microservice
Develop a REST-based microservice called NewsService.

1- Configure Apache server in eclipse.

2- Configure Mysql database. (Please change username and password)

    - Tables and test data are exists in sql_script folder.
    
    - News Json sample data exists in json_sample_data.txt. (Use this while create or update News) 
    
3- Endpoints: 

    - /login/{username}/{password}                    (login user)
    
    - /register/{username}/{password}/{repassword}    (Register new user)
    
    - /roles                                          (Get all roles)
    
    - /createRole/{userId}/{newRole}                  (Create role only by admin)
    
    - /assignRole/{userId}/{targetUserId}/{roleId}    (Assign role only by admin)
    
    - /News/{userId}                                  (Get News based on user role)
    
    - /createNews/{userId}                            (Create news by user (Admin ,Publisher))
    
    - /updateNews/{userId}                            (Update news by user (Admin ,Publisher))
    
    - /deleteNews/{userId}/{newsId}                   (Delete news by user (Admin ,Publisher))
    
    - /readNews                                       (get All Read News)
    
    - /publishNews/{userId}/{newsId}                  (Publish news by user (Admin ,Publisher))
    
    - /setReadStatus/{userId}/{newsId}                (Set read status)
    
    - /pictures/{userId}/{newsId}                     (Get pictures)
    
    - /unreadNews/{userId}/{roleId}                   (GET news for a single role-accountID combination)
