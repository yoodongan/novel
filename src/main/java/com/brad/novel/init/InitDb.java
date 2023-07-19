package com.brad.novel.init;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Profile({"dev", "test"})
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init1();
        initService.init2();
    }
}
