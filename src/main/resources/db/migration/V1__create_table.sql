DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias`
(
    `id`        bigint     NOT NULL AUTO_INCREMENT,
    `nome`      varchar(200) DEFAULT NULL,
    `descricao` varchar(400) DEFAULT NULL,
    `ativo`     tinyint(1) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `produtos`;
CREATE TABLE IF NOT EXISTS `produtos`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `nome`           varchar(200) NOT NULL,
    `descricao`      varchar(400) DEFAULT NULL,
    `valor`          double       NOT NULL,
    `ativo`          tinyint(1)   NOT NULL,
    `categoriaId_fk` bigint       NOT NULL,
    `imagemBase64`   longtext,
    `imagemUrl`      varchar(400) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `tipoProdutoId_fk` (`categoriaId_fk`),
    CONSTRAINT `produtos_ibfk_1` FOREIGN KEY (`categoriaId_fk`) REFERENCES `categorias` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `clientes`;
CREATE TABLE IF NOT EXISTS `clientes`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT,
    `cpf`          varchar(11) NOT NULL,
    `primeironome` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `ultimonome`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `email`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `ativo`        tinyint(1)                                                    DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `statuspedidos`;
CREATE TABLE IF NOT EXISTS `statuspedidos`
(
    `id`    bigint     NOT NULL AUTO_INCREMENT,
    `nome`  varchar(200) DEFAULT NULL,
    `ativo` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `pedidos`;
CREATE TABLE IF NOT EXISTS `pedidos`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `clienteId_fk`        bigint      DEFAULT NULL,
    `ativo`               tinyint(1)  DEFAULT NULL,
    `statusPedidoId_fk`   bigint      DEFAULT NULL,
    `dataHoraCriado`      datetime    DEFAULT NULL,
    `dataHoraRecebimento` datetime    DEFAULT NULL,
    `dataHoraFinalizado`  datetime    DEFAULT NULL,
    `valor`               double      DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `clienteId_fk` (`clienteId_fk`),
    KEY `statusPedidoId_fk` (`statusPedidoId_fk`),
    CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`clienteId_fk`) REFERENCES `clientes` (`id`),
    CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`statusPedidoId_fk`) REFERENCES `statuspedidos` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `produtosdeumpedido`;
CREATE TABLE IF NOT EXISTS `produtosdeumpedido`
(
    `pedidoId_fk`  bigint NOT NULL,
    `produtoId_fk` bigint NOT NULL,
    `quantidade`   int    NOT NULL,
    `valor`        double NOT NULL,
    PRIMARY KEY (`pedidoId_fk`, `produtoId_fk`),
    KEY `pedidoId_fk` (`pedidoId_fk`),
    KEY `produtoId_fk` (`produtoId_fk`),
    CONSTRAINT `produtosdeumpedido_ibfk_1` FOREIGN KEY (`pedidoId_fk`) REFERENCES `pedidos` (`id`),
    CONSTRAINT `produtosdeumpedido_ibfk_2` FOREIGN KEY (`produtoId_fk`) REFERENCES `produtos` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;