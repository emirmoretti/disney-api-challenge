INSERT INTO personaje (id, nombre, edad, peso, historia) VALUES (1, 'flash', 15, 60.5, 'Corre rapido je');
INSERT INTO personaje (id, nombre, edad, peso, historia) VALUES (2, 'goku', 20, 70.5, 'Ssj');
INSERT INTO personaje (id, nombre, edad, peso, historia) VALUES (3, 'Superman', 15, 70.5, 'Sarasa');
INSERT INTO personaje (id, nombre, edad, peso, historia) VALUES (4, 'Koro Sensei', 15, 75.5, 'Piola');

INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (1, '101 Dalmata', DATE '1999-03-02', 10.4);
INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (2, 'Avengers', DATE '2001-03-02', 100);
INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (3, 'El viaje de chihiro', DATE '2001-03-02', 100);
INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (4, 'El lobo de wallstreet', DATE '2001-03-02', 100);
INSERT INTO pelicula (id, titulo, create_at, calificacion) VALUES (5, 'One Piece', DATE '2001-03-02', 100);

INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (1,1);
INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (1,2);
INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (1,3);
INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (1,4);

INSERT INTO peliculas_personajes(pelicula_id, personaje_id) VALUES (2,1);