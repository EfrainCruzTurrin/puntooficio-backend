package com.puntooficio.puntooficio.config;

import com.puntooficio.puntooficio.categoria.models.Categoria;
import com.puntooficio.puntooficio.categoria.repositories.CategoriaRepository;
import com.puntooficio.puntooficio.ciudad.models.Ciudad;
import com.puntooficio.puntooficio.ciudad.repositories.CiudadRepository;
import com.puntooficio.puntooficio.subcategoria.models.Subcategoria;
import com.puntooficio.puntooficio.subcategoria.repositories.SubcategoriaRepository;
import com.puntooficio.puntooficio.trabajador.repositories.TrabajadorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(CategoriaRepository categoriaRepo,
                               SubcategoriaRepository subcategoriaRepo,
                               CiudadRepository ciudadRepo,
                               TrabajadorRepository trabajadorRepo) {
        return args -> {
            // Seed ciudades
            if (ciudadRepo.count() == 0) {
                ciudadRepo.save(Ciudad.builder().nombre("Las Varillas").build());
            }



            if (categoriaRepo.count() > 0) return;

            Map<String, List<String>> data = Map.of(
                    "Oficios del hogar", List.of(
                            "Plomería", "Electricidad", "Instalación de gas", "Carpintería",
                            "Pintura", "Albañilería", "Herrería", "Cerrajería", "Aire acondicionado"
                    ),
                    "Jardín y exteriores", List.of(
                            "Corte de césped", "Poda de árboles", "Diseño de jardines", "Limpieza de terrenos"
                    ),
                    "Belleza y estética", List.of(
                            "Peluquería", "Barbería", "Coloración y mechas", "Manicura y pedicura",
                            "Uñas acrílicas y gel", "Diseño de cejas", "Limpieza facial",
                            "Tratamientos faciales", "Maquillaje artístico", "Depilación",
                            "Extensiones de pestañas"
                    ),
                    "Hogar y cuidados", List.of(
                            "Limpieza del hogar", "Planchado", "Cuidado de niños",
                            "Cuidado de adultos mayores", "Paseo de mascotas"
                    ),
                    "Educación", List.of(
                            "Apoyo escolar", "Clases de matemática", "Clases de inglés",
                            "Clases de guitarra", "Clases de piano", "Clases de canto",
                            "Clases de dibujo y pintura"
                    ),
                    "Tecnología", List.of(
                            "Reparación de PC", "Reparación de celulares",
                            "Diseño gráfico", "Soporte técnico"
                    ),
                    "Fotografía y video", List.of(
                            "Fotografía de eventos", "Fotografía de productos",
                            "Fotografía de casamientos", "Retratos y sesiones",
                            "Video y edición", "Fotografía inmobiliaria"
                    )
            );

            data.forEach((nombreCategoria, subcategorias) -> {
                Categoria categoria = new Categoria();
                categoria.setNombre(nombreCategoria);
                Categoria savedCategoria = categoriaRepo.save(categoria);

                subcategorias.forEach(nombreSub -> {
                    Subcategoria sub = new Subcategoria();
                    sub.setNombre(nombreSub);
                    sub.setCategoria(savedCategoria);
                    subcategoriaRepo.save(sub);
                });
            });
        };
    }
}