package com.thor.spring.batch.fileconvert2;

import com.thor.spring.batch.fileconvert2.config.TaskletsConfig;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FileConvert2AppMain {

    static Job jobObj;
    static JobLauncher jobLauncherObj;
    static ApplicationContext contextObj;
    private static String[] springConfig  = {"spring/batch/jobs/file-convert-job-spring-context.xml"};

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    public static void main(String[] args) {

        // Spring Java config
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(TaskletsConfig.class);
        context.refresh();

        final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        final Job job = (Job) context.getBean("firstBatchJob");
        System.out.println("Starting the batch job");
        try {
            final JobExecution execution = jobLauncher.run(job, new JobParameters());
            System.out.println("Job Status : " + execution.getStatus());
            System.out.println("Job succeeded");
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("Job failed");
        }
    }
}