INSERT INTO `categorias` (`id`, `nome`, `descricao`, `ativo`)
VALUES (1, 'LANCHE', 'LANCHE', 1),
       (2, 'BEBIDA', 'BEBIDA', 1),
       (3, 'ACOMPANHAMENTO', 'ACOMPANHAMENTO', 1),
       (4, 'SOBREMESA', 'SOBREMESA', 1);

INSERT INTO `clientes` (`cpf`, `primeironome`, `ultimonome`, `email`, `ativo`)
VALUES ('29234725897', 'Fiap', 'SOAT1', 'grupo56@fiapl.com', 1);

INSERT INTO `produtos` (`id`, `nome`, `descricao`, `valor`, `ativo`, `categoriaId_fk`, `imagemBase64`, `imagemUrl`)
VALUES (1, 'X-SALADA', 'X-salada com um hamburger 180g', 20, 1, 1, NULL, NULL),
       (2, 'X-BACON', 'X-sacon com um hamburger 180g', 22, 1, 1, NULL, NULL),
       (3, 'HOT-DOG SIMPLES', 'Hot-dog simples uma salsicha', 10, 1, 1, NULL, NULL),
       (4, 'COCA COLA LT 350ML', 'Coca cola lata 350ml', 6, 1, 2, NULL, NULL),
       (5, 'GUARANA ANTARTICA LT 350ML', 'Guarana antartica lata 350ml', 6, 1, 2, NULL, NULL),
       (6, 'AGUA 500ML', 'Agua garrafa 500ml', 5, 1, 2, NULL, NULL),
       (7, 'BATATA FRITA P', 'Batata frita pequena', 8, 1, 3, NULL, NULL),
       (8, 'BATATA FRITA M', 'Batata frita média', 12, 1, 3, NULL, NULL),
       (9, 'SORVETE CASCÃO UN', 'Sorvete cascão Un', 15, 1, 4, NULL, NULL),
       (10, 'SORVETE CASQUINHA UN', 'Sorvete casquinha Un', 7, 1, 4, NULL, NULL);


INSERT INTO `statuspedidos` (`id`, `nome`, `ativo`)
VALUES (1, 'CRIADO', 1),
       (2, 'AGUARDANDO_PAGAMENTO', 1),
       (3, 'PAGO', 1),
       (4, 'RECEBIDO', 1),
       (5, 'EM_PREPARO', 1),
       (6, 'PRONTO', 1),
       (7, 'FINALIZADO', 1),
       (8, 'CANCELADO', 1);