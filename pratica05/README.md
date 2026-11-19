# Prática 5 - Testes Automatizados com JUnit

Sistema de pedidos com testes JUnit 5 (Maven, Java 17+).

## Domínio (`src/main/java/pedidos`)
- `Pedido` (abstrata): itens, status, `processar()`, `alterarStatus()`, `calcularValorFinal()`
  - `PedidoFisico`: subtotal + frete
  - `PedidoEletronico`: 10% de desconto
  - `PedidoAssinatura`: subtotal mensal x meses, 20% de desconto a partir de 12 meses
- `ItemPedido`: produto, preço unitário e quantidade
- `StatusPedido` (enum): fluxo CRIADO → PAGO → EM_PREPARACAO → ENVIADO → ENTREGUE; ENTREGUE e CANCELADO são finais
- `excecoes/`: `PedidoException`, `PedidoVazioException`, `StatusInvalidoException`
- `Main`: apenas demonstração, sem regra de negócio

## Testes (`src/test/java/pedidos`)
- `PedidoTestBase`: testes comuns a todos os tipos de pedido (processamento, regra do ENTREGUE, exceções). `PedidoFisicoTest`, `PedidoEletronicoTest` e `PedidoAssinaturaTest` herdam dela, então tudo roda para cada tipo.
- `ItemPedidoTest`, `StatusPedidoTest`
- `@BeforeEach` instancia objetos antes de cada teste; `@AfterEach` imprime que o teste terminou.

## Executar
```
mvn test
```
