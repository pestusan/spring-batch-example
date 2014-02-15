package org.ape.org.ape.example.spring_batch;

import org.ape.org.ape.example.spring_batch.domain.TOObject;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Bean
	public ItemReader<TOObject> reader() {
		FlatFileItemReader<TOObject> reader = new FlatFileItemReader<TOObject>();
		reader.setResource(new ClassPathResource("TOObject.csv"));
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(getInputColumns());
		BeanWrapperFieldSetMapper<TOObject> beanWrapper = new BeanWrapperFieldSetMapper<TOObject>();
		beanWrapper.setTargetType(TOObject.class);
		DefaultLineMapper<TOObject> lineMapper = new DefaultLineMapper<TOObject>();
		lineMapper.setFieldSetMapper(beanWrapper);
		lineMapper.setLineTokenizer(tokenizer);
		return reader;
	}
	
	public ItemWriter<TOObject> writer() {
		FlatFileItemWriter<TOObject> writer = new FlatFileItemWriter<TOObject>();
		writer.setResource(new ClassPathResource("Output.txt"));
		return writer;
	}
	
	@Bean
	public ItemProcessor<TOObject, TOObject> processor() {
		return new TOObjectItemProcessor();
	}
	
    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .build();
    }
	
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<TOObject> reader,
            ItemWriter<TOObject> writer, ItemProcessor<TOObject, TOObject> processor) {
        return stepBuilderFactory.get("step1")
                .<TOObject, TOObject> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
	
	private String[] getInputColumns() {
		return new String[] {"id", "name", "description"};
	}
}
