/**
 * @file: Passenger.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Representa a entidade de um passageiro no sistema.
 *               Contém atributos básicos encapsulados, como o nome do
 *               utilizador, servindo como a unidade fundamental de
 *               dados que transita entre as paragens
 *               e o interior do autocarro.
 * @date: Junho de 2026
 * @version: 1.1
 * @authors:
 *           - Adriane Gonçalves - 240000004
 *           - Bruno Hortelão - 240001083
 * @institution: Instituto Politécnico de Santarém - Escola Superior de Gestão e
 *               Tecnologia
 * @course: Licenciatura em Engenharia Informática
 * @uc: Algoritmos e Estruturas de Dados
 */
public class Passenger {

    private String name;

    // construtor
    public Passenger(String name) {
        this.name = name;
    }

    // getters e setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}