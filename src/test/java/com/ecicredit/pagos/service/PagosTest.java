package com.ecicredit.pagos.service;

import com.ecicredit.pagos.exception.PagoException;
import com.ecicredit.pagos.model.Articulo;
import com.ecicredit.pagos.model.Pago;
import com.ecicredit.pagos.model.PagoDTO;
import com.ecicredit.pagos.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void procesarPago_Exitoso() {
        PagoDTO pagoDTO = crearPagoDTOValido();
        Pago pagoGuardado = crearPagoValido();
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoGuardado);

        Pago resultado = pagoService.procesarPago(pagoDTO);

        assertNotNull(resultado);
        assertEquals("APROBADO", resultado.getEstado());
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    void procesarPago_TotalIncorrecto() {
        PagoDTO pagoDTO = crearPagoDTOValido();
        pagoDTO.setTotal(BigDecimal.valueOf(999)); 

        assertThrows(PagoException.class, () -> pagoService.procesarPago(pagoDTO));
    }

    @Test
    void consultarPagos_PorUsuarioId() {
        when(pagoRepository.findByUsuarioId("user123"))
                .thenReturn(Collections.singletonList(crearPagoValido()));

        List<Pago> resultados = pagoService.consultarPagos("user123");

        assertEquals(1, resultados.size());
        verify(pagoRepository, times(1)).findByUsuarioId("user123");
    }

    private PagoDTO crearPagoDTOValido() {
        PagoDTO dto = new PagoDTO();
        dto.setUsuarioId("user123");
        dto.setFecha("15-05-2023");
        dto.setTotal(BigDecimal.valueOf(1500));
        
        Articulo articulo1 = new Articulo();
        articulo1.setNombre("Laptop");
        articulo1.setPrecioUnitario(BigDecimal.valueOf(1000));
        articulo1.setCantidad(1);
        
        Articulo articulo2 = new Articulo();
        articulo2.setNombre("Mouse");
        articulo2.setPrecioUnitario(BigDecimal.valueOf(500));
        articulo2.setCantidad(1);
        
        dto.setArticulos(Arrays.asList(articulo1, articulo2));
        return dto;
    }

    private Pago crearPagoValido() {
        Pago pago = new Pago();
        pago.setUsuarioId("user123");
        pago.setFecha(LocalDate.of(2023, 5, 15));
        pago.setTotal(BigDecimal.valueOf(1500));
        pago.setEstado("APROBADO");
        pago.setTransaccionId("TXN-ABC123");
        return pago;
    }
}
