INSERT INTO personaje (id, nombre, edad, peso, historia) VALUES (1, 'Iron Man', 15, 60.5, 'Corre rapido je');
INSERT INTO personaje (id, nombre, edad, peso, historia) VALUES (2, 'Son Goku', 48, 70.5, 'Gokū es hijo de Bardock y Gine, nace en el año 737 con el nombre de Kakarot / Kakarotto');
INSERT INTO personaje (id, nombre, edad, peso, historia) VALUES (3, 'Superman', 15, 70.5, 'Sarasa');
INSERT INTO personaje (id, nombre, edad, peso, historia) VALUES (4, 'Monkey D. Luffy', 19, 61, 'Es el capitán de la tripulación pirata conocida como los Piratas de Sombrero de Paja, fundada por él mismo para cumplir su sueño de encontrar el One Piece. Es originario del East Blue.');

INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (1, 'Dragon Ball Z', DATE '1989-04-26', 5);
INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (2, 'Avengers', DATE '2001-03-02', 5);
INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (3, 'El viaje de chihiro', DATE '2015-10-02', 5);
INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (4, 'El lobo de wallstreet', DATE '2010-05-02', 5);
INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (5, 'One Piece', DATE '1997-06-22', 5);

INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (2,1);
INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (1,2);
INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (2,3);
INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (2,1);
INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (5,4);


/*Generos*/

INSERT INTO genero(id,nombre) VALUES (1, 'Terror');
INSERT INTO genero(id,nombre) VALUES (2, 'Comedia');
INSERT INTO genero(id,nombre) VALUES (3, 'Anime');

INSERT INTO peliculas_genero(pelicula_id, genero_id) VALUES (1,3);
INSERT INTO peliculas_genero(pelicula_id, genero_id) VALUES (2,1);
INSERT INTO peliculas_genero(pelicula_id, genero_id) VALUES (3,2);
INSERT INTO peliculas_genero(pelicula_id, genero_id) VALUES (4,2);
INSERT INTO peliculas_genero(pelicula_id, genero_id) VALUES (5,3);