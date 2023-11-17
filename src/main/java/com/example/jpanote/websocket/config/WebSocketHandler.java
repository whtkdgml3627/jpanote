package com.example.jpanote.websocket.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * packageName    : com.example.jpanote.websocket
 * fileName       : WebSockChatHandler
 * author         : 조 상 희
 * date           : 2023-11-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-08       조 상 희         최초 생성
 */

@Log4j2
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

	//클라이언트 연결이 성공했을 때 호출되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		CLIENTS.put(session.getId(), session);
		log.info("==================== afterConnectionEstablished ====================");
		log.info("WebSocket 연결 성공: " + session.getId());
		log.info("==================== //afterConnectionEstablished ====================");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		CLIENTS.remove(session.getId());
		log.info("==================== afterConnectionClosed ====================");
		log.info("WebSocket 연결 해지: " + session.getId());
		log.info("==================== //afterConnectionClosed ====================");
	}

	//클라이언트로부터 메시지를 수신했을 때 호출되는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		//메시지를 보낸 아이디
		String id = session.getId();
		String payload = message.getPayload();
		CLIENTS.entrySet().forEach( arg->{
			if(!arg.getKey().equals(id)) {  //같은 아이디가 아니면 메시지를 전달합니다.
				try {
					arg.getValue().sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		log.info("==================== handleTextMessage ====================");
		log.info("받은 메시지: " + payload);
		// 클라이언트에게 응답 메시지를 전송
		session.sendMessage(new TextMessage("서버 응답: " + payload));
		log.info("==================== //handleTextMessage ====================");
	}
}