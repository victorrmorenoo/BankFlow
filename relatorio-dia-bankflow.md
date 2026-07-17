# Relatório de Progresso — BankFlow

**Data:** 16 de julho de 2026

---

## Resumo do dia

Com a estrutura de `Categoria`, `Movimentacao`, `Entrada` e `Despesa` (renomeada para `Saida`) já fechada, o foco do dia foi completar o **design de domínio do MVP**, fechando as classes `Carteira` e `Usuario` — as duas peças que faltavam para o modelo estar completo no papel. Também houve uma primeira aplicação prática de herança (`super()`) diretamente no código, mesmo antes de assistir formalmente as aulas 71-75.

---

## 1. Renomeação: `Despesa` → `Saida`

- A classe antes chamada `Despesa` passou a se chamar `Saida`, mantendo os mesmos atributos e responsabilidades (herda de `Movimentacao`, possui `formaPagamento` como atributo próprio).
- **Pendência identificada:** avaliar se essa renomeação deve refletir também no enum `TipoCategoria` (hoje `ENTRADA`/`DESPESA`) e nos métodos da `Carteira` (`registrarSaida()`), para manter consistência de nomenclatura em todo o domínio.

## 2. `Carteira` — estrutura definida

| Atributo | Tipo | Mutabilidade |
|---|---|---|
| `usuario` | `Usuario` | Definido no construtor, só leitura |
| `saldo` | double | Privado, atualizado só internamente, inicia em zero |
| `movimentacoes` | `List<Movimentacao>` | Privado, exposição somente-leitura planejada (pendente) |
| `proximoId` | int | Privado, incrementado a cada novo registro, controla o id das movimentações |

**Decisões importantes:**

- **Sem `id` próprio:** a `Carteira` é localizada através do `Usuario` (relação 1:1), dispensando um identificador independente.
- **Criação de movimentações controlada pela própria `Carteira`:** os métodos `registrarEntrada()`/`registrarSaida()` recebem os dados soltos (valor, data, descrição, categoria, forma de pagamento quando aplicável) e a própria `Carteira` monta o objeto internamente, atribuindo o `id` via o contador `proximoId`. Essa decisão evita dois riscos: (1) que o mesmo objeto de movimentação seja registrado duas vezes por engano, e (2) que a geração de id fique espalhada fora da entidade responsável pelas movimentações.
- **`proximoId` como contador dedicado**, em vez de derivar do tamanho da lista — mais seguro a longo prazo, já que não reaproveita números mesmo se uma movimentação for excluída no futuro.
- **Validação de saldo não-negativo implementada:** `registrarSaida()` recusa a operação se o valor solicitado for maior que o saldo disponível. Atualmente a comunicação dessa recusa usa `System.out.println()`, marcado como **solução provisória** — pertence à camada `application`/interface, não ao `domain`, e deve ser revisitada quando essa camada for formalizada (ex.: lançar uma exceção customizada, tratada por quem chama o método).
- **Construtor da `Carteira` recebe apenas `Usuario`**, sem parâmetro de saldo inicial — toda carteira nova nasce zerada; o único jeito de ter saldo é através de movimentações reais, garantindo rastreabilidade.

**Pendência técnica:** a lista `movimentacoes` ainda não é inicializada (`new ArrayList<>()`) nem populada dentro de `registrarEntrada()`/`registrarSaida()` — os objetos `Entrada`/`Saida` são criados, mas descartados sem serem adicionados à lista. Essa parte fica **pausada intencionalmente** até as aulas 166-168 (Coleções — List) serem assistidas.

## 3. `Usuario` — estrutura definida

| Atributo | Tipo | Mutabilidade |
|---|---|---|
| `id` | int/long | Definido no construtor, só leitura |
| `email` | String | Definido no construtor, só leitura |
| `senha` | String | Definido no construtor, só leitura (texto plano — débito técnico já documentado) |
| `carteira` | `Carteira` | Criada automaticamente dentro do construtor, só leitura via `getCarteira()` |

**Decisões importantes:**

- **`Usuario` cria sua própria `Carteira` automaticamente no construtor** (`this.carteira = new Carteira(this)`), garantindo estruturalmente que nunca existirá um `Usuario` sem `Carteira` — a garantia nasce da própria construção do objeto, não de uma convenção externa.
- **`id` mantido além do `email`**, por dois motivos: (1) segurança/estabilidade — um identificador técnico que não muda mesmo que o email do usuário seja alterado no futuro; (2) compatibilidade com banco de dados — `id` numérico auto-incrementado é o padrão para chave primária, mais eficiente que indexar por `String`.
- **`email` funciona como identificador de negócio** (usado no login), enquanto `id` é o identificador técnico/interno.

**Pendência técnica:** ainda não existe um mecanismo central para gerar os `id`s de `Usuario` (diferente da `Carteira`, que já resolve isso internamente via `proximoId`). Provável solução futura: um "repositório" ou gerenciador de usuários na camada `application`, responsável por controlar um contador global de ids — fica como problema a resolver quando essa camada for criada, não como bloqueio atual.

---

## 4. Débitos técnicos e pendências (atualizados)

- **Lista de movimentações não inicializada/populada** — aguardando aulas 166-168 (List).
- **`System.out.println()` na validação de saldo** — provisório, deve migrar para a camada `application` (ex.: exceção customizada) quando essa camada for formalizada.
- **Geração de `id` de `Usuario`** — ainda sem mecanismo definido; resolução prevista para quando a camada `application`/repositório for criada.
- **Consistência de nomenclatura `Saida` vs. `DESPESA`** (enum `TipoCategoria`) — avaliar renomeação em cadeia.
- Débitos já documentados anteriormente seguem válidos: `double` vs. `BigDecimal`, senha em texto plano vs. BCrypt, `private` sem `final`.

