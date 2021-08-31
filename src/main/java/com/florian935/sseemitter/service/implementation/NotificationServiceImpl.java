package com.florian935.sseemitter.service.implementation;

import com.florian935.sseemitter.service.NotificationService;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    @Override
    public void addEmitter(SseEmitter sseEmitter) {
        emitters.add(sseEmitter);
    }

    @Override
    public void removeEmitter(SseEmitter sseEmitter) {
        emitters.remove(sseEmitter);
    }

    @Override
    public void doNotify(String data) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .data(DATE_FORMATTER.format(new Date()) + " : " + data));
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });
        emitters.removeAll(deadEmitters);
    }
}
