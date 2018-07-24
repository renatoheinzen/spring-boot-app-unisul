package com.unisul.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.unisul.domain.Pedido;
import com.unisul.services.PedidoService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value= "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) throws ObjectNotFoundException {

		Pedido obj = service.find(id);
		return ResponseEntity.ok(obj);

	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody Pedido obj) throws ObjectNotFoundException {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				  .path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="instante") String orderBy,
			@RequestParam(value="direction", defaultValue="DESC")String direction)
			throws ObjectNotFoundException {

		Page<Pedido> pedidos = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok(pedidos);

	}
	

}