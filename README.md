# Final_work


## 1. Задание БД:

    6. Нарисовать диаграмму, в которой есть класс родительский класс, домашние животные и вьючные животные, в составы которых в случае домашних животных войдут классы: собаки, кошки, хомяки, а в класс вьючные животные войдут: Лошади, верблюды и ослы).  
    
    ![diagram](https://user-images.githubusercontent.com/101984872/232582983-6108295b-f1f0-4e19-95c2-5b21d6daa5ee.png)

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



irina@irina-linux:~$ cat > 'Домашние животные'
собаки
кошки
хомяки ^C
irina@irina-linux:~$ cat 'Домашние животные'
собаки
кошки
хомяки irina@irina-linux:~$ cat > 'Вьючие животные'
лошади
верблюды
ослы
^C
irina@irina-linux:~$ cat  'Вьючие животные'
лошади
верблюды
ослы
irina@irina-linux:~$ cat 'Вьючие животные' 'Домашние животные' > 'Животные'
irina@irina-linux:~$ cat 'Животные'
лошади
верблюды
ослы
собаки
кошки
хомяки irina@irina-linux:~$ mv 'Животные' 'Друзья человека'
irina@irina-linux:~$
irina@irina-linux:~$ mkdir animals
irina@irina-linux:~$ mv 'Друзья человека' animals/'Друзья человека'
irina@irina-linux:~$ ls -la animals
итого 12
drwxrwxr-x  2 irina irina 4096 кра 18 19:04  .
drwxr-x--- 26 irina irina 4096 кра 18 19:04  ..
-rw-rw-r--  1 irina irina   76 кра 18 19:01 'Друзья человека'
irina@irina-linux:~$
irina@irina-linux:~$ sudo apt-key adv --keyserver pgp.mit.edu --recv-keys 3A79BD29
Warning: apt-key is deprecated. Manage keyring files in trusted.gpg.d instead (see apt-key(8)).
Executing: /tmp/apt-key-gpghome.ly0qIQtzsT/gpg.1.sh --keyserver pgp.mit.edu --recv-keys 3A79BD29

^C
gpg: signal 2 caught ... exiting
irina@irina-linux:~$ sudo echo 'deb http://repo.mysql.com/apt/ubuntu/ bionic mysql-8.0' > /etc/apt/sources
-bash: /etc/apt/sources: Отказано в доступе
irina@irina-linux:~$ sudo apt update
Сущ:1 http://by.archive.ubuntu.com/ubuntu jammy InRelease
Пол:2 http://by.archive.ubuntu.com/ubuntu jammy-updates InRelease [119 kB]
Пол:3 http://by.archive.ubuntu.com/ubuntu jammy-backports InRelease [108 kB]
Пол:4 http://security.ubuntu.com/ubuntu jammy-security InRelease [110 kB]
Пол:5 https://download.docker.com/linux/ubuntu jammy InRelease [48,9 kB]
Пол:6 http://by.archive.ubuntu.com/ubuntu jammy-updates/main amd64 Packages [1.026 kB]
Пол:7 http://by.archive.ubuntu.com/ubuntu jammy-updates/main i386 Packages [477 kB]
Пол:8 http://by.archive.ubuntu.com/ubuntu jammy-updates/main Translation-en [216 kB]
Пол:9 https://download.docker.com/linux/ubuntu jammy/stable amd64 Packages [16,1 kB]
Пол:10 http://by.archive.ubuntu.com/ubuntu jammy-updates/main amd64 DEP-11 Metadata [102 kB]
Пол:11 http://by.archive.ubuntu.com/ubuntu jammy-updates/main amd64 c-n-f Metadata [14,0 kB]
Пол:12 http://by.archive.ubuntu.com/ubuntu jammy-updates/restricted i386 Packages [28,4 kB]
Пол:13 http://by.archive.ubuntu.com/ubuntu jammy-updates/restricted amd64 Packages [812 kB]
Пол:14 http://by.archive.ubuntu.com/ubuntu jammy-updates/restricted Translation-en [127 kB]
Пол:15 http://by.archive.ubuntu.com/ubuntu jammy-updates/universe i386 Packages [608 kB]
Пол:16 http://by.archive.ubuntu.com/ubuntu jammy-updates/universe amd64 Packages [902 kB]
Пол:17 http://security.ubuntu.com/ubuntu jammy-security/main i386 Packages [280 kB]
Пол:18 http://by.archive.ubuntu.com/ubuntu jammy-updates/universe Translation-en [181 kB]
Пол:19 http://by.archive.ubuntu.com/ubuntu jammy-updates/universe amd64 DEP-11 Metadata [269 kB]
Пол:20 http://by.archive.ubuntu.com/ubuntu jammy-updates/universe amd64 c-n-f Metadata [18,6 kB]
Пол:21 http://by.archive.ubuntu.com/ubuntu jammy-updates/multiverse amd64 Packages [24,1 kB]
Пол:22 http://by.archive.ubuntu.com/ubuntu jammy-updates/multiverse amd64 DEP-11 Metadata [940 B]
Пол:23 http://by.archive.ubuntu.com/ubuntu jammy-backports/main amd64 DEP-11 Metadata [7.980 B]
Пол:24 http://by.archive.ubuntu.com/ubuntu jammy-backports/universe amd64 DEP-11 Metadata [12,5 kB]
Пол:25 http://security.ubuntu.com/ubuntu jammy-security/main amd64 Packages [731 kB]
Пол:26 http://security.ubuntu.com/ubuntu jammy-security/main amd64 DEP-11 Metadata [41,5 kB]
Пол:27 http://security.ubuntu.com/ubuntu jammy-security/main amd64 c-n-f Metadata [9.028 B]
Пол:28 http://security.ubuntu.com/ubuntu jammy-security/universe amd64 Packages [717 kB]
Пол:29 http://security.ubuntu.com/ubuntu jammy-security/universe i386 Packages [520 kB]
Пол:30 http://security.ubuntu.com/ubuntu jammy-security/universe amd64 DEP-11 Metadata [18,5 kB]
Пол:31 http://security.ubuntu.com/ubuntu jammy-security/universe amd64 c-n-f Metadata [14,2 kB]
Получено 7.557 kB за 4с (1.721 kB/s)
Чтение списков пакетов… Готово
Построение дерева зависимостей… Готово
Чтение информации о состоянии… Готово
Может быть обновлено 206 пакетов. Запустите «apt list --upgradable» для их показа.
W: https://download.docker.com/linux/ubuntu/dists/jammy/InRelease: Key is stored in legacy trusted.gpg keyring (/etc/apt/trusted.gpg), see the DEPRECATION section in apt-key(8) for details.
irina@irina-linux:~$ sudo apt install mysql-common
Чтение списков пакетов… Готово
Построение дерева зависимостей… Готово
Чтение информации о состоянии… Готово
Уже установлен пакет mysql-common самой новой версии (5.8+1.0.8).
mysql-common помечен как установленный вручную.
Обновлено 0 пакетов, установлено 0 новых пакетов, для удаления отмечено 0 пакетов, и 206 пакетов не обновлено.
irina@irina-linux:~$
irina@irina-linux:~$ apt download lftp
Пол:1 http://by.archive.ubuntu.com/ubuntu jammy/main amd64 lftp amd64 4.9.2-1build1 [720 kB]
Игн:1 http://by.archive.ubuntu.com/ubuntu jammy/main amd64 lftp amd64 4.9.2-1build1
Пол:1 http://by.archive.ubuntu.com/ubuntu jammy/main amd64 lftp amd64 4.9.2-1build1 [720 kB]
Игн:1 http://by.archive.ubuntu.com/ubuntu jammy/main amd64 lftp amd64 4.9.2-1build1
Пол:1 http://by.archive.ubuntu.com/ubuntu jammy/main amd64 lftp amd64 4.9.2-1build1 [720 kB]
Игн:1 http://by.archive.ubuntu.com/ubuntu jammy/main amd64 lftp amd64 4.9.2-1build1
Пол:1 http://by.archive.ubuntu.com/ubuntu jammy/main amd64 lftp amd64 4.9.2-1build1 [720 kB]
Ошб:1 http://by.archive.ubuntu.com/ubuntu jammy/main amd64 lftp amd64 4.9.2-1build1
  Ошибка записи в файл - write (28: На устройстве не осталось свободного места) [IP: 82.209.230.71 80]
E: Не удалось получить http://by.archive.ubuntu.com/ubuntu/pool/main/l/lftp/lftp_4.9.2-1build1_amd64.deb  Ошибка записи в файл - write (28: На устройстве не осталось свободного места) [IP: 82.209.230.71 80]
irina@irina-linux:~$ sudo dpkg -i lftp_4.9.2-1build1_amd64.deb
dpkg-deb: ошибка: 'lftp_4.9.2-1build1_amd64.deb' is not a Debian format archive
dpkg: ошибка при обработке архива lftp_4.9.2-1build1_amd64.deb (--install):
 dpkg-deb --control subprocess returned error exit status 2
При обработке следующих пакетов произошли ошибки:
 lftp_4.9.2-1build1_amd64.deb
irina@irina-linux:~$ sudo dpkg -r lftp
dpkg: предупреждение: игнорируется запрос на удаление неустановленного пакета lftp
irina@irina-linux:~$ history
  140  cat > 'Домашние животные'
  141  cat 'Домашние животные'
  142  cat > 'Вьючие животные'
  143  cat  'Вьючие животные'
  144  cat 'Вьючие животные' 'Домашние животные' > 'Животные'
  145  cat 'Животные'
  146  mv 'Животные' 'Друзья человека'
  147  mkdir animals
  148  mv 'Друзья человека' animals/'Друзья человека'
  149  ls -la animals
  150  sudo apt-key adv --keyserver pgp.mit.edu --recv-keys 3A79BD29
  151  sudo echo 'deb http://repo.mysql.com/apt/ubuntu/ bionic mysql-8.0' > /etc/apt/sources
  152  sudo apt update
  153  sudo apt install mysql-common
  154  apt download lftp
  155  sudo dpkg -i lftp_4.9.2-1build1_amd64.deb
  156  sudo dpkg -r lftp
  157  history
