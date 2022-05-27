package com.cfa.letter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableBatchIntegration
@EnableBatchProcessing
public class ManagerConfiguration {
    @Autowired
    private RemoteChunkingManagerStepBuilderFactory managerStepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private DirectChannel outgoingRequestsToWorkers;
    @Autowired
    private QueueChannel incomingRepliesFromWorkers;

    @Bean
    public Job letterJob() {
        return jobBuilderFactory
                .get("letterJob")
                .start(readFileStep())
                .build();
    }

    @Bean
    public Step readFileStep() {
        return this.managerStepBuilderFactory.get("readFileStep")
                .chunk(10)
                .reader(reader())
                .outputChannel(outgoingRequestsToWorkers) // requête aux workers
                .inputChannel(incomingRepliesFromWorkers) // réponse des workers
                .build();
    }


    @Bean
    public FlatFileItemReader<String> reader() {
        FlatFileItemReader<String> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("C:/Intel/test.txt"));
        reader.setLineMapper(new PassThroughLineMapper());
        return reader;
    }
}
