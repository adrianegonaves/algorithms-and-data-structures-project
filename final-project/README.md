# 🚌 Bus Simulation System
**Trabalho Final | Algoritmos e Estruturas de Dados**

Este projeto consiste num sistema de simulação de transporte público utilizando conceitos desenvolvidos em aula.

---

## 👥 Equipa
* **Adriane Gonçalves** - 240000004
* **Bruno Hortelão** - 240001083

---

## 🌐 Padronização Linguística (English)
Optamos por desenvolver o código e a documentação técnica em **Inglês**.

**Motivação:** Devido às variações regionais do português (ex: *Autocarro* em Portugal vs. *Ônibus* no Brasil), o inglês foi escolhido como língua neutra. Esta decisão garante a consistência da terminologia técnica no código e facilita a colaboração entre os membros do grupo.

---

## ✅ Checklist de Implementação

### 1. 👤 Classe `Passenger`
- [ ] Atributos básicos (name).
- [ ] Construtor e métodos *get/set*.

### 2. 👥 Classe `PassengerQueue`
- [ ] Implementação da estrutura de Fila (FIFO).
- [ ] Método `enqueue` (adicionar passageiro à fila).
- [ ] Método `dequeue` (remover passageiro quando entra no autocarro).
- [ ] Verificação de estado (fila vazia ou cheia).

### 3. 🚌 Classe `Bus`
- [ ] Atributos (Capacidade máxima, lista de passageiros atuais).
- [ ] Método `board(Passenger)`: Adicionar passageiro se houver lugar.
- [ ] Método `disembark(Stop)`: Desembarcar passageiros que chegaram ao destino.
- [ ] Controle de rota e paragem atual.

### 4. 🚏 Classe `BusStop`
- [ ] Atributo para identificação da paragem.
- [ ] Instância de `PassengerQueue` associada à paragem.
- [ ] Lógica de interface entre a fila de espera e o autocarro.


"De acordo vamos colocando as outras classes

---

