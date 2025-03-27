package com.ecicredit.pagos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "pagos")
public class Pago {
    @Id
    private String id;
    private String usuarioId;
    private List<Articulo> articulos;
    private BigDecimal total;
    private String estado;
    private String mensaje;
    private LocalDate fecha;
    private String transaccionId;

    public boolean validarTotal() {
        BigDecimal calculado = calcularTotal();
        return calculado.compareTo(total) == 0;
    }

    public BigDecimal calcularTotal() {
        return articulos.stream()
                .map(a -> a.getPrecioUnitario().multiply(BigDecimal.valueOf(a.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
