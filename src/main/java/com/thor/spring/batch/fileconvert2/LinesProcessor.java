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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinesProcessor implements Tasklet, StepExecutionListener {

    private Logger logger = LoggerFactory.getLogger(LinesProcessor.class);

    private List<ValueObject> lines;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        this.lines = (List<ValueObject>) executionContext.get("lines");
        logger.info("Lines Processor initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution,
                                ChunkContext chunkContext) throws Exception {
        for (ValueObject line : lines) {
            // Reverse the value
            String reversedValue = new StringBuilder(Objects.toString(line.getValue())).reverse().toString();
            logger.info("Reversed value: " + reversedValue + " for line " + line.toString());
            line.setValue(reversedValue);
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("Lines Processor ended.");
        return ExitStatus.COMPLETED;
    }
}