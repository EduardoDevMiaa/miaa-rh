package com.miaa.rh.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"}),
    @UniqueConstraint(columnNames = {"numero_nomina"})
})
public class UsuariosModel implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String username;
    
    @JsonIgnore
    @Column(name = "password", nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    
    @Column(name = "numero_nomina", nullable = false, unique = true)
    @NotNull(message = "El número de nómina es obligatorio")
    @Positive(message = "El número de nómina debe ser positivo")
    private Integer numeroNomina;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER;
    
    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "estado", length = 50)
    private String estado;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relaciones principales
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puesto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PuestoModel puesto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DireccionModel direccion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subdireccion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SubdireccionesModel subdireccion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DepartamentoModel departamento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private HorariosModel horario;
    
    // Relaciones uno a uno con las entidades separadas
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ConfiguracionUsuarioModel configuracion;
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DatosPersonalesUsuarioModel datosPersonales;
    
    // Relaciones uno a muchos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AsistenciasModel> asistencias;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IncidenciasModel> incidencias;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConstanciasModel> constancias;
    
    // Relaciones muchos a muchos
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "usuario_lugares_autorizados",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "lugar_id")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<LugaresAutorizadosModel> lugaresAutorizados;
    
    @OneToMany(mappedBy = "jefe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonalCargoModel> personalACargo;
    
    // Métodos de UserDetails
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return Boolean.TRUE.equals(isActive);
    }
    
    // Métodos de utilidad
    public String getNombreCompleto() {
        if (datosPersonales != null) {
            return datosPersonales.getNombre() + " " + datosPersonales.getApellidoPaterno() + 
                   (datosPersonales.getApellidoMaterno() != null ? " " + datosPersonales.getApellidoMaterno() : "");
        }
        return "Usuario " + numeroNomina;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Enums
    public enum UserRole {
        ADMIN, SUPERVISOR, JEFE, USER
    }
}
