package top.hiccup;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;
import javax.swing.*;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.*;
import javax.media.util.*;
import javax.media.format.*;

import com.sun.image.codec.jpeg.*;

/**
 * 视频通话
 *
 * @author wenhy
 * @date 2019/10/16
 */
public class VAplay extends JFrame implements Runnable {

    //定义视频图像播放器
    private static Player player = null;
    //定义音频播放器
    private static Player player2 = null;
    //获取视频设备
    private CaptureDeviceInfo device = null;
    //获取音频设备
    private CaptureDeviceInfo device2 = null;
    //媒体定位器
    private MediaLocator locator = null;

    private Image image;

    private Buffer buffer = null;

    private BufferToImage b2i = null;

    //设置摄像头驱动类型
    String str = "vfw:Microsoft WDM Image Capture (Win32):0";

    //定义播放组件变量
    Component comV, comVC, comA;

    //定义面板
    JPanel p1, p2, p3, p4;

    JLabel label = new JLabel("对方ＩＰ：");

    //初始时，在接收图像窗口显示一幅静态图片
    JLabel label2 = new JLabel(new ImageIcon("image//load.gif"));

    //定义二个线程，用于接收数据和发送数据
    Thread thread1, thread2;


    VAplay() {
        super("★视频传输★");
        setBounds(150, 100, 500, 500);
        p1 = new JPanel(new GridLayout(1, 2));
        p2 = new JPanel(new GridLayout(2, 1));
        p3 = new JPanel(new BorderLayout());
        p4 = new JPanel(new BorderLayout());

        //加载文本数据传输类

        p1.add(new TxtChat());

        p1.add(p2);

        p2.add(p3);

        p2.add(p4);

        p3.add("North", new JLabel("Java视频图像传输"));

        p3.add(label2, "Center");

        add(label, "North");

        add(p1, "Center");

        try {

            //在本地播放视频

            jbInit();

            //在本地播放音频

            speaker();

        } catch (Exception e) {

            e.printStackTrace();

        }

        thread1 = new Thread(this);

        thread2 = new Thread(this);

//负责接收对方数据

        thread1.start();

//负责向对方发送数据

        thread2.start();


        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);

            }

        });

        setVisible(true);

        validate();

    }


    //在本地播放视频
    private void jbInit() throws Exception {
        //初始化设备，str为设备驱动
        device = CaptureDeviceManager.getDevice(str);
        //确定所需的协议和媒体资源的位置
        locator = device.getLocator();

        try {

            //调用sethint后Manager会尽力用一个能和轻量级组件混合使用的Renderer来创建播放器

            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));

            //通过管理器创建播放线程使player达到Realized状态

            player = Manager.createRealizedPlayer(locator);

            player.start();


            if ((comV = player.getVisualComponent()) != null)

            //player.getVisualComponent()是一个播放视频媒体组件。

            {

                p4.add(comV, "Center");

            }

            if ((comVC = player.getControlPanelComponent()) != null)

            //player.getControlPanelComponent()是显示时间的组件

            {

                p4.add(comVC, "South");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        setBounds(200, 100, 600, 550);

        setVisible(true);

        int new_w = p4.getWidth(); //输出的图像宽度

        int new_h = p4.getHeight(); //输出的图像高度

//MediaTracker类跟踪一个Image对象的装载，完成图像加载

        MediaTracker mt = new MediaTracker(this.p4);

        try {

            mt.addImage(image, 0);//装载图像

            mt.waitForID(0);//等待图像全部装载

        } catch (Exception e) {

            e.printStackTrace();

        }

//将图像信息写入缓冲区

        BufferedImage buffImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);

        Graphics g = buffImg.createGraphics();

        g.drawImage(image, 0, 0, new_w, new_h, this.p4);

        g.dispose();

    }


