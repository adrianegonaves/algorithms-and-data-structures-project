import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaSimulacao {
    public static void main(String[] args) {
        // 1. Criar a janela (JFrame)
        JFrame frame = new JFrame("Sistema de Gestão de Autocarros");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // 2. Criar os componentes
        JTextArea areaTexto = new JTextArea("Estado atual da linha aparecerá aqui...\n");
        areaTexto.setEditable(false); // O utilizador não edita diretamente
        
        JButton botaoSimular = new JButton("Simular Chegada de Autocarro");

        // 3. Adicionar lógica ao botão (Ação)
        botaoSimular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui chamarias os métodos da tua lista ligada e fila!
                areaTexto.append("Autocarro chegou à paragem. Passageiros a embarcar...\n");
            }
        });

        // 4. Organizar os componentes no ecrã
        frame.getContentPane().add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        frame.getContentPane().add(botaoSimular, BorderLayout.SOUTH);

        // 5. Mostrar a janela
        frame.setVisible(true);
    }
}