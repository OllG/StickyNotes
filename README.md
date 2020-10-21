<b>StickyNotes</b>

Simple Spring Webapp for storing simple notes.

CRUD operations on Notes linked to user account,

Registration of an account with e-mail confirmation
Possibility to reset Password via attached e-mail
User can change e-mail attached to account aswell


Used in this Project:

Spring, Spring Boot, Spring MVC, Spring Data,
Spring Security and H2 database, Thymeleaf
Gradle


<b>Missing</b>

There is missing yaml file 
with properties for mail sender and https protocol.
(don't want passwords on public)
On application.properties file you can find template to fulfiil properties,
so application will be able to send confirmation mails.
I focus on back end development, so I used just simplest visual representation.


Project is quite simple and small, but it delivers fully functioning authorization process.
Users can create account, with password and e-mail, and only this user can access his private notes.
This offers a wide range of possibilities in implementing new features to this app
(fairly eaisly it could be developed as communicator, just by adding messages between users.
Also it could be used as backend server for some kind of online game).

If I will continue on this project I will probably convert it to API that will be contacted via specified client.
