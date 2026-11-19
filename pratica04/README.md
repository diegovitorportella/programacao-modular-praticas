# Prática 4 - Iteradores, Enumeradores e Tratamento de Exceções

Sistema gerenciador de pedidos (Maven, Java 17+).

- **Enums:** `StatusPedido` (PENDENTE, PROCESSANDO, ENVIADO, ENTREGUE, CANCELADO) e `Categoria`
- **Interfaces:** `Processavel` (`processarPedido`, `calcularValorFinal`) e `Entregavel` (`calcularFrete`)
- **Exceções:** `PedidoInvalidoException` (processar cancelado / alterar entregue) e `ValorInvalidoException` (valor <= 0)
- **Pedidos:** `Pedido` (abstrata), `PedidoEletronico`, `PedidoRoupa`, `PedidoAlimento` (único não entregável)
- **`GerenciadorPedidos`:** processa e remove cancelados com `Iterator`, lista pedidos sem os cancelados e os entregáveis
- **`Main`:** só o menu de console

## Executar
```
mvn -q compile
java -cp target/classes pedidos.Main
```
