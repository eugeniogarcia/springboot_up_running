package com.thehecklers.aircraftpositions;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

//Crea un websocket handler, especificamente para gestionar el intercambio de texto
@Component
public class WebSocketHandler extends TextWebSocketHandler {
	//Guarda la lista de sesiones
    private final List<WebSocketSession> sessionList = new ArrayList<>();
    @NonNull
    private final AircraftRepository repository;

    public WebSocketHandler(@NonNull AircraftRepository repository) {
		super();
		this.repository = repository;
	}

	public List<WebSocketSession> getSessionList() {
        return sessionList;
    }

	//Ciclo de vida. Interviene cuando se crea una sesión
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        System.out.println("Connection established from " + session.toString() +
                " @ " + Instant.now().toString());
    }

	//Ciclo de vida. Interviene cuando se recibe un mensaje
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        try {
            System.out.println("Message received: '" + message + "', from " +
                    session.toString());

            for (WebSocketSession sessionInList : sessionList) {
                if (sessionInList != session) {
                	//Enviamos un mensaje
                    sessionInList.sendMessage(message);
                    System.out.println("--> Sending message '" + message + "' to " +
                            sessionInList.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception handling message: " + e.getLocalizedMessage());
        }
    }

	//Ciclo de vida. Gestiona el cierre de la sesión
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        sessionList.remove(session);
        System.out.println("Connection closed by " + session.toString() +
                " @ " + Instant.now().toString());
    }
}