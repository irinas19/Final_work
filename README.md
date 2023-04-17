# Final_work


## 1. Задание БД:
    7. В подключенном MySQL репозитории создать базу данных “Друзья человека”  
CREATE DATABASE mans_friends;

    8. Создать таблицы с иерархией из диаграммы в БД  
USE mans_friends;

CREATE TABLE animals
(
	id INT AUTO_INCREMENT PRIMARY KEY,  
	animal_type VARCHAR(30)  
);  

INSERT INTO animals (animal_type)  
VALUES ('Домашние животные'), ('Вьючные животные');  

CREATE TABLE pets  
(  
	id INT AUTO_INCREMENT PRIMARY KEY,     
	animal_kind VARCHAR(30),  
	animal_type_id INT DEFAULT 1,  
	FOREIGN KEY (animal_type_id) REFERENCES animals (id) ON DELETE CASCADE ON UPDATE CASCADE  
);  

INSERT INTO pets (animal_kind)  
VALUES ('Собаки'), ('Кошки'), ('Хомяки');  
  
CREATE TABLE pack_animals  
(  
	id INT AUTO_INCREMENT PRIMARY KEY,  
	animal_kind VARCHAR(30),  
	animal_type_id INT DEFAULT 2,  
	FOREIGN KEY (animal_type_id) REFERENCES animals (id) ON DELETE CASCADE ON UPDATE CASCADE  
);  

INSERT INTO pack_animals (animal_kind)   
VALUES ('Лошади'), ('Верблюды'), ('Ослы');  
  

    9. Заполнить низкоуровневые таблицы именами(животных), командами которые они выполняют и датами рождения.
CREATE TABLE dogs   
(         
    id INT AUTO_INCREMENT PRIMARY KEY,   
    name VARCHAR(30),   
    commands VARCHAR(100),  
    birthday DATE,  
    animal_kind_id INT DEFAULT 1,  
    Foreign KEY (animal_kind_id) REFERENCES pets (id) ON DELETE CASCADE ON UPDATE CASCADE  
);  
INSERT INTO dogs (name, commands, birthday)  
VALUES ('Тузик', 'лежать' фас, '2011-05-19'),  
('Ричи', 'дай лапу, сидеть', '2006-05-02'),  
('Боб', 'лежать, голос', '2022-04-25');  
  
CREATE TABLE cats   
(          
    id INT AUTO_INCREMENT PRIMARY KEY,   
    name VARCHAR(30),   
    commands VARCHAR(100),  
    birthday DATE,  
    animal_kind_id INT DEFAULT 2,  
    Foreign KEY (animal_kind_id) REFERENCES pets (id) ON DELETE CASCADE ON UPDATE CASCADE  
);  
INSERT INTO cats (name, commands, birthday)  
VALUES ('Мурзик', 'лежать', '2023-03-14'),  
('Дымок', 'дай лапу', '2017-04-09'),  
('Грей', 'ко мне', '2013-02-21');  
  
CREATE TABLE hamsters   
(         
    id INT AUTO_INCREMENT PRIMARY KEY,   
    name VARCHAR(30),   
    commands VARCHAR(100),   
    birthday DATE,  
    animal_kind_id INT DEFAULT 3,  
    Foreign KEY (animal_kind_id) REFERENCES pets (id) ON DELETE CASCADE ON UPDATE CASCADE  
);  
INSERT INTO hamsters (name, commands, birthday)  
VALUES ('Хома', 'стоять', '2011-11-11'),  
('Умка', 'гулять', '2015-02-12'),  
('Черныш', 'кушать', '2009-04-21');  
  
CREATE TABLE horses   
(         
    id INT AUTO_INCREMENT PRIMARY KEY,   
    name VARCHAR(30),   
    commands VARCHAR(100),  
    birthday DATE,  
    animal_kind_id INT DEFAULT 1,  
    Foreign KEY (animal_kind_id) REFERENCES pack_animals (id) ON DELETE CASCADE ON UPDATE CASCADE  
);   
INSERT INTO horses (name, commands, birthday)  
VALUES ('Бель', 'рысь', '2021-03-10'),  
('Рик', 'шагом', '2016-03-15'),  
('Кондор', 'галоп', '2012-02-27');  
  
