package com.miaa.rh.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configuracion_usuario")
public class ConfiguracionUsuarioModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuariosModel usuario;
    
    @Builder.Default
    @Column(name = "genera_qr", nullable = false)
    private Boolean generaQr = false;
    
    @Builder.Default
    @Column(name = "checa_qr", nullable = false)
    private Boolean checaQr = false;
    
    @Builder.Default
    @Column(name = "checa_ubicacion", nullable = false)
    private Boolean checaUbicacion = false;
    
    @Builder.Default
    @Column(name = "asistencias", nullable = false)
    private Boolean asistencias = false;
    
    @Builder.Default
    @Column(name = "is_jefe", nullable = false)
    private Boolean jefe = false;
    
    @Builder.Default
    @Column(name = "finger_print", nullable = false)
    private Boolean fingerPrint = false;
    
    @Builder.Default
    @Column(name = "geo_cerca_size")
    @Min(value = 1, message = "El tamaño de geocerca debe ser mayor a 0")
    @Max(value = 100, message = "El tamaño de geocerca no puede exceder 100 metros")
    private Integer geoCercaSize = 5;
    
    @Builder.Default
    @Column(name = "periodo_dias")
    @Min(value = 1, message = "El período debe ser mayor a 0 días")
    @Max(value = 365, message = "El período no puede exceder 365 días")
    private Integer periodoDias = 30;
    
    @Builder.Default
    @Column(name = "device_token", columnDefinition = "TEXT")
    private String deviceToken = "";
    
    @Column(name = "verification_code")
    @Builder.Default
    private String verificationCode = "";
    
    @Builder.Default
    @Column(name = "email_send", nullable = false)
    private Boolean emailSend = false;
    
    @Builder.Default
    @Column(name = "not_check", nullable = false)
    private Boolean notCheck = false;
}
