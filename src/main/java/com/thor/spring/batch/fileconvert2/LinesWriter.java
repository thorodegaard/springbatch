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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class LinesWriter implements Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory
            .getLogger(LinesWriter.class);

    private List<ValueObject> lines;
    private FileUtils fileUtils;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        this.lines = (List<ValueObject>) executionContext.get("lines");
        fileUtils = new FileUtils("output/outputReport.csv");
        logger.info("Lines Writer initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution,
                                ChunkContext chunkContext) throws Exception {
        for (ValueObject line : lines) {
            fileUtils.writeLine(line);
            logger.info("Wrote line " + line.toString());
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fileUtils.closeWriter();
        logger.info("Lines Writer ended.");
        return ExitStatus.COMPLETED;
    }
}