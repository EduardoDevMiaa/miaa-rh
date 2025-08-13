package com.miaa.rh.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "datos_personales_usuario")
public class DatosPersonalesUsuarioModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuariosModel usuario;
    
    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Column(name = "apellido_paterno", nullable = false, length = 100)
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 100, message = "El apellido paterno no puede exceder 100 caracteres")
    private String apellidoPaterno;
    
    @Column(name = "apellido_materno", length = 100)
    @Size(max = 100, message = "El apellido materno no puede exceder 100 caracteres")
    private String apellidoMaterno;
    
    @Column(name = "genero")
    private Boolean genero; // true = masculino, false = femenino
    
    @Column(name = "fecha_nacimiento")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;
    
    @Column(name = "fecha_ingreso")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso;
    
    @Column(name = "curp", unique = true, length = 18)
    @Size(min = 18, max = 18, message = "El CURP debe tener exactamente 18 caracteres")
    @Pattern(regexp = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}$", message = "Formato de CURP inválido")
    private String curp;
    
    @Column(name = "rfc", unique = true, length = 13)
    @Size(min = 10, max = 13, message = "El RFC debe tener entre 10 y 13 caracteres")
    @Pattern(regexp = "^[A-ZÑ&]{3,4}[0-9]{6}[A-Z0-9]{3}$", message = "Formato de RFC inválido")
    private String rfc;
    
    @Column(name = "nss", length = 11)
    @Size(min = 11, max = 11, message = "El NSS debe tener exactamente 11 dígitos")
    @Pattern(regexp = "^[0-9]{11}$", message = "El NSS solo debe contener números")
    private String nss;
    
    @Column(name = "telefono_personal", length = 15)
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Formato de teléfono inválido")
    private String telefonoPersonal;
    
    @Column(name = "telefono_institucional", length = 15)
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Formato de teléfono inválido")
    private String telefonoInstitucional;
    
    @Column(name = "correo_institucional", length = 100)
    @Email(message = "Formato de correo electrónico inválido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correoInstitucional;
    
    @Column(name = "correo_personal", length = 100)
    @Email(message = "Formato de correo electrónico inválido")
    @Size(max = 100, message = "El correo personal no puede exceder 100 caracteres")
    private String correoPersonal;
    
    @Column(name = "contacto_emergencia_1", length = 100)
    @Size(max = 100, message = "El contacto de emergencia no puede exceder 100 caracteres")
    private String contactoEmergencia1;
    
    @Column(name = "telefono_emergencia_1", length = 15)
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Formato de teléfono inválido")
    private String telefonoEmergencia1;
    
    @Column(name = "contacto_emergencia_2", length = 100)
    @Size(max = 100, message = "El contacto de emergencia no puede exceder 100 caracteres")
    private String contactoEmergencia2;
    
    @Column(name = "telefono_emergencia_2", length = 15)
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Formato de teléfono inválido")
    private String telefonoEmergencia2;
    
    @Column(name = "tipo_sangre", length = 3)
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Tipo de sangre inválido")
    private String tipoSangre;
    
    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;
    
    @Column(name = "foto_url")
    private String fotoUrl;
    
    @Builder.Default
    @Column(name = "sindicalizado", nullable = false)
    private Boolean sindicalizado = false;
    
    @Column(name = "direccion_domicilio", columnDefinition = "TEXT")
    private String direccionDomicilio;
    
    @Column(name = "ciudad", length = 100)
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciudad;
    
    @Column(name = "estado", length = 50)
    @Size(max = 50, message = "El estado no puede exceder 50 caracteres")
    private String estado;
    
    @Column(name = "codigo_postal", length = 10)
    @Pattern(regexp = "^[0-9]{5}$", message = "El código postal debe tener 5 dígitos")
    private String codigoPostal;
    
    @Column(name = "estado_civil", length = 20)
    @Pattern(regexp = "^(SOLTERO|CASADO|DIVORCIADO|VIUDO|UNION_LIBRE)$", message = "Estado civil inválido")
    private String estadoCivil;
    
    @Column(name = "numero_hijos")
    @Min(value = 0, message = "El número de hijos no puede ser negativo")
    @Max(value = 20, message = "El número de hijos no puede exceder 20")
    private Integer numeroHijos;
    
    // Métodos de utilidad
    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + 
               (apellidoMaterno != null ? " " + apellidoMaterno : "");
    }
    
    public Integer getEdad() {
        if (fechaNacimiento != null) {
            return LocalDate.now().getYear() - fechaNacimiento.getYear();
        }
        return null;
    }
    
    public Integer getAntiguedad() {
        if (fechaIngreso != null) {
            return LocalDate.now().getYear() - fechaIngreso.getYear();
        }
        return null;
    }
}