CREATE TABLE camels   
(         
    id INT AUTO_INCREMENT PRIMARY KEY,   
    name VARCHAR(30),   
    commands VARCHAR(100),  
    birthday DATE,   
    animal_kind_id INT DEFAULT 2,  
    Foreign KEY (animal_kind_id) REFERENCES pack_animals (id) ON DELETE CASCADE ON UPDATE CASCADE  
);  
INSERT INTO camels (name, commands, birthday)  
VALUES ('Кай', 'дурр', '2011-02-15'),  
('Рома', 'дурр', '2017-12-05'),  
('Джои', 'гит', '2019-07-11');  
  
CREATE TABLE donkeys   
(         
    id INT AUTO_INCREMENT PRIMARY KEY,   
    name VARCHAR(30),   
    commands VARCHAR(100),  
    birthday DATE,   
    animal_kind_id INT DEFAULT 3,   
    Foreign KEY (animal_kind_id) REFERENCES pack_animals (id) ON DELETE CASCADE ON UPDATE CASCADE  
);  
INSERT INTO donkeys (name, commands, birthday)  
VALUES ('Лир', 'стоять', '2010-03-19'),  
('Жак', 'идти', '2014-10-25'),  
('Изабель', 'вперед', '2020-07-24');   
   
    10. Удалив из таблицы верблюдов, т.к. верблюдов решили перевезти в другой питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу.
DELETE FROM camels;
   
CREATE TABLE horses_and_donkeys SELECT * FROM horses  
UNION SELECT * FROM donkeys;  
    
    11. Создать новую таблицу “молодые животные” в которую попадут все животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью до месяца подсчитать возраст животных в новой таблице  
CREATE TEMPORARY TABLE all_animals  
SELECT * FROM dogs  
UNION SELECT * FROM cats  
UNION SELECT * FROM hamsters  
UNION SELECT * FROM horses  
UNION SELECT * FROM camels  
UNION SELECT * FROM donkeys;  
   
CREATE TABLE young_animals  
SELECT name, commands, birthday, animal_kind_id, TIMESTAMPDIFF(MONTH, birthday, CURDATE()) AS age_in_month  
FROM all_animals   
WHERE birthday BETWEEN ADDDATE(CURDATE(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR);  
  

    12. Объединить все таблицы в одну, при этом сохраняя поля, указывающие на прошлую принадлежность к старым таблицам.
SELECT dogs.name, dogs.commands, dogs.birthday, pets.animal_kind, animals.animal_type  
FROM dogs  
LEFT JOIN pets ON pets.id = dogs.animal_kind_id  
LEFT JOIN animals ON animals.id=pets.animal_type_id  
UNION  
SELECT cats.name, cats.commands, cats.birthday, pets.animal_kind, animals.animal_type  
FROM cats  
LEFT JOIN pets ON pets.id = cats.animal_kind_id  
LEFT JOIN animals ON animals.id=pets.animal_type_id  
UNION   
SELECT hamsters.name, hamsters.commands, hamsters.birthday, pets.animal_kind, animals.animal_type   
FROM hamsters   
LEFT JOIN pets ON pets.id = hamsters.animal_kind_id  
LEFT JOIN animals ON animals.id=pets.animal_type_id  
UNION   
SELECT horses.name, horses.commands, horses.birthday, pack_animals.animal_kind, animals.animal_type  
FROM horses  
LEFT JOIN pack_animals ON pack_animals.id = horses.animal_kind_id  
LEFT JOIN animals ON animals.id=pack_animals.animal_type_id  
UNION  
SELECT camels.name, camels.commands, camels.birthday, pack_animals.animal_kind, animals.animal_type  
FROM camels  
LEFT JOIN pack_animals ON pack_animals.id = camels.animal_kind_id  
LEFT JOIN animals ON animals.id=pack_animals.animal_type_id  
UNION  
SELECT donkeys.name, donkeys.commands, donkeys.birthday, pack_animals.animal_kind, animals.animal_type  
FROM donkeys  
LEFT JOIN pack_animals ON pack_animals.id = donkeys.animal_kind_id  
LEFT JOIN animals ON animals.id=pack_animals.animal_type_id;   
