package com.ecicredit.pagos.service;

import com.ecicredit.pagos.model.Pago;
import com.ecicredit.pagos.model.PagoDTO;
import com.ecicredit.pagos.repository.PagoRepository;
import com.ecicredit.pagos.exception.PagoException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    
    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Pago procesarPago(PagoDTO pagoDTO) {
        Pago pago = convertirDTOaPago(pagoDTO);
        
        if (!pago.validarTotal()) {
            pago.setEstado("DECLINADO");
            pago.setMensaje("El total calculado no coincide con el total proporcionado");
            pagoRepository.save(pago);
            throw new PagoException("El total calculado no coincide con el total proporcionado");
        }
        
        pago.setEstado("APROBADO");
        pago.setMensaje("Pago procesado correctamente");
        pago.setTransaccionId(generarIdTransaccion());
        return pagoRepository.save(pago);
    }

    public List<Pago> consultarPagos(String usuarioId) {
        return pagoRepository.findByUsuarioId(usuarioId);
    }

    public Pago consultarPago(String transaccionId) {
        return pagoRepository.findByTransaccionId(transaccionId)
                .orElseThrow(() -> new PagoException("Pago no encontrado"));
    }

    private Pago convertirDTOaPago(PagoDTO pagoDTO) {
        Pago pago = new Pago();
        pago.setUsuarioId(pagoDTO.getUsuarioId());
        pago.setArticulos(pagoDTO.getArticulos());
        pago.setTotal(pagoDTO.getTotal());
        pago.setFecha(parseFecha(pagoDTO.getFecha()));
        return pago;
    }

    private LocalDate parseFecha(String fechaStr) {
        try {
            return LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (Exception e) {
            throw new PagoException("Formato de fecha inválido. Use DD-MM-YYYY");
        }
    }

    private String generarIdTransaccion() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
