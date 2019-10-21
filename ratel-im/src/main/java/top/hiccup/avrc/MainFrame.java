package top.hiccup.avrc;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.media.*;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

/**
 * 音视频实时聊天
 *
 * @author wenhy
 * @date 2019/10/16
 */
public class MainFrame extends JFrame {

    // 定义音频播放器
    private static Player audioPlayer = null;
    // 定义视频图像播放器
    private static Player videoPlayer = null;
    // 获取音频设备
    private CaptureDeviceInfo audioDevice = null;
    // 获取视频设备
    private CaptureDeviceInfo videoDevice = null;
    // 媒体定位器
    private MediaLocator locator = null;

    private Image image;
    private Buffer buffer = null;
    private BufferToImage b2i = null;

    // 设置摄像头驱动类型
    private static String str = "vfw:Microsoft WDM Image Capture (Win32):0";

    //定义播放组件变量
    Component comV, comVC, comA;

    //定义面板
    JPanel p1, p2, p3, p4;

    JLabel label = new JLabel("对方ＩＰ：");

    //初始时，在接收图像窗口显示一幅静态图片
    JLabel label2 = new JLabel(new ImageIcon("image//load.gif"));

    //定义二个线程，用于接收数据和发送数据
    Thread thread1, thread2;

    MainFrame() {
        super("音视频实时聊天");
        setBounds(350, 250, 900, 600);
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
        thread1 = new Thread(() -> {
            DatagramPacket pack = null;
            DatagramSocket maildata = null;
            byte data[] = new byte[320 * 240];
            while (true) {
                try {
                    //定义数据包
                    pack = new DatagramPacket(data, data.length);
                    //定义数据报接收包
                    maildata = new DatagramSocket(8083);

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            }
        });
        thread2 = new Thread(() -> {
            DatagramPacket pack = null;
            DatagramSocket maildata = null;
            byte data[] = new byte[320 * 240];
            try {
                //定义数据包
                pack = new DatagramPacket(data, data.length);
                //定义数据报接收包
                maildata = new DatagramSocket(999);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    //捕获要在播放窗口显示的图象帧
                    FrameGrabbingControl fgc = (FrameGrabbingControl) videoPlayer.getControl("javax.media.control.FrameGrabbingControl");
                    // 获取当前祯并存入Buffer类
                    buffer = fgc.grabFrame();
                    b2i = new BufferToImage((VideoFormat) buffer.getFormat());
                    image = b2i.createImage(buffer); //转化为图像
                    //创建image图像对象大小的图像缓冲区
                    BufferedImage bi = (BufferedImage) createImage(image.getWidth(null), image.getHeight(null));
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
                    DatagramPacket datapack1 = new DatagramPacket(output.toByteArray(), output.size(), address, 555);
                    DatagramSocket maildata1 = new DatagramSocket();
                    maildata1.send(datapack1);
                    Thread.sleep(400);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("视频发送失败！");
                }
            }
        });
        //负责接收对方数据
        thread1.start();
        //负责向对方发送数据
        thread2.start();
        addWindowListener(new WindowAdapter() {
            @Override
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
        videoDevice = CaptureDeviceManager.getDevice(str);
        //确定所需的协议和媒体资源的位置
        locator = videoDevice.getLocator();
        try {
            //调用sethint后Manager会尽力用一个能和轻量级组件混合使用的Renderer来创建播放器
            Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
            //通过管理器创建播放线程使player达到Realized状态
            videoPlayer = Manager.createRealizedPlayer(locator);
            videoPlayer.start();
            if ((comV = videoPlayer.getVisualComponent()) != null)
            //videoPlayer.getVisualComponent()是一个播放视频媒体组件。
            {
                p4.add(comV, "Center");
            }
            if ((comVC = videoPlayer.getControlPanelComponent()) != null)
            //videoPlayer.getControlPanelComponent()是显示时间的组件
            {
                p4.add(comVC, "South");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setBounds(200, 100, 600, 550);
        setVisible(true);
        int new_w = p4.getWidth(); // 输出的图像宽度
        int new_h = p4.getHeight(); // 输出的图像高度
        //MediaTracker类跟踪一个Image对象的装载，完成图像加载
        MediaTracker mt = new MediaTracker(this.p4);
        try {
            mt.addImage(image, 0);// 装载图像
            mt.waitForID(0);// 等待图像全部装载
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将图像信息写入缓冲区
        BufferedImage buffImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.createGraphics();
        g.drawImage(image, 0, 0, new_w, new_h, this.p4);
        g.dispose();
    }


    // 在本地播放音频
    private void speaker() throws Exception {
        Vector deviceList = CaptureDeviceManager.getDeviceList(new AudioFormat(AudioFormat.LINEAR, 44100, 16, 2));
        if (deviceList.size() > 0) {
            audioDevice = (CaptureDeviceInfo) deviceList.firstElement();
        } else {
            System.out.println("找不到音频设备！");
        }
        try {
            audioPlayer = Manager.createRealizedPlayer(audioDevice.getLocator());
            audioPlayer.start();
            if ((comA = audioPlayer.getControlPanelComponent()) != null) {
                p3.add(comA, "South");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        new MainFrame();
    }
}



