package ru.gb.chat.client;

import ru.gb.chat.library.Library;
import ru.gb.jtwo.network.SocketThread;
import ru.gb.jtwo.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, SocketThreadListener { // создаём класс ClientGUI наследник JFrame окно с рамкой реализуем интерфейс ActionListener(слушатель)/Thread.UncaughtExceptionHandler(вывод ошибки над консолью)/SocketThreadListener(слушатель сокетов)

    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3)); //  создали JPanel где сказали что 2 строки и 3 колонны
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss: ");
    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;

    SocketThread socketThread;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // @REVLON вопрос почему не просто new Runnable
            @Override
            public void run() { //Запустили поток
                new ClientGUI(); // создали в нём новый объект ClientGUI
            }
        });
    }

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this); // выводим исключение в окно с клииентом, this, отмечаем какого объекта ошибки ловить
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // даём стандартную возможность закрывать окно
        setLocationRelativeTo(null); // расположение окна если null то левый верхний край по центру
        setSize(WIDTH, HEIGHT); // задали размеры окна
        log.setEditable(false); //запретили редактировать log окно где сами сообщения в чате
        JScrollPane scrollLog = new JScrollPane(log); // создали панель где разместили log окно где сами сообщения в чате
        JScrollPane scrollUser = new JScrollPane(userList); // создали окно где раместили списки пользователей
        String[] users = {"user1", "user2", "user3", "user4", "user5",
                "user_with_an_exceptionally_long_name_in_this_chat"}; // создали список для userList
        userList.setListData(users); // закинули список пользователей в userList
        scrollUser.setPreferredSize(new Dimension(100, 0)); // задали размер окна где список юзеров scrollUser
        cbAlwaysOnTop.addActionListener(this); // добавили слушателя галке всегда ли сверху окно
        btnSend.addActionListener(this); // добавили слушателя кнопке отправить сообщение
        tfMessage.addActionListener(this); // добавили слушателя окну для ввода сообщения
        btnLogin.addActionListener(this); // добавили слушателя кнопке логин / подключится
        btnDisconnect.addActionListener(this); // добавили слушателя кнопке отключится

        panelTop.add(tfIPAddress);  // добавили в верхнюю панель окно для ввода ip
        panelTop.add(tfPort); // добавили в верхнюю панель окно для ввода port
        panelTop.add(cbAlwaysOnTop); // добавили в верхнюю панель галку сверху ли окно
        panelTop.add(tfLogin); // добавили в верхнюю панель окно для ввода логина
        panelTop.add(tfPassword);// добавили в верхнюю панель окно для ввода пароля
        panelTop.add(btnLogin);// добавили в верхнюю панель кнопку логин

        panelBottom.add(btnDisconnect, BorderLayout.WEST); // + кнопка дисконет внижнюю панель указывая расположение
        panelBottom.add(tfMessage, BorderLayout.CENTER); // + окно для ввода сообщения по центру в нижнюю панель
        panelBottom.add(btnSend, BorderLayout.EAST);// + кнопка отправить сообщение в нижнюю панель
        panelBottom.setVisible(false); // сделали панель нижнюю не видимой

        add(scrollLog, BorderLayout.CENTER); // разместил панели указав их расположение
        add(scrollUser, BorderLayout.EAST);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true); // отобразили внутри окна объекты
    }


    @Override
    public void actionPerformed(ActionEvent e) { // реализовали, унаследованный метод от ActionListener которые принимает события от слушателя
        Object src = e.getSource(); // getSource запрашиваем объект с которым первоночально произошло событие и присваиваем src
        if (src == cbAlwaysOnTop) { // слушаем что проиозошли и взависимости от этого действуем
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnLogin) {
            connect();
        } else if (src == btnDisconnect) {
            socketThread.close();
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }


    }

    private void connect() {
        try {
            Socket socket = new Socket(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread("Client", this, socket);
        } catch (IOException exception) {
            showException(Thread.currentThread(), exception);
        }
    }

    private void sendMessage() {
        String msg = tfMessage.getText();
        if ("".equals(msg)) return;
        tfMessage.setText(null);
        tfMessage.grabFocus();
        socketThread.sendMessage(msg);
    }

    private void wrtMsgToLogFile(String msg, String username) {
        try (FileWriter out = new FileWriter("log.txt", true)) {
            out.write(username + ": " + msg + "\n");
            out.flush();
        } catch (IOException e) {
            if (!shownIoErrors) {
                shownIoErrors = true;
                showException(Thread.currentThread(), e);
            }
        }
    }

    private void putLog(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    private void showException(Thread t, Throwable e) {
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0)
            msg = "Empty Stacktrace";
        else {
            msg = String.format("Exception in \"%s\" %s: %s\n\tat %s",
                    t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
        }
        JOptionPane.showMessageDialog(null, msg, "Exception", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        showException(t, e);
        System.exit(1);
    }

    /**
     * Socket Threead Listener Methods
     * */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Start");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("Stop");
        panelBottom.setVisible(false);
        panelTop.setVisible(true);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Ready");
        panelBottom.setVisible(true);
        panelTop.setVisible(false);
        String login = tfLogin.getText();
        String password = new String(tfPassword.getPassword());
        thread.sendMessage(Library.getAuthRequest(login, password));
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        String[] msgArr;
        msgArr = msg.split("±");
        if(msgArr[0].equals("/bcast")) { //Для сообщений, отправляемых всем
            putLog(DATE_FORMAT.format(new Long(msgArr[1])) + " " + msgArr[3]);
        } else {
            putLog("Тип сообщения, не распознан!");
        }
        //putLog(msg);
    }

    @Override
    public void onSocketException(SocketThread thread, Throwable throwable) {
        showException(thread, throwable);
        thread.close();
    }
}
