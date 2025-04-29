1. ## Yunmo Poetry Garden Function Description Document

   ## I. Function Introduction

   ### 1. Preface

   Yunmo Poetry Garden is a web application that integrates poetry and poet search, poetry creation, sharing, and communication. This application is dedicated to inheriting and promoting the excellent traditional Chinese culture and provides a convenient and practical poetry creation platform for poetry enthusiasts. The following are the main functions of Yunmo Poetry Garden.

   ### 2. Function Modules

   #### 2.1 User Registration and Login

   - **Register an Account**: Users can register an account via mobile phone, email, etc.
   - **Login to Account**: Users can log in via the registered mobile phone, email, or third-party account.

   #### 2.2 Home Page Recommendations

   - **Poetry Recommendations**: Recommend popular and classic poems based on user interests and browsing history.
   - **Hot Topics**: Show current hot poetry topics for user discussion.

   #### 2.3 Poetry Creation

   - **Online Creation**: Provide a variety of poetry templates for users to create poetry online.
   - **Draft Saving**: Autosave drafts to prevent accidental loss.
   - **Poetry Publication**: Users can publish their poetry works and share them with other users.

   #### 2.4 Poetry Library

   - **Poetry Classification**: Categorized by dynasty, author, theme, etc., for easy user search.
   - **Poetry Search**: Support keyword search for quick location of favorite poetry.
   - **Poetry Collection**: Users can add favorite poetry to their bookmarks for easy reading anytime.

   #### 2.5 Interactive Communication

   - **Comment and Like**: Users can comment on and like poetry works for interactive communication.
   - **Follow Authors**: Follow favorite authors to get their latest works first.
   - **Private Messaging**: Support one-on-one communication between users via private messages.

   #### 2.6 Activities and Events

   - **Online Competitions**: Regular poetry creation contests are held to encourage user participation.
   - **Offline Activities**: Organize poetry lectures, salons, and other events to promote user interaction.

   #### 2.7 Personal Center

   - **Personal Information**: Users can view and modify personal information such as avatars and signatures.
   - **My Works**: Display the user’s published poetry works for easy management.
   - **Browsing History**: Record the poetry that users have browsed for easy review.

   ### 3. Special Features

   #### 3.1 Poetry Analysis

   - **Annotation Analysis**: Provide annotation analysis for obscure words in poetry to aid user understanding.

   #### 3.2 Poetry Challenge

   - **Poetry Relay**: Users can participate in a poetry relay game to challenge their own poetry skills.

   ### 4. Summary

   Yunmo Poetry Garden, as a poetry application, provides a convenient and practical platform for poetry enthusiasts to create, share, and communicate. By continuously optimizing and improving our features, we are committed to letting more people understand and appreciate the excellent traditional Chinese culture. Thank you for your support and attention to Yunmo Poetry Garden!

   ## II. Database Design

   #### 1. User Table (users)

   | Field    | Type    | Description  |
   | :------- | :------ | :----------- |
   | user_id  | int     | User ID      |
   | username | varchar | Username     |
   | password | varchar | Password     |
   | nickname | varchar | Nickname     |
   | type_id  | int     | User type ID |
   | avatar   | varchar | User avatar  |
   
   #### 2. Module Table (models)

   | Field         | Type         | Description                            |
   | :------------ | :----------- | :------------------------------------- |
   | model_id      | int          | Module ID (PK, AI)                     |
   | name          | varchar(255) | Module name                            |
   | url           | varchar(255) | Connection URL                         |
   | parent_models | int          | Parent module ID (0 for parent module) |
   
   #### 3. User Type Table (type)

   | Field     | Type         | Description      |
   | :-------- | :----------- | :--------------- |
   | type_id   | int          | Type ID (PK, AI) |
   | type_name | varchar(255) | Type name        |
   | model_id  | int          | Module ID        |
   
   #### 4. Comment Table (comment)

   | Field      | Type         | Description                      |
   | :--------- | :----------- | :------------------------------- |
   | comment_id | int          | Comment ID (PK, AI)              |
   | content    | varchar(255) | Comment content                  |
   | time       | datetime     | Publication time                 |
   | blog_id    | int          | Post ID (the comment belongs to) |
   | user_id    | int          | User ID (who published it)       |
