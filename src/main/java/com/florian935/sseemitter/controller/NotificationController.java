package com.florian935.sseemitter.controller;

import com.florian935.sseemitter.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1.0/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService notificationService;

    @GetMapping("/{data}")
    @ResponseStatus(OK)
    public SseEmitter doNotify(@PathVariable String data) {

        final SseEmitter emitter = new SseEmitter(60 * 60 * 1000L);
        notificationService.addEmitter(emitter);
        notificationService.doNotify(data);
        emitter.onCompletion(() -> notificationService.removeEmitter(emitter));
        emitter.onTimeout(() -> notificationService.removeEmitter(emitter));

        return emitter;
    }
}
