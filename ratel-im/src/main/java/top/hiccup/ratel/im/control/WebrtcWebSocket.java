package top.hiccup.ratel.im.control;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * 接收ws://协议的请求
 * 
 * 用于 处理webrtc请求
 * 
 * @author graceup<br/>
 * @version 1.0<br/>
 * @email: charmails@163.com<br/>
 */
public class WebrtcWebSocket extends WebSocketServlet {

	private static final long serialVersionUID = 5221899681376313963L;

	@Override
	public void configure(WebSocketServletFactory webSocketServletFactory) {

	}
}
