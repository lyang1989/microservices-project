package com.princeli.micro.services.spring.cloud.client.event;

import javax.swing.*;
import java.awt.event.*;

/**
 * @program: microservices-project
 * @description: ${description}
 * @author: ly
 * @create: 2019-03-27 10:05
 **/
public class GUIEvent {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("简单GUI程序-Java 事件/监听");

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                System.out.printf("[%s]事件:%s\n",Thread.currentThread().getName(),event);
            }

        });


        frame.setBounds(300,300,400,300);

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