---

## 5. Próximos passos sugeridos

- [ ] Assistir aulas 166-168 (Coleções — List) para inicializar e popular corretamente `movimentacoes` na `Carteira`, além de implementar a exposição somente-leitura.
- [ ] Decidir sobre a consistência de nomenclatura `Saida`/`DESPESA`.
- [ ] Avançar para o diagrama de classes formal, agora com o modelo de domínio completo (`Categoria`, `TipoCategoria`, `FormaPagamento`, `Movimentacao`, `Entrada`, `Saida`, `Carteira`, `Usuario`).
- [ ] Planejar a camada `application`: pontos já identificados como pertencentes a ela — tratamento de saldo insuficiente (hoje via `println`) e geração de id de `Usuario`.

---

# Relatório de Progresso — BankFlow

**Data:** 17 de julho de 2026

---

## Resumo do dia

Com o design de domínio já fechado no papel (`Categoria`, `Movimentacao`, `Entrada`, `Saida`, `Carteira`, `Usuario`), o foco do dia foi a **primeira implementação em código** dessas classes, seguida de um code review para identificar e corrigir problemas reais de encapsulamento e lógica — antes mesmo de o curso ter coberto formalmente herança (aulas 71-75) e coleções (aulas 166-168).

## 1. Código implementado e revisado

Todas as classes de domínio foram escritas em Java: `Categoria`, `TipoCategoria` (enum, já ajustado para `ENTRADA`/`SAIDA`, resolvendo a pendência de nomenclatura), `FormaPagamento` (enum), `Movimentacao`, `Entrada`, `Saida`, `Carteira` e `Usuario`.

**Pontos corrigidos durante a revisão:**

- **`Saida` (antes `Despesa`):** faltava `extends Movimentacao` na primeira versão enviada — corrigido para herdar corretamente da superclasse.
- **`Entrada`/`Saida`:** construtores implementados aplicando `super(...)` para repassar os atributos herdados à superclasse `Movimentacao`, e `this.formaPagamento` para o atributo próprio de `Saida` — aplicado com sucesso mesmo antes das aulas formais de herança.
- **`Usuario` — geração de `id`:** a primeira versão tentava um contador `proximoId` como atributo de instância dentro do próprio `Usuario`, o que resultaria em cada objeto tendo sua própria cópia do contador (todo `Usuario` novo receberia `id = 1`). Corrigido: `id` passou a ser recebido como parâmetro do construtor, com a geração automática marcada como pendência para quando a camada `application`/repositório for criada.
- **`Usuario` — perda da referência à `Carteira`:** a primeira versão criava a `Carteira` como variável local dentro do construtor (`Carteira carteira = new Carteira(this)`), sem armazená-la em um atributo da classe — a referência era perdida ao final do construtor. Corrigido: `carteira` passou a ser atributo da classe, preenchido via `this.carteira = new Carteira(this)`, acessível através de `getCarteira()`.
- **`Usuario` — getters ausentes:** adicionados `getId()`, `getEmail()`, `getCarteira()`.
- **`Usuario` — `getSenha()` removido:** identificado como falha de encapsulamento — um getter público de senha permite que qualquer parte do sistema leia a senha em texto plano sem necessidade real. Decisão tomada: remover o getter agora; a implementação de um método `autenticar(senhaDigitada)` (que compararia a senha internamente, sem nunca expô-la) foi discutida e definida como a abordagem correta, mas **fica para uma sessão futura** — não implementada hoje.

## 2. Decisão de design discutida (não implementada)

- **Autenticação encapsulada:** ao invés de expor a senha via getter, `Usuario` deveria oferecer um método de comportamento (`autenticar(senhaDigitada)`, retornando `boolean`), seguindo o mesmo padrão já usado em `Carteira` (expor comportamento controlado, não o dado bruto). Essa abordagem também absorve com transparência a futura migração de senha em texto plano para hash (BCrypt), já que a assinatura do método não mudaria — só sua implementação interna. **Pendente de implementação.**

---

## 3. Débitos técnicos e pendências (atualizados)

- **Lista de movimentações não inicializada/populada** — aguardando aulas 166-168 (List).
- **`System.out.println()` na validação de saldo** — provisório, deve migrar para a camada `application` quando essa camada for formalizada.
- **Geração de `id` de `Usuario`** — ainda manual via parâmetro do construtor; mecanismo automático fica para a camada `application`/repositório.
- **Método `autenticar(senhaDigitada)` em `Usuario`** — discutido e definido como abordagem correta, ainda não implementado.
- Débitos já documentados anteriormente seguem válidos: `double` vs. `BigDecimal`, senha em texto plano vs. BCrypt, `private` sem `final`.

---

## 4. Próximos passos sugeridos

- [ ] Implementar `autenticar(senhaDigitada)` em `Usuario`.
- [ ] Assistir aulas 166-168 (Coleções — List) para inicializar e popular `movimentacoes` na `Carteira`, com exposição somente-leitura.
- [ ] Avançar para o diagrama de classes formal, com o modelo de domínio completo e já implementado em código.
- [ ] Planejar a camada `application`: saldo insuficiente (hoje via `println`) e geração de id de `Usuario`.
- [ ] Realizar commit do progresso de hoje.

---

*Relatório gerado automaticamente com base nos arquivos de projeto do dia.*
