package com.cfa.letter;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
@EnableBatchIntegration
@EnableBatchProcessing
public class WorkerConfiguration {
    @Autowired
    private RemoteChunkingWorkerBuilder remoteChunkingWorkerBuilder;
    @Autowired
    private DirectChannel incomingRequestsFromManager;
    @Autowired
    private DirectChannel outgoingRepliesToManager;

    @Bean
    public IntegrationFlow workerFlow() {
        return this.remoteChunkingWorkerBuilder
                .itemProcessor(new LetterProcessor())
                .itemWriter(new LetterWriter())
                .inputChannel(incomingRequestsFromManager)
                .outputChannel(outgoingRepliesToManager)
                .build();
    }

}
