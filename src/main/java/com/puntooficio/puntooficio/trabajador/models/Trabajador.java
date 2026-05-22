package com.puntooficio.puntooficio.trabajador.models;
import com.puntooficio.puntooficio.categoria.models.Categoria;
import com.puntooficio.puntooficio.ciudad.models.Ciudad;
import com.puntooficio.puntooficio.galeria.models.GaleriaImagen;
import com.puntooficio.puntooficio.resena.models.Resena;
import com.puntooficio.puntooficio.subcategoria.models.Subcategoria;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trabajadores")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String dni;


    // URL de foto de perfil (puede ser null — se muestra avatar con iniciales)
    @Column(name = "foto_perfil")
    private String fotoPerfil;

    // Descripción libre del trabajador (máx 500 caracteres)
    @Column(length = 500)
    private String biografia;

    // Número de WhatsApp para el botón de contacto (puede diferir del teléfono de registro)
    @Column(name = "whatsapp_contacto")
    private String whatsappContacto;

    // Usuario de Instagram (sin @, se usa para armar el link)
    @Column(name = "instagram_usuario")
    private String instagramUsuario;

    // Cómo prefiere que lo contacten: WHATSAPP, INSTAGRAM, AMBOS
    @Column(name = "metodo_contacto")
    @Enumerated(EnumType.STRING)
    private MetodoContacto metodoContacto;

    // Si el perfil fue verificado por el admin
    @Column(name = "perfil_verificado")
    @Builder.Default
    private Boolean perfilVerificado = false;

    // Subcategorías que ofrece este trabajador (relación ManyToMany)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "trabajador_subcategorias",
            joinColumns = @JoinColumn(name = "trabajador_id"),
            inverseJoinColumns = @JoinColumn(name = "subcategoria_id")
    )
    @Builder.Default
    private Set<Subcategoria> subcategorias = new HashSet<>();

    // Imágenes de la galería de trabajos (máx 9)
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GaleriaImagen> galeria = new ArrayList<>();

    // Reseñas recibidas
    @OneToMany(mappedBy = "trabajador", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Resena> resenas = new ArrayList<>();

    // Enum para método de contacto preferido
    public enum MetodoContacto {
        WHATSAPP, INSTAGRAM, AMBOS
    }
}