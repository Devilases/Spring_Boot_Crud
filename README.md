# aston-internship

Запустить приложение:

через tomcat 10 в IntelliJ IDEA

Примечание: в connection.properties должно быть connection.url=jdbc:postgresql://localhost:5432/postgres?currentSchema=task3

Выполнить следующую команду:
mvn clean install
Запустить Docker.

Выполнить команду в терминале:

docker run -p 5432:5432 --name postgres-db-Aston -e POSTGRES_USER=jira -e POSTGRES_PASSWORD=JiraRush -e POSTGRES_DB=postgres -e PGDATA=/var/lib/postgresql/data/pgdata -v d:\docker\aston:/var/lib/postgresql/data -d postgres:16.0

Инициализировать БД с помощью скрипта data.sql.

Запустить Postman;

Путь к ресурсу
http://localhost:8080/



Запросы в постмане:

Lesson:

GET http://localhost:8080/less/all - получить всe лекции  

GET http://localhost:8080/less/{id} - получить лекцию с {Id}

POST http://localhost:8080/less - создать новую лекцию

{
"name": "Физика",
"teacher": {
        "id": 4
        }     
}
PUT http://localhost:8080/less - изменить имя работника

{
"id": 10,
"name": "Математика",
"teacher": {
    "id": 4,
    "name": "Программист DB"
    } ,
"studentList": []
}
DELETE http://localhost:8080/less/{id} - удалить лекцию с {Id}

Teacher:

GET http://localhost:8080/teacher/all - получить всех преподавателей

GET http://localhost:8080/teacher/{Id} - получить учителя с {Id}

POST http://localhost:8080/teacher - создать нового преподавателя

{
"name": "New teacher name"
}
PUT http://localhost:8080/teacher - изменить преподавателя

{
"id": 7,
"name": "Василий"
}
DELETE http://localhost:8080/teacher/{Id} - удалить преподавателя с {Id}

Student:

GET http://localhost:8080/stud/all - получить все номера project

GET http://localhost:8080/stud/{Id} - получить project {projectId}

POST http://localhost:8080/stud - сохранить в базу новый project

{
"name": "new stud"
}
DELETE http://localhost:8080/stud/{Id} - удалить project {projectId}

PUT http://localhost:8080/project - изменить наименование project

{
"id": 6,
"name": "Edit stud"
}
DELETE http://localhost:8080/stud/{studId}/deleteLesson/{LessonId} - удалить студента из лекции

http://localhost:8080/stud/4/deleteLesson/12
PUT http://localhost:8080/stud/{studId}/addLesson/{lessonId} - добавить работника в проект
