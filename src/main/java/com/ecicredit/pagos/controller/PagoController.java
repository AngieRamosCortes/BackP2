package com.ecicredit.pagos.controller;

import com.ecicredit.pagos.model.Pago;
import com.ecicredit.pagos.model.PagoDTO;
import com.ecicredit.pagos.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<?> procesarPago(@RequestBody PagoDTO pagoDTO) {
        try {
            Pago pago = pagoService.procesarPago(pagoDTO);
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pago>> consultarPagos(@PathVariable String usuarioId) {
        return ResponseEntity.ok(pagoService.consultarPagos(usuarioId));
    }

    @GetMapping("/{transaccionId}")
    public ResponseEntity<Pago> consultarPago(@PathVariable String transaccionId) {
        return ResponseEntity.ok(pagoService.consultarPago(transaccionId));
    }
}
