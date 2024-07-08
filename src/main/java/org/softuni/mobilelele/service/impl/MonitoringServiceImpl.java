package org.softuni.mobilelele.service.impl;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softuni.mobilelele.service.MonitoringService;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final Logger LOGGER = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    private final Counter offerSearches;

    public MonitoringServiceImpl(MeterRegistry meterRegistry) {
        this.offerSearches = Counter
                .builder("offer_search_cnt")
                .description("How many offer searches we have performed")
                .register(meterRegistry);
    }

    @Override
    public void logOfferSearch() {

        LOGGER.info("Offer search was performed.");

        this.offerSearches.increment();
    }
}
