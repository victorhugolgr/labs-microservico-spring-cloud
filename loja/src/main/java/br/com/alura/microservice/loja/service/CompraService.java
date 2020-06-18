package br.com.alura.microservice.loja.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.client.TransportadorClient;
import br.com.alura.microservice.loja.dto.CompraDTO;
import br.com.alura.microservice.loja.dto.InfoEntregaDTO;
import br.com.alura.microservice.loja.dto.InfoFornecedorDTO;
import br.com.alura.microservice.loja.dto.InfoPedidoDTO;
import br.com.alura.microservice.loja.dto.VoucherDTO;
import br.com.alura.microservice.loja.model.Compra;
import br.com.alura.microservice.loja.model.CompraState;
import br.com.alura.microservice.loja.repository.CompraRepository;

@Service
public class CompraService {
	
	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private TransportadorClient transportadorClient;
	
	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compra) {
		
		Compra compraSalva = new Compra();
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		compraSalva.setState(CompraState.RECEBIDO);
		compraRepository.save(compraSalva);
		compra.setCompraId(compraSalva.getId());
		
		//if(1==1) throw new RuntimeException(); //Para testar o tratamento de erro.

		InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());
		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());
		InfoEntregaDTO entregaDTO = new InfoEntregaDTO();
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setState(CompraState.PEDIDO_REALIZADO);
		compraRepository.save(compraSalva);
		
		entregaDTO.setPedidoId(pedido.getId());
		entregaDTO.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDTO.setEnderecoOrigem(info.getEndereco());
		entregaDTO.setEnderecoDestino(compra.getEndereco().toString());
		
		VoucherDTO voucher = transportadorClient.reservaEntrega(entregaDTO);
		compraSalva.setState(CompraState.RESERVA_ENTREGA_REALIZADA);
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());
		compraRepository.save(compraSalva);
		
		return compraSalva;
	}
	
	/**
	 * Caso exista falha poderá repocessa a compra
	 * @param id
	 * @return
	 */
	public Compra reprocessaCompra(Long id) {
		return null;
	}
	
	/**
	 * Caso exista falha poderá cancelar a compra
	 * @param id
	 * @return
	 */
	public Compra cancelaCompra(Long id) {
		return null;
	}
	
	public Compra realizaCompraFallback(CompraDTO compra) {
		if(compra.getCompraId() != null) {
			return compraRepository.findById(compra.getCompraId()).get();
		}
		Compra compraFallback = new Compra();
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());
		return compraFallback;
	}

	@HystrixCommand(threadPoolKey = "getByIdThreadPoll")
	public Compra getById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	
}
