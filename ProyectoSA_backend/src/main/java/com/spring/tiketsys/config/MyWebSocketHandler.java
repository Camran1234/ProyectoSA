package com.spring.tiketsys.config;

import com.spring.tiketsys.service.History_of_CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

public class MyWebSocketHandler implements WebSocketHandler {

    private final DirectProcessor<String> messageProcessor = DirectProcessor.create();
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // Suscribirse al procesador de mensajes para recibir mensajes y enviarlos a través del WebSocket
        Mono<Void> outgoing = session.send(
                messageProcessor.map(session::textMessage)

        );

        // Procesar los mensajes entrantes del cliente
        Mono<Void> incoming = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(message -> {
                    // Emitir el mensaje a todos los suscriptores
                    messageProcessor.onNext(message);
                })
                .then();

        // Combinar los flujos de entrada y salida en un único flujo Mono<Void>
        return Mono.zip(incoming, outgoing).then();
    }
}