//在本地播放音频

    private void speaker() throws Exception {

        Vector deviceList = CaptureDeviceManager.getDeviceList(new

                AudioFormat(AudioFormat.LINEAR, 44100, 16, 2));

        if (deviceList.size() > 0) {

            device2 = (CaptureDeviceInfo) deviceList.firstElement();

        } else {

            System.out.println("找不到音频设备！");

        }

        try {

            player2 = Manager.createRealizedPlayer(device2.getLocator());

            player2.start();

            if ((comA = player2.getControlPanelComponent()) != null) {

                p3.add(comA, "South");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


//线程，接收或发送视频图像数据

    public void run() {

        DatagramPacket pack = null;

        DatagramSocket maildata = null;

        byte data[] = new byte[320 * 240];

        try {

//定义数据包

            pack = new DatagramPacket(data, data.length);

//定义数据报接收包

            maildata = new DatagramSocket(999);

        } catch (Exception e) {
        }

        while (true) {

            if (Thread.currentThread() == thread1) {

                if (maildata == null) {

                    break;

                } else {

                    try {

                        //接收

                        maildata.receive(pack);

                        ByteArrayInputStream input = new ByteArrayInputStream(data);

                        Image message = ImageIO.read(input);

                        //在接收图像窗口显示视频图像

                        label2.setIcon(new ImageIcon(message));

                        label.setText("对方ＩＰ：" + pack.getAddress() +

                                " 端口：" + pack.getPort());

                    } catch (Exception e) {

                        System.out.println("接收图像数据失败！");

                    }

                }

            } else if (Thread.currentThread() == thread2) {

                try {

                    //捕获要在播放窗口显示的图象帧

                    FrameGrabbingControl fgc = (FrameGrabbingControl) player.getControl(

                            "javax.media.control.FrameGrabbingControl");

                    // 获取当前祯并存入Buffer类

                    buffer = fgc.grabFrame();

                    b2i = new BufferToImage((VideoFormat) buffer.getFormat());

                    image = b2i.createImage(buffer); //转化为图像

                    //创建image图像对象大小的图像缓冲区

                    BufferedImage bi = (BufferedImage) createImage(image.getWidth(null),

                            image.getHeight(null));

                    //根据BufferedImage对象创建Graphics2D对象

                    Graphics2D g2 = bi.createGraphics();

                    g2.drawImage(image, null, null);

                    ByteArrayOutputStream output = new ByteArrayOutputStream();

                    //转换成JPEG图像格式

                    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);

                    JPEGEncodeParam jpeg = encoder.getDefaultJPEGEncodeParam(bi);

                    jpeg.setQuality(0.5f, false);

                    encoder.setJPEGEncodeParam(jpeg);

                    encoder.encode(bi);

                    output.close();

                    InetAddress address = InetAddress.getByName("localhost");

                    DatagramPacket datapack1 = new DatagramPacket(output.toByteArray(), output.size(),

                            address, 555);

                    DatagramSocket maildata1 = new DatagramSocket();

                    maildata1.send(datapack1);

                    Thread.sleep(400);

                } catch (Exception e) {

                    System.out.println("视频发送失败！");

                }

            }

        }

    }


    public static void main(String args[]) {

        new VAplay();

    }

}


class TxtChat extends JPanel implements ActionListener, Runnable {

    JPanel p1, p2, p3, p4;

    JLabel jpic;

    JTextArea txt1, txt2;

    JButton btn1, btn2;

    JScrollPane scroll, scroll2;

    Thread thread;

    boolean boo1 = false, boo2 = false;


    TxtChat() {

        setLayout(new BorderLayout());


        txt1 = new JTextArea();

        txt2 = new JTextArea();

        p1 = new JPanel(new GridLayout(2, 1));

        p2 = new JPanel();

        p3 = new JPanel(new BorderLayout());

        jpic = new JLabel(new ImageIcon("image//upload.gif"));

        btn1 = new JButton("电子文档");

        btn2 = new JButton("发送信息");


        scroll = new JScrollPane(txt1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,

                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scroll2 = new JScrollPane(txt2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,

                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        p1.add(scroll);

        p1.add(p3);

        p2.add(btn1);

        p2.add(btn2);

        p3.add(jpic, "North");

        p3.add(scroll2, "Center");


        add(p1, "Center");

        add(p2, "South");


        setVisible(true);

        Font f = new Font("", Font.PLAIN, 18);

        txt1.setFont(f);

        txt1.setForeground(Color.red);

        txt2.setFont(f);

        txt2.setForeground(Color.blue);

        btn1.setBackground(Color.cyan);

        btn2.setBackground(Color.yellow);


        btn1.addActionListener(this);

        btn2.addActionListener(this);


        thread = new Thread(this);

        thread.start();


    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btn2) {

            byte buffer[] = txt2.getText().trim().getBytes();

            try {

                InetAddress address = InetAddress.getByName("localhost");

                DatagramPacket data_pack = new DatagramPacket(buffer, buffer.length, address, 888);

                DatagramSocket mail_data = new DatagramSocket();

                txt1.append("我说：" + txt2.getText() + '\n');

                mail_data.send(data_pack);

                txt2.setText("");

            } catch (Exception e1) {

                System.out.println("聊天信息发送失败！");

            }

        }

    }


    public void run() {

        DatagramPacket pack = null;

        DatagramSocket mail_data = null;

        byte data[] = new byte[8192];

        try {

            pack = new DatagramPacket(data, data.length);

            mail_data = new DatagramSocket(666);

        } catch (Exception e) {
        }

        while (true) {

            if (Thread.currentThread() == thread) {

                if (mail_data == null) {
                    break;
                } else {

                    try {

                        mail_data.receive(pack);

                        int length = pack.getLength();

                        String message = new String(pack.getData(), 0, length);

                        txt1.append("某某说：" + message + '\n');

                    } catch (Exception e) {

                        System.out.println("接收数据失败！");

                    }

                }

            }

        }// while end

    } //run() end

}
