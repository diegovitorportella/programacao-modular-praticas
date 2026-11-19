# Prática 3 - Herança e Polimorfismo

## Exercício 1 - Agenda Telefônica (`exercicio1/`)
- `Contato` (base) e subclasses `ContatoPessoal`, `ContatoProfissional` e `ContatoEmergencia` (prioridade de 1 a 5)
- `Agenda`: adicionar, remover, buscar por nome, e-mail e telefone, e quantidade de contatos
- `MainAgenda`: menu interativo

## Exercício 2 - Venda de Ingressos (`exercicio2/`)
- `Ingresso` (abstrata) com `calcularValorFinal()` e `obterBeneficios()`
  - `IngressoComum`: valor base, sem benefícios
  - `IngressoEstudante`: 50% de desconto, meia-entrada
  - `IngressoVIP`: acréscimo de 80%, lounge e backstage opcional
- `Bilheteria`: adicionar, remover, buscar, listar, arrecadação total e benefícios
- `MainIngressos`: menu interativo

## Executar
```
cd exercicio1
javac *.java
java MainAgenda
```
```
cd exercicio2
javac *.java
java MainIngressos
```
