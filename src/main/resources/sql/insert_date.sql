insert into roles
values (1, 'Роль пользователя', 'USER'),
       (2, 'Роль библиотекаря', 'LIBRARIAN');


--Режиссеры
drop sequence directors_sequence;
create sequence directors_sequence;
alter sequence directors_sequence owner to postgres;

truncate table directors cascade;
INSERT INTO directors (id, created_by, created_when, directors_fio, position) VALUES
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Станислав Говорухин', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Леонид Гайдай', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Никита Михалков', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Николай Лебедев', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Александр Рогожкин', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Андрей Тарковский', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Алексей Балабанов', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Владимир Меньшов', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Алексей Сидоров', '10'),
    (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Эльдар Рязанов', '10');

-- FILMS
drop sequence films_sequence;
create sequence films_sequence;
alter sequence films_sequence owner to postgres;
truncate table films cascade;

INSERT INTO films (id, created_by, created_when, title, country, genre, premier_year) VALUES
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.115079', 'Место встречи изменить нельзя', 'Россия', 4, 1979),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.165892', 'Бриллиантовая рука', 'Россия', 5, 1969),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.195463', '12', 'Россия', 1, 2007),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.225698', 'Легенда №17', 'Россия', 2, 2013),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.248956', 'Особенности национальной охоты', 'Россия', 5, 1995),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.287562', 'Зеркало', 'Россия', 0, 1974),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.287562', 'Брат', 'Россия', 1, 1997),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.287562', 'Москва слезам не верит', 'Россия', 3, 1979),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.287562', 'Бригада', 'Россия', 6, 2002),
    (nextval('films_sequence'), 'admin', '2023-06-29 13:54:28.287562', 'Ирония судьбы или с легким паром', 'Россия', 4, 1975);

--FILMS_DIRECTORS

INSERT INTO public.films_directors (film_id, director_id) VALUES
    (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);
