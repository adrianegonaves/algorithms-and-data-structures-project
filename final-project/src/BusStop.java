/**
 * @file: BusStop.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Representa um nó individual da DLL que
 *               constitui a linha de autocarros. Cada instância armazena o
 *               nome da paragem,
 *               os ponteiros de conectividade para os nós adjacentes ('next' e
 *               'previous') e uma instância de 'PassengerQueue'
 *               para gerir localmente os passageiros em fila.
 * @date: Junho de 2026
 * @version: 1.1
 * @authors:
 *           - Adriane Gonçalves - 240000004
 *           - Bruno Hortelão - 240001083
 * @institution: Instituto Politécnico de Santarém - Escola Superior de Gestão e Tecnologia
 * @course: Licenciatura em Engenharia Informática
 * @uc: Algoritmos e Estruturas de Dados
 */
public class BusStop {

    String name;
    PassengerQueue queue;

    BusStop previous;
    BusStop next;

    public BusStop(String name) {
        this.name = name;
        previous = null;
        next = null;
        queue = new PassengerQueue();
    }

    public String getName() {
        return name;
    }

    public void queue(Passenger passenger) {
        this.queue.enqueue(passenger);
    }

}
