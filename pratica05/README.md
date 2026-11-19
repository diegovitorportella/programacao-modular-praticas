# Prática 5 - Testes Automatizados com JUnit

Testes automatizados (JUnit 5) do sistema de pedidos da **Prática 4** (Maven, Java 17+).
O código de `src/main` é o da Prática 4; a regra de negócio da lista de pedidos fica em
`GerenciadorPedidos`, e a `Main` só cuida do menu, então nada é testado pela `Main`.

## Testes (`src/test/java/pedidos`)
- `PedidoTestBase`: testes comuns a todos os tipos (construção, `ValorInvalidoException`, `PedidoInvalidoException`, fluxo de status, regra do ENTREGUE, exceções do Java). `PedidoTest`, `PedidoEletronicoTest`, `PedidoRoupaTest` e `PedidoAlimentoTest` herdam dela, então tudo roda para cada tipo.
- `PedidoEletronicoTest`, `PedidoRoupaTest`, `PedidoAlimentoTest`: valor final (limites 100 e 200), frete e se é entregável
- `StatusPedidoTest`: regra do enum (ENTREGUE não pode ser alterado) e fluxo
- `CategoriaTest`
- `GerenciadorPedidosTest`: processamento e remoção de cancelados (Iterator), listagens
- `@BeforeEach` instancia os objetos antes de cada teste; `@AfterEach` imprime que o teste terminou.

## Executar
```
mvn test
```
