package org.softuni.mobilelele.service.impl;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.softuni.mobilelele.service.MonitoringService;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final Counter counter;

    public MonitoringServiceImpl(MeterRegistry meterRegistry) {
        this.counter = Counter
                .builder("offer_search_cnt")
                .description("How many offer searches we have performed")
                .register(meterRegistry);
    }

    @Override
    public void logOfferSearch() {
        this.counter.increment();
    }
}
