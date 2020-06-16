package br.com.alura.microservice.fornecedor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.microservice.fornecedor.dto.ItemDoPedidoDTO;
import br.com.alura.microservice.fornecedor.model.Pedido;
import br.com.alura.microservice.fornecedor.service.PedidoService;

@RestController
@RequestMapping("pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Pedido realizaPedido(@RequestBody List<ItemDoPedidoDTO> produtos) {
		return pedidoService.realizaPedido(produtos);
	}
	
	@RequestMapping("/{id}")
	public Pedido getPedidoPorId(@PathVariable Long id) {
		return pedidoService.getPedidoPorId(id);
	}
}
