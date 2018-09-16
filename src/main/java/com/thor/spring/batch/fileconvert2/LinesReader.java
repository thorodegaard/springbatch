package com.thor.spring.batch.fileconvert2;

import com.thor.model.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;

public class LinesReader implements Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private List<ValueObject> lines;
    private FileUtils fileUtils;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        lines = new ArrayList<>();
        fileUtils = new FileUtils("data/sampleReport.csv");
        logger.info("Lines Reader initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        ValueObject line = fileUtils.readLine();
        while (line != null) {
            lines.add(line);
            logger.info("Read line: " + line.toString());
            line = fileUtils.readLine();
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fileUtils.closeReader();
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put("lines", this.lines);
        logger.info("Lines Reader ended.");
        return ExitStatus.COMPLETED;
    }
}