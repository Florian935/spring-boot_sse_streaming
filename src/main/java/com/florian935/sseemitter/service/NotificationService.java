package com.florian935.sseemitter.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

    void addEmitter(SseEmitter sseEmitter);

    void removeEmitter(SseEmitter sseEmitter);

    void doNotify(String data);
}
