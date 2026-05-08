INSERT INTO categorias (id, nombre, descripcion)
VALUES (1, 'Carpintería', 'Servicios relacionados con madera y muebles');
INSERT INTO trabajadores (id, nombre, apellido, ciudad, telefono, password, perfil_verificado, dni, categoria_id)
VALUES (10, 'Juan', 'Pérez', 'Córdoba', '3511234567', 'password123', false, '12345678', 1);
INSERT INTO clientes (id, nombre_completo, telefono, password, email)
VALUES (100, 'Efrain Turrin', '3533123456', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lapp', 'efrain@example.com');