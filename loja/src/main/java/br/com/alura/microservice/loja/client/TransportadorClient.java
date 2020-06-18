package br.com.alura.microservice.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.alura.microservice.loja.dto.InfoEntregaDTO;
import br.com.alura.microservice.loja.dto.VoucherDTO;

@FeignClient("transportador")
public interface TransportadorClient {

	@PostMapping(path = "/entrega")
	VoucherDTO reservaEntrega(InfoEntregaDTO entregaDTO);
}
