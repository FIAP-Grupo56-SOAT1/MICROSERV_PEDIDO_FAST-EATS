package br.com.fiap.fasteats.dataprovider;

import br.com.fiap.fasteats.core.dataprovider.ProdutoPedidoOutputPort;
import br.com.fiap.fasteats.core.domain.model.ProdutoPedido;
import br.com.fiap.fasteats.dataprovider.repository.ProdutoPedidoRepository;
import br.com.fiap.fasteats.dataprovider.repository.entity.ProdutoPedidoIdEntity;
import br.com.fiap.fasteats.dataprovider.repository.mapper.ProdutoPedidoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutoPedidoAdapter implements ProdutoPedidoOutputPort {
    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final ProdutoPedidoEntityMapper produtoPedidoEntityMapper;

    @Override
    @Transactional
    public void removerProdutoPedido(ProdutoPedido produtoPedido) {
        produtoPedidoRepository.removeByIdPedidoAndIdProduto(produtoPedido.getIdPedido(),produtoPedido.getIdProduto());
    }

    @Override
    public Optional<ProdutoPedido> consultarProdutoPedido(ProdutoPedido produtoPedido) {
        ProdutoPedidoIdEntity id = produtoPedidoEntityMapper.toProdutoPedidoId(produtoPedido);
        return produtoPedidoRepository.findById(id).map(produtoPedidoEntityMapper::toProdutoPedido);
    }
}
