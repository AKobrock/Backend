package com.usuarios.Demo.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Usuario que hace la reserva
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private UserModel usuario;

    // Papá reservado
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "papa_id", referencedColumnName = "id", nullable = false)
    private PapaModel papa;

    private LocalDate fechaReserva;
    private LocalDate fechaVisita;
    private String estado;
    private String direccionVisita;
}

//fetch = FetchType.LAZY evita cargar todo el usuario/papá completo en cada consulta.
//referencedColumnName = "id" garantiza que se vinculen a las columnas correctas.
//nullable = false mantiene integridad.