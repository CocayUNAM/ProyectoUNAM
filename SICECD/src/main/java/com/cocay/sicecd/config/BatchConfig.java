package com.cocay.sicecd.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.repo.CursoBatchRep;
import com.cocay.sicecd.repo.GrupoBatchRep;
import com.cocay.sicecd.step.ProcessorCurso;
import com.cocay.sicecd.step.ProcessorGrupo;
import com.cocay.sicecd.step.ReaderCurso;
import com.cocay.sicecd.step.ReaderGrupo;
import com.cocay.sicecd.step.VerificacionArchivo;
import com.cocay.sicecd.step.WriterCurso;
import com.cocay.sicecd.step.WriterGrupo;


@Configuration
@EnableBatchProcessing
public class BatchConfig  {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
    private CursoBatchRep cursoRep;
	
	@Autowired
    private GrupoBatchRep grupoRep;
	
	@Bean
	public SkipPolicy fileVerificationSkipper() {
		return new VerificacionArchivo();
	}
	
//    @Bean
//    @Scope(value = "step1", proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public FlatFileItemReader<Curso> importReader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
//        FlatFileItemReader<Curso> reader = new FlatFileItemReader<>();
//        reader.setResource(new FileSystemResource(pathToFile));
//        reader.setLineMapper(new DefaultLineMapper<Curso>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//            	setNames(new String[] {"clave", "nombre", "horas"});
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<Curso>() {{
//                setTargetType(Curso.class);
//            }});
//        }});
//        return reader;
//    }

//	@Bean
//	public Job jobCurso(ItemReader<Curso> importReader) {
//		return jobBuilderFactory.get("jobCurso").incrementer(new RunIdIncrementer())
//				.flow(step1(importReader)).end().build();
//	}
//
//	@Bean
//	public Step step1(ItemReader<Curso> importReader) {
//		return stepBuilderFactory.get("step1").<Curso, Curso>chunk(2)
//				.reader(importReader)
//				.faultTolerant()
//				.skipPolicy(fileVerificationSkipper())
//				.processor(new ProcessorCurso()).writer(new WriterCurso(cursoRep)).build();
//	}
	
	@Bean
	public Job jobCurso() {
		return jobBuilderFactory.get("jobCurso").incrementer(new RunIdIncrementer())
				.flow(step1()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Curso, Curso>chunk(2)
				.reader(ReaderCurso.reader("curso-data.csv"))
				.faultTolerant()
				.skipPolicy(fileVerificationSkipper())
				.processor(new ProcessorCurso()).writer(new WriterCurso(cursoRep)).build();
	}
	
	@Bean
	public Job jobGrupo() {
		return jobBuilderFactory.get("jobGrupo").incrementer(new RunIdIncrementer())
				.flow(stepGrupo()).end().build();
	}

	@Bean
	public Step stepGrupo() {
		return stepBuilderFactory.get("stepGrupo").<Grupo, Grupo>chunk(2)
				.reader(ReaderGrupo.reader("grupo-data.csv"))
				.processor(new ProcessorGrupo()).writer(new WriterGrupo(grupoRep)).build();
	}
}